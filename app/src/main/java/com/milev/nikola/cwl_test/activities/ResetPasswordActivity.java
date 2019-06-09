package com.milev.nikola.cwl_test.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.ResetPasswordListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordListener{

    private AlertDialog pleaseWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputEnabled(false);
                String email = editTextEmail.getText().toString();
                if(validateInput(email)){
                    showDialog();
                    RestManager.getInstance().requestResetPassword(email, ResetPasswordActivity.this);
                } else {
                    setInputEnabled(true);
                }
            }
        });

        // For the back button
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void onResetPasswordSuccess(){
        setInputEnabled(true);
        hideDialog();
        Toast.makeText(this.getApplicationContext(), "Reset email has been sent", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onResetPasswordFail(){
        hideDialog();
        setInputEnabled(true);
        Toast.makeText(this.getApplicationContext(), "Unable to send reset email.", Toast.LENGTH_LONG).show();
    }

    private String checkValidInput(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        if(!matcher.find()){
            return "Email invalid.";
        }

        return "";

    }

    private boolean validateInput(String email){
        String res = checkValidInput(email);
        if(res.equals("")){
            return true;
        } else {
            Toast.makeText(this.getApplicationContext(), res, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void setInputEnabled(boolean enabled){
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(enabled);


        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setEnabled(enabled);
    }

    private void resetInput(){
        // Reset the fields
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setText("");
    }

    @Override
    public void onStart(){
        super.onStart();
        resetInput();
        setInputEnabled(true);
    }

    private void showDialog(){
        if(pleaseWait == null){
            AlertDialog.Builder pleaseWaitBuilder = new AlertDialog.Builder(ResetPasswordActivity.this);
            pleaseWaitBuilder.setCancelable(false);
            pleaseWaitBuilder.setView(R.layout.please_wait_dialog);
            pleaseWait = pleaseWaitBuilder.create();
        }

        if(!this.isFinishing()){
            pleaseWait.show();
        }

    }

    private void hideDialog(){
        if(pleaseWait != null && !this.isFinishing()){
            pleaseWait.dismiss();
        }
    }

}
