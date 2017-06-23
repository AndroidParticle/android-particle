/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.my.myapplication.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTDeviceListFragment;
import com.example.my.myapplication.IOT.IOTGridviewFragment;
import com.example.my.myapplication.IOT.IOTPlaceListFragment;
import com.example.my.myapplication.IOT.LoginActivity;
import com.example.my.myapplication.IOT.map.mapClickDetail.TestMapFragment;
import com.example.my.myapplication.IOT.materialprofile.InfoDeviceFragment;
import com.example.my.myapplication.IOT.materialprofile.ProfileFragment;
import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.R;
import com.example.my.myapplication.fenjuly.toggleexpandlayout.SettingCustomFragment;
import com.example.my.myapplication.ui.fragment.MainFragment;
import com.example.my.myapplication.ui.fragment.ViewPagerFragment;

//import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

//import com.example.my.myapplication.IOT.Config;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;
  //  public static Config configContants;
    SharedPreferences sharedPreferences;
    private boolean isSessionUser;

    private String usernameDefault = "User name";
    private String username = null;

    private String emailDefault = "username@gmail.com";
    private String email = null;

    @Override
    public void onInt(Bundle savedInstanceState) {
//        if (configContants == null) {
//            configContants = new Config();
//        }

        // User Information
        this.userName.setText(usernameDefault);
        this.userEmail.setText(emailDefault);
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);
        //this.userBackground.setBackgroundColor(Color.BLUE);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.inbox), R.drawable.ic_place_black_24dp, 2);//0
        mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader//1 nono
        mHelpLiveo.add(getString(R.string.starred), R.drawable.ic_star_black_24dp);//2
        mHelpLiveo.add(getString(R.string.sent_mail), R.drawable.ic_person_pin_circle_black_24dp);//3 @android:drawable/ic_menu_myplaces
        // mHelpLiveo.add(getString(R.string.sent_mail), R.drawable.ic_person_pin_circle_black_24dp);//3 @android:drawable/ic_menu_myplaces
        // mHelpLiveo.addNoCheck(getString(R.string.drafts), R.drawable.ic_drafts_black_24dp);
        mHelpLiveo.add(getString(R.string.drafts), R.drawable.ic_home_black_24dp);//4
        mHelpLiveo.addSeparator(); //Item separator//5 nono
        mHelpLiveo.add(getString(R.string.trash), R.drawable.ic_account_box_black_24dp);//6
        mHelpLiveo.add(getString(R.string.listDevice), R.drawable.ic_perm_device_information_black_24dp, 2);//7  //120 = 99+
        mHelpLiveo.add(getString(R.string.spam), R.drawable.ic_perm_device_information_black_24dp, 2);//8  //120 = 99+
        mHelpLiveo.add(getString(R.string.logout), R.drawable.logout_variant);//9
        //{optional} - Header Customization - method customHeader
        //View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
        //ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        with(this).startingPosition(7) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                        //{optional} - List Customization "If you remove these methods and the list will take his white standard color"

                .selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                .colorItemDefault(R.color.nliveo_white) //Inform the standard color name, icon and counter

                .colorItemSelected(R.color.nliveo_blue_colorPrimary) //State the name of the color, icon and meter when it is selected

                .backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                .colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                        //{optional} - SubHeader Customization
                        //.colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                .colorLineSeparator(R.color.nliveo_white)

                        //.removeFooter()
                .footerItem(R.string.settings, R.drawable.ic_settings_black_24dp)

                        //{optional} - Second footer
                        //.footerSecondItem(R.string.settings, R.drawable.ic_settings_black_24dp)

                        //{optional} - Header Customization
                        //.customHeader(mCustomHeader)

                        //{optional} - Footer Customization
                        //.footerNameColor(R.color.nliveo_blue_colorPrimary)
                        //.footerIconColor(R.color.nliveo_blue_colorPrimary)

                        //.footerSecondNameColor(R.color.nliveo_blue_colorPrimary)
                        //.footerSecondIconColor(R.color.nliveo_blue_colorPrimary)

                        //.footerBackground(R.color.nliveo_white)

                        //{optional} - Remove color filter icon
                        //.removeColorFilter()

                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)

                        //{optional} - Second footer
                        //.setOnClickFooterSecond(onClickFooter)
                .build();


        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);

       /* try {
            Intent intent = getIntent();
            String email = intent.getStringExtra("Email");
            String pass = intent.getStringExtra("Pass");
            Log.i("taikhoan", email + " " + pass);
            Toast.makeText(getBaseContext(), email + "---" + pass, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
        }*/

        // kiem tra session dang nhap
        checkSession();
    }

    public boolean checkSession() {
        sharedPreferences = getApplicationContext().getSharedPreferences(ConfigApp.getACCOUNT(), MODE_PRIVATE);
        isSessionUser = checkSessionLogin();
        if (isSessionUser) {
            Log.d("user name", username);
            this.userName.setText(username);
            this.userEmail.setText(email);

            return true;
        } else {
            Log.d("user name", "null");
            this.userName.setText(usernameDefault);
            this.userEmail.setText(emailDefault);
            return false;
        }
    }

    public boolean checkSessionLogin() {
        username = sharedPreferences.getString(ConfigApp.getFULLNAME(), null);
        email = sharedPreferences.getString(ConfigApp.getEMAIL(), null);
        Log.d("user name", "+++++"+username+"--------");

        if (username != null && email != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                mFragment = new IOTPlaceListFragment();
                break;
            case 1:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                Log.i("nono", "-" + 1);
                break;
            case 2:
                mFragment = new ViewPagerFragment();
                break;
            case 3:
                mFragment = new TestMapFragment();
               // mFragment = new Maps3Activity();
                break;
            /*case 3:
                mFragment = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;*/
            case 4:
                mFragment = new IOTGridviewFragment();
                break;
            case 5:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                Log.i("nono", "-" + 5);
                break;
            case 6:
                mFragment = new ProfileFragment();
                break;
            case 7:
                mFragment = new IOTDeviceListFragment();
                break;
            case 8:
                mFragment = new InfoDeviceFragment();
                break;
            case 9:
                logout();
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                break;
            default:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                break;
        }

        if (mFragment != null) {
            Log.i("not null", "MyClass.getView() — get item number " + mFragment.toString());
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }

        setElevationToolBar(position != 2 ? 15 : 0);
    }


    private void logout() {
        sharedPreferences = getApplicationContext().getSharedPreferences(ConfigApp.getACCOUNT(), Context.MODE_PRIVATE);
        SharedPreferences.Editor clear = sharedPreferences.edit().clear();
        clear.clear(); clear.commit();
        checkSession();
      /*  SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(ConfigApp.getFULLNAME(), null);
            editor.putString(ConfigApp.getEMAIL(), null);
            editor.putString(ConfigApp.getIdAccount(), null);

            editor.commit();
        } catch (Exception e) {
        }*/
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
            Log.i("menu", "MyClass.getView() — get item number ");

        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(), com.example.my.myapplication.fenjuly.toggleexpandlayout.MainActivity.class));

            Fragment mFragment = new SettingCustomFragment();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();

//            Fragment mFragment = new Maps3Activity();
//            FragmentManager mFragmentManager = getSupportFragmentManager();
//            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            closeDrawer();
        }
    };

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

    /*revice data
    *   //cach 1
        Intent intent = getIntent();
        String messagename = intent.getStringExtra(MainActivity.EXTRA_Name);
        name.setText(messagename);
        String messagepass = intent.getStringExtra("Pass");
        pass.setText(messagepass);
        //cach 2
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String name1 = pref.getString("Name", "No name defined");
        String pass1 = pref.getString("Pass", "No pass defined");
        Toast.makeText(LoginActivity.this, "SharedPreferences" + name1.toString() + ":" + pass1.toString(),
                Toast.LENGTH_LONG).show();*/
}

