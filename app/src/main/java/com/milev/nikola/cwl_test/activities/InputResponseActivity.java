package com.milev.nikola.cwl_test.activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.milev.nikola.cwl_test.R;

public abstract class InputResponseActivity extends AppCompatActivity {
    protected  AlertDialog pleaseWait;


    @Override
    public void onStart(){
        super.onStart();
        resetInput();
        setInputEnabled(true);
    }

    protected void showDialog(){
        if(pleaseWait == null){
            AlertDialog.Builder pleaseWaitBuilder = new AlertDialog.Builder(InputResponseActivity.this);
            pleaseWaitBuilder.setCancelable(false);
            pleaseWaitBuilder.setView(R.layout.please_wait_dialog);
            pleaseWait = pleaseWaitBuilder.create();
        }

        if(!this.isFinishing()){
            pleaseWait.show();
        }

    }

    protected void hideDialog(){
        if(pleaseWait != null && !this.isFinishing()){
            pleaseWait.dismiss();
        }
    }

    protected abstract void setInputEnabled(boolean enabled);
    protected abstract void resetInput() ;
}
