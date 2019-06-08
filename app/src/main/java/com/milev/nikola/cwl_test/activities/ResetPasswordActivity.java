package com.milev.nikola.cwl_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.ResetPasswordListener;
import com.milev.nikola.cwl_test.rest_client.RestManager;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestManager.getInstance().requestResetPassword(editTextEmail.getText().toString(), ResetPasswordActivity.this);
            }
        });
    }

    @Override
    public void onResetPasswordSuccess(){
        Toast.makeText(this.getApplicationContext(), "Reset email has been sent", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onResetPasswordFail(){
        Toast.makeText(this.getApplicationContext(), "Unable to send reset email.", Toast.LENGTH_LONG).show();
    }
}
