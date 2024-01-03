package com.globalsion.helloworld;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.globalsion.helloworld.tools.UIHelper;

public class LoginActivity extends AppCompatActivity{

    EditText txtUser;
    EditText txtPassword;
    Button btnLogin;
    private SharedPreferences sharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUser = (EditText)findViewById(R.id.txtLoginUser);
        txtPassword = (EditText)findViewById(R.id.txtLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new BtnLoginClickListener());

        sharedPreferences = getSharedPreferences("HELLOWORLD", MODE_PRIVATE);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        }
    }

    public class BtnLoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String login = sharedPreferences.getString("LoginID","");
            String pass = sharedPreferences.getString("LoginPassword","");

            if(login.equalsIgnoreCase("") && pass.equalsIgnoreCase(""))
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("LoginID", "admin");
                editor.putString("LoginPassword", "admin123");
                editor.commit();

                login = "admin";
                pass = "admin123";
            }
            else if(login != "" && pass != ""){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("LoginID", "a");
                editor.putString("LoginPassword", "123");
                editor.commit();
            }

            if(txtUser.getText().toString().equals(login) &&
                    txtPassword.getText().toString().equals(pass)) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("loginID","admin");
                startActivity(i);
            }
            else{
                UIHelper.ToastMessage(LoginActivity.this, "Username/Password is incorrect to login.");
                return;
            }

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
