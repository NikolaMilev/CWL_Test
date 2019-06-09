package com.milev.nikola.cwl_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
    SwipeRefreshLayout swipeRefreshLayout;


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

        final EditText editTextEnterZipCode = findViewById(R.id.editTextEnterZipCode);

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

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String zipCode = editTextEnterZipCode.getText().toString();
                if(zipCode.length() > 0){
                    RestManager.getInstance().getCharities(zipCode, HomeFeedActivity.this);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(HomeFeedActivity.this, "Invalid zip code.", Toast.LENGTH_LONG).show();
                }
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

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCharities);
        CharityAdapter charityAdapter = new CharityAdapter(charities);
        recyclerView.setAdapter(charityAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onGetCharitiesFail(){
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(this.getApplicationContext(), "Failed getting charities", Toast.LENGTH_LONG).show();
    }


}
