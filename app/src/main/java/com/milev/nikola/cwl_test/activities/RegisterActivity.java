package com.milev.nikola.cwl_test.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.RegisterUserListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends InputResponseActivity implements RegisterUserListener{

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
                setInputEnabled(false);
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();

                if(validateInput(firstName, lastName, email, password, confirmPassword)){
                    showDialog();
                    RestManager.getInstance().registerUser(firstName, lastName, email, password, RegisterActivity.this);
                } else{
                    setInputEnabled(true);
                }
            }
        });

        // For connecting using Facebook (for now, just a message the option is unavailable)
        Button buttonContinueWithFacebook = findViewById(R.id.buttonContinueWithFacebook);
        buttonContinueWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this.getApplicationContext(), "Sorry, this option is currently unavailable.", Toast.LENGTH_LONG).show();
            }
        });

        // For the back button
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.onBackPressed();
            }
        });

    }
    @Override
    public void onRegisterUserSuccess() {
        hideDialog();
        setInputEnabled(true);
        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Succesfully registered. Welcome!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, HomeFeedActivity.class));
    }

    @Override
    public void onRegisterUserFailure() {
        hideDialog();
        setInputEnabled(true);
        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Failed to register.", Toast.LENGTH_LONG).show();
    }

    private String checkValidInput(String firstName, String lastName, String email, String password, String confirmPassword){
        if(firstName.length() == 0){
            return "Invalid first name.";
        } else if(lastName.length() == 0){
            return "Invalid last name.";
        } else {
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(email);
            if(!matcher.find()){
                return "Email invalid.";
            }

            if(password.length() == 0){
                return "Invalid password.";
            }

            if(!confirmPassword.equals(password)){
                return "Passwords not matching.";
            }

            return "";
        }
    }

    private boolean validateInput(String firstName, String lastName, String email, String password, String confirmPassword){
        String res = checkValidInput(firstName, lastName, email, password, confirmPassword);
        if(res.equals("")){
            return true;
        } else {
            Toast.makeText(this.getApplicationContext(), res, Toast.LENGTH_LONG).show();
            return false;
        }
    }


    protected void setInputEnabled(boolean enabled){
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setEnabled(enabled);

        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.setEnabled(enabled);

        EditText editTextLastName = findViewById(R.id.editTextLastName);
        editTextLastName.setEnabled(enabled);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(enabled);

        EditText editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword.setEnabled(enabled);

        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextConfirmPassword.setEnabled(enabled);
    }

    protected void resetInput(){
        // Reset the fields
        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.setText("");

        EditText editTextLastName = findViewById(R.id.editTextLastName);
        editTextLastName.setText("");

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setText("");

        EditText editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword.setText("");

        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextConfirmPassword.setText("");
    }
}
