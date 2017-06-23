package com.example.my.myapplication.IOT;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.service.ServiceAccount;
import com.example.my.myapplication.R;
import com.example.my.myapplication.ui.activity.MainActivity;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText emailEditText;
    private EditText passEditText;
    private String email, pass, username;

    private ServiceAccount serviceEditAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        serviceEditAccount = new ServiceAccount();

        usernameText = (EditText) findViewById(R.id.createUsername);

        // Address the email and password field
        emailEditText = (EditText) findViewById(R.id.createEmail);
        passEditText = (EditText) findViewById(R.id.createPassword);

    }

    public void loginAccount(View arg0) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void checkAccount(View arg0) {
   /*     boolean checkNullUsername = false;
        username = usernameText.getText().toString();
        if (username.isEmpty() || username.length() < 3) {
            usernameText.setError("at least 3 characters");
            checkNullUsername = false;

        } else {
            usernameText.setError(null);
            checkNullUsername = true;
        }

        email = emailEditText.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            emailEditText.setError("Invalid Email");
        }

        pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Password cannot be empty");
        }

        Log.i("valid",checkNullUsername+"----------------------");
        if (isValidEmail(email) && isValidPassword(pass) && checkNullUsername) {
            // Validation Completed
            CreateAccount();
        }*/

        // CreateAccount();
        username = usernameText.getText().toString();
        email = emailEditText.getText().toString();
        pass = passEditText.getText().toString();
        username = "Huyền My Nguyễn";
        email = "htmynguyenthi@gmail.com";
        pass = "mymai";
        Account account = new Account(username, true, true, email, pass);
        Account check = null;
        try {
            check = serviceEditAccount.createAccount(account);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (check!=null) {
            Log.d("create", "true");

            Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Log.d("create", "false");
            Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG).show();
        }
    }

    public void showDialogRegistry() {
        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        alertDialog.setTitle("Lỗi đăng ký");
        alertDialog.setMessage("Vui lòng thử lại");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void CreateAccount() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
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
