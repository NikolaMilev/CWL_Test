package com.milev.nikola.cwl_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.RegisterUserListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

public class RegisterActivity extends AppCompatActivity implements RegisterUserListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button buttonRegister = findViewById(R.id.buttonRegister);
        final EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = findViewById(R.id.editTextLastName);
        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        final EditText editTextPassword = findViewById(R.id.editTextPassword);
        final EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();


                if(!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Passwords not matching.", Toast.LENGTH_LONG).show();
                    return;
                }

                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();

                RestManager.getInstance().registerUser(firstName, lastName, email, password, RegisterActivity.this);
            }
        });

    }
    @Override
    public void onRegisterUserSuccess() {
        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Succesfully registered. Welcome!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, HomeFeedActivity.class));
    }

    @Override
    public void onRegisterUserFailure() {
        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Failed to register.", Toast.LENGTH_LONG).show();
    }

}
