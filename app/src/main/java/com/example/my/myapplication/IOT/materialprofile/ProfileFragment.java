package com.example.my.myapplication.IOT.materialprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTDeviceListFragment;
import com.example.my.myapplication.IOT.LoginActivity;
import com.example.my.myapplication.IOT.SignupActivity;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.TypeParticle;
import com.example.my.myapplication.IOT.server.service.ServiceAccount;
import com.example.my.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {
    View rootView;
    FloatingActionButton fabpro, fab1, fab2;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    AppBarLayout app_bar;
    TextView emailAccount, nameAccount;


    EditText t_user, t_pass, t_email;

    //edit
    private View alertView;
    private AlertDialog myDialog;
    private ServiceAccount serviceEditAccount;
    private SharedPreferences preferences;
    Account accountInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (serviceEditAccount == null)
            serviceEditAccount = new ServiceAccount();

        try {
            rootView = inflater.inflate(R.layout.activity_profile, container, false);

        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        accountInfo = checkSessionAccountInfo(getContext(), preferences);


        nameAccount = (TextView) rootView.findViewById(R.id.nameAccount);
        emailAccount = (TextView) rootView.findViewById(R.id.emailAccount);

        if (accountInfo == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            setAccountInfo(accountInfo, nameAccount, emailAccount);
        }

        fabpro = (FloatingActionButton) rootView.findViewById(R.id.fabpro);
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fabpro1);
        fab2 = (FloatingActionButton) rootView.findViewById(R.id.fabpro2);
        linearLayout1 = (LinearLayout) rootView.findViewById(R.id.layoutpro1);
        linearLayout2 = (LinearLayout) rootView.findViewById(R.id.layoutpro2);

        app_bar = (AppBarLayout) rootView.findViewById(R.id.app_bar);
        app_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibilityFABclose();

            }
        });

        fabpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setVisibilityFAB();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("key"));
                Snackbar.make(v, "Replace with your own action 2", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setVisibilityFAB();

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertEdit();
                myDialog.show();

                setVisibilityFAB();

            }
        });


        return rootView;
    }

    private void AlertEdit() {
        //alert edit
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflaters = LayoutInflater.from(getContext());
        alertView = inflaters.inflate(R.layout.dialog_edit_account, null);

        t_user = (EditText) alertView.findViewById(R.id.editUsername);
        t_pass = (EditText) alertView.findViewById(R.id.editPassword);
        t_email = (EditText) alertView.findViewById(R.id.editEmail);
        if (accountInfo != null)
            setAccountInfo(accountInfo, t_user, t_email);

        builder.setView(alertView);
        builder.setInverseBackgroundForced(true);
        builder.setTitle("Edit Profile");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = t_user.getText().toString();
                String password = t_pass.getText().toString();
                String email = t_email.getText().toString();

                username = "Huyền My Nguyễn 1";
                email = "htmynguyen@gmail.com";
                password = "mymai";

                if (editProfile(accountInfo,username, password, email) == null) {
                    error(getContext());
                } else {
                    LoginActivity.createSessionUser(getContext(), preferences, serviceEditAccount, accountInfo.getId());
                    reloadProfileFragment(getContext());
                }
                setVisibilityFAB();

            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                setVisibilityFAB();

            }
        });

        builder.setCancelable(false);
        myDialog = builder.create();
        //end alert edit
    }

    private void error(Context context) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Vui lòng thử lại");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void setAccountInfo(Account accountInfo, TextView nameAccount, TextView emailAccount) {
        nameAccount.setText(accountInfo.getFullName());
        emailAccount.setText(accountInfo.getEmail());

    }

    private Account editProfile(Account accountInfo,String username, String password, String email) {
//        Account account = new Account(username, true, true, email, password, "5944e7a58d5211082430ad7f");
        Account account = new Account(username, true, true, email, password, accountInfo.getId());
        account.setFullName(username);
        account.setEmail(email);
        account.setPassword(password);
        Account check = null;
        try {
            check = serviceEditAccount.editAccount(account);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return check;

    }

    private void setVisibilityFAB() {
        if (linearLayout1.getVisibility() == View.VISIBLE && linearLayout2.getVisibility() == View.VISIBLE) {
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
        } else {
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityFABclose() {
        if (linearLayout1.getVisibility() == View.VISIBLE && linearLayout2.getVisibility() == View.VISIBLE) {
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
        } else {
        }
    }

    public static void reloadProfileFragment(Context context) {
        Fragment mFragment;
        mFragment = new ProfileFragment();
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, mFragment).commit();
    }

    public static Account checkSessionAccountInfo(Context context, SharedPreferences sharedPreferences) {
        sharedPreferences = context.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<Account>() {
        }.getType();
        //c2
        String json2 = sharedPreferences.getString(ConfigApp.getACCOUNTINFO(), null);
        Account account = gson.fromJson(json2, type);
        return account;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setHasOptionsMenu(true);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settingsnewnew) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
