package com.milev.nikola.cwl_test.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.LoginAttemptListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends InputResponseActivity implements LoginAttemptListener{

    private static final String TAG = "LoginActivity";
    private static final int CODE_PERMISSION_USE_INTERNET = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermissions();

        // Logging in
        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setInputEnabled(false);
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                EditText editTextPassword = findViewById(R.id.editTextPassword);

                String emailString = editTextEmail.getText().toString();
                String passwordString = editTextPassword.getText().toString();

                if(validateInput(emailString, passwordString)){
                    Log.d(TAG, "Login attempt. Email: " + emailString + ", Password: " + passwordString);
                    showDialog();
                    RestManager.getInstance().login(emailString, passwordString, LoginActivity.this);
                } else {
                    setInputEnabled(true);
                }


            }
        });

        // For registering a new user
        Button registerButton = findViewById(R.id.buttonRegisterWithEmail);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputEnabled(false);
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Forgot password?
        TextView forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputEnabled(false);
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        // For connecting using Facebook (for now, just a message the option is unavailable)
        Button buttonContinueWithFacebook = findViewById(R.id.buttonContinueWithFacebook);
        buttonContinueWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this.getApplicationContext(), "Sorry, this option is currently unavailable.", Toast.LENGTH_LONG).show();
            }
        });

        // For the back button
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.onBackPressed();
            }
        });

    }


    protected void setInputEnabled(boolean enabled){
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(enabled);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword.setEnabled(enabled);
        TextView invalidTextView = findViewById(R.id.textViewEmailPasswordInvalid);
        invalidTextView.setVisibility(View.INVISIBLE);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setEnabled(enabled);
        Button buttonContinueWithFacebook = findViewById(R.id.buttonContinueWithFacebook);
        buttonContinueWithFacebook.setEnabled(enabled);
        Button buttonRegisterWithEmail = findViewById(R.id.buttonRegisterWithEmail);
        buttonRegisterWithEmail.setEnabled(enabled);
    }

    protected void resetInput(){
        // Reset the fields
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setText("");
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword.setText("");
        TextView invalidTextView = findViewById(R.id.textViewEmailPasswordInvalid);
        invalidTextView.setVisibility(View.INVISIBLE);
    }



    // LoginAttemptFinishedListener methods
    @Override
    public void onLoginSuccess() {
        // Go to feed screen
//        Toast.makeText(this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        hideDialog();
        startActivity(new Intent(this, HomeFeedActivity.class));
    }

    @Override
    public void onLoginFailure(String message) {
        // Tell the user
//        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        hideDialog();
        setInputEnabled(true);
        TextView invalidTextView = findViewById(R.id.textViewEmailPasswordInvalid);
        invalidTextView.setVisibility(View.VISIBLE);
    }

    private String checkValidInput(String email, String password){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        if(!matcher.find()){
            return "Email invalid.";
        }

        if(password.length() == 0){
            return "Invalid password.";
        }

        return "";

    }

    private boolean validateInput(String email, String password){
        String res = checkValidInput(email, password);
        if(res.equals("")){
            return true;
        } else {
            Toast.makeText(this.getApplicationContext(), res, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, CODE_PERMISSION_USE_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_PERMISSION_USE_INTERNET: {
                // Permission denied.
                if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "This app needs internet to function. Exiting.", Toast.LENGTH_LONG).show();
                    this.onBackPressed();
                }
                return;
            }
        }
    }
}
