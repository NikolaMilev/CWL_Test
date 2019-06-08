package com.milev.nikola.cwl_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

        RestManager.getInstance().getCharities("20110", this);



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
        Toast.makeText(this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCharities);

        CharityAdapter charityAdapter = new CharityAdapter(charities);
        recyclerView.setAdapter(charityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onGetCharitiesFail(){
        Toast.makeText(this.getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
    }

}
