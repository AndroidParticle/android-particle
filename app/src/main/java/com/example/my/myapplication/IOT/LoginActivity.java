package com.example.my.myapplication.IOT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.LoginInfo;
import com.example.my.myapplication.IOT.server.service.ServiceAccount;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.R;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.ui.activity.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private String email, pass;

    private ServiceAccount serviceLogin;
    private SharedPreferences preferences;
    private ServiceDevice serviceDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (serviceLogin == null) {
            serviceLogin = new ServiceAccount();
        }
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }

        // Address the email and password field
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);

        emailEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        emailEditText.requestFocus();


    }

    public void createAccountMember(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void checkLogin(View arg0) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(emailEditText, InputMethodManager.SHOW_IMPLICIT);


      /*  email = emailEditText.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            emailEditText.setError("Invalid Email");
        }

        pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Password cannot be empty");
        }

        if (isValidEmail(email) && isValidPassword(pass)) {
            // Validation Completed
            SignIn();
        }*/

        email = emailEditText.getText().toString();
        pass = passEditText.getText().toString();
        email = "htmynguyen@gmail.com";
        pass = "mymai";

        LoginInfo check = null;

        try {
            check = serviceLogin.checkAccount(email, pass);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (check != null) {
            createSessionUser(getApplicationContext(), preferences, serviceLogin, check.getUser().getId());
            createSeesionListDevice(getApplicationContext(), preferences, serviceDevice, check.getUser().getId());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            showDialogErrorLogin();
        }

    }

    // tao session nguoi dung
    public static boolean createSessionUser(Context applicationContext, SharedPreferences preferences, ServiceAccount serviceLogin, String accountID) {
        boolean check = false;
        preferences = applicationContext.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            accountID = "594427a18872d40011a9d344";
            Account user = serviceLogin.getAccount(accountID);
            editor.putString(ConfigApp.getFULLNAME(), user.getFullName());
            editor.putString(ConfigApp.getEMAIL(), user.getEmail());
            editor.putString(ConfigApp.getIdAccount(), user.getId());

            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putString(ConfigApp.getACCOUNTINFO(), json);

            editor.commit();
            return true;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return check;
    }

    // tao session list device
    public static ArrayList<Device> createSeesionListDevice(Context applicationContext, SharedPreferences preferences, ServiceDevice serviceDevice, String accountID) {
        ArrayList<Device> listDevice = null;
        preferences = applicationContext.getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            accountID = "594427a18872d40011a9d344";
            listDevice = serviceDevice.getListDevice(accountID);

            Gson gson = new Gson();
            String json = gson.toJson(listDevice);
            Log.d("logingetlist", json);
            /*   //danh sach thiet bi
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editors = sharedPrefs.edit();
            //c1
            editors.putString(ConfigApp.getListDevice(), json);
            editors.commit();
            */

            //chuyen cach 2
            editor.putString(ConfigApp.getListDevice(), json);
            editor.commit();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int len = listDevice.size();
        for (int i = 0; i < len; i++) {
            Log.i("ListDEVICEDircreate", listDevice.get(i).getLatitude() + " : " + listDevice.get(i).getLongitude());
        }
        return listDevice;

    }

    public void showDialogErrorLogin() {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Lỗi đăng nhập");
        builder.setMessage("Tên đăng nhập hoặc mật khẩu không đúng");
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/


        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        alertDialog.setTitle("Lỗi đăng nhập");
        alertDialog.setMessage("Tên đăng nhập hoặc mật khẩu không đúng");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void SignIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("Email", email);
        intent.putExtra("Pass", pass);


/*send data
*     //cach 1
        Login = (Button) findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                String message = name.getText().toString();
                intent.putExtra(EXTRA_Name, message);
                intent.putExtra("Pass", pass.getText().toString());
                startActivity(intent);
            }
        });
        //cách 2
        Login2 = (Button) findViewById(R.id.menu);
        Login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String messageName = name.getText().toString();
                editor.putString("Name", messageName);
                String messagePass = pass.getText().toString();
                editor.putString("Pass", messagePass);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
        startActivity(intent);
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }
}
