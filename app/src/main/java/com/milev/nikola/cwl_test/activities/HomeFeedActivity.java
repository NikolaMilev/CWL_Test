package com.milev.nikola.cwl_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.listeners.GetCharitiesListener;
import com.milev.nikola.cwl_test.recycler_view.CharityAdapter;
import com.milev.nikola.cwl_test.rest_client.RestManager;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;

import java.util.List;

public class HomeFeedActivity extends AppCompatActivity implements GetCharitiesListener{

    private static final String TAG = "HomeFeedActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestManager.getInstance().resetState();
                HomeFeedActivity.this.startActivity(new Intent(HomeFeedActivity.this, LoginActivity.class));
            }
        });

        EditText editTextEnterZipCode = findViewById(R.id.editTextEnterZipCode);

        editTextEnterZipCode.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                RestManager.getInstance().getCharities(c.toString(), HomeFeedActivity.this);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

    }

    @Override
    public void onGetCharitiesSuccess(List<Charity> charities){
        if(charities != null){
            for(Charity charity :  charities){
                Log.d(TAG, charity.toString());
            }
        } else {
            Log.d(TAG, "CHARITIES NULL");
        }
//        Toast.makeText(this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCharities);

        CharityAdapter charityAdapter = new CharityAdapter(charities);
        recyclerView.setAdapter(charityAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onGetCharitiesFail(){
        Toast.makeText(this.getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
    }

}
