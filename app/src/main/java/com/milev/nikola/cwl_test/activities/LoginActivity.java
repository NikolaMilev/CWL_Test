package com.milev.nikola.cwl_test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.LoginAttemptListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

public class LoginActivity extends AppCompatActivity implements LoginAttemptListener{

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                EditText editTextPassword = findViewById(R.id.editTextPassword);

                String emailString = editTextEmail.getText().toString();
                String passwordString = editTextPassword.getText().toString();
                Log.d(TAG, "Login attempt. Email: " + emailString + ", Password: " + passwordString);

                RestManager.getInstance().login(emailString, passwordString, LoginActivity.this);
            }
        });

        // For registering a new user
        Button registerButton = findViewById(R.id.buttonRegisterWithEmail);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Forgot password?
        TextView forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        // Reset the fields
//        EditText editTextEmail = findViewById(R.id.editTextEmail);
//        editTextEmail.setText("");
//        EditText editTextPassword = findViewById(R.id.editTextPassword);
//        editTextPassword.setText("");
        TextView invalidTextView = findViewById(R.id.textViewEmailPasswordInvalid);
        invalidTextView.setVisibility(View.INVISIBLE);
    }



    // LoginAttemptFinishedListener methods
    @Override
    public void onLoginSuccess() {
        // Go to feed screen
//        Toast.makeText(this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, HomeFeedActivity.class));
    }

    @Override
    public void onLoginFailure(String message) {
        // Tell the user
//        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        TextView invalidTextView = findViewById(R.id.textViewEmailPasswordInvalid);
        invalidTextView.setVisibility(View.VISIBLE);
    }

}
