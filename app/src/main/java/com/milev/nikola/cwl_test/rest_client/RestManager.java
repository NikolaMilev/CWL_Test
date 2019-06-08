package com.milev.nikola.cwl_test.rest_client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.milev.nikola.cwl_test.listeners.GetCharitiesListener;
import com.milev.nikola.cwl_test.listeners.LoginAttemptListener;
import com.milev.nikola.cwl_test.listeners.ResetPasswordListener;
import com.milev.nikola.cwl_test.rest_client.authentication.AuthenticateUserRequestBody;
import com.milev.nikola.cwl_test.rest_client.authentication.AuthorizationResponse;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is a singleton manager for our rest connections
 */
public class RestManager {

    public final static String BASE_URL_IMG = "http://main-service.staging.resupply.tech";
    public final static String BASE_URL = "http://main-service.staging.resupply.tech/api/v2/";

    private static final String TAG = "RestManager";


    private static RestManager self = null;

    private Gson gson;

    ReSupplyAPI reSupplyAPI;
    Retrofit retrofit;


    // Basic user data
    private String userEmail, token, refreshToken;

    private RestManager(){
        this.gson = new GsonBuilder().setLenient().create();

        // The client builder for interception
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
//                        .addHeader("Cache-Control", "no-cache")
//                        .addHeader("Host", "main-service.staging.resupply.tech")
//                        .addHeader("accept-encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
                        .build();
                return chain.proceed(request);
            }
        });

        // The interceptor for logging (should be removed in the product version)
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);

        // The retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build())
                .build();

        // The ReSupply API instance
       reSupplyAPI = retrofit.create(ReSupplyAPI.class);

    }


    public static RestManager getInstance(){
        if(RestManager.self == null){
            RestManager.self = new RestManager();
        }

        return RestManager.self;
    }

    // Login
    public void login(String email, String password, final LoginAttemptListener loginAttemptFinishedListener){
        Call<AuthorizationResponse> call = reSupplyAPI.authenticateEmail(new AuthenticateUserRequestBody(email, password));
        call.enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, retrofit2.Response<AuthorizationResponse> response) {
                if(response.isSuccessful()){
                    AuthorizationResponse authorizationResponse= response.body();
                    Log.d(TAG, "Success: " + response.toString());
                    RestManager.this.updateState(authorizationResponse);
                    if(loginAttemptFinishedListener != null){
                        loginAttemptFinishedListener.onLoginSuccess();
                    }
                } else {
                    try{
                        Log.d(TAG, "Not success: " +response.errorBody().string());
                        if(loginAttemptFinishedListener != null){
                            loginAttemptFinishedListener.onLoginFailure("Login failed");
                        }
                    } catch (IOException ioe){
                        Log.d(TAG, "EXCEPTION");
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                Log.d(TAG, "Failure: " +t.getMessage());
                if(loginAttemptFinishedListener != null){
                    loginAttemptFinishedListener.onLoginFailure("Login failed");
                }
            }
        });
    }

    // Get charities
    public void getCharities(String zip, final GetCharitiesListener getCharitiesListener){
        if(token == null){
            throw new IllegalStateException("Current Token invalid");
        }

        Call<List<Charity>> call = reSupplyAPI.getCharities(zip, this.token);
        call.enqueue(new Callback<List<Charity>>() {
            @Override
            public void onResponse(Call<List<Charity>> call, retrofit2.Response<List<Charity>> response) {
                if(response.isSuccessful()){
                    getCharitiesListener.onGetCharitiesSuccess(response.body());
                } else {
                    getCharitiesListener.onGetCharitiesFail();
                }
            }

            @Override
            public void onFailure(Call<List<Charity>> call, Throwable t) {
                getCharitiesListener.onGetCharitiesFail();
            }
        });


    }



    // Reset password
    public void requestResetPassword(String email, final ResetPasswordListener resetPasswordListener){
        Call<Void> call = reSupplyAPI.resetPassword(new ResetPasswordRequestBody(email));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(resetPasswordListener != null){
                    if(response.isSuccessful()){
                        resetPasswordListener.onResetPasswordSuccess();
                    } else {
                        resetPasswordListener.onResetPasswordFail();
                    }
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if(resetPasswordListener != null){
                    resetPasswordListener.onResetPasswordFail();
                }

            }
        });
    }


    private void updateState(AuthorizationResponse response) {
        this.userEmail = response.getUserEmail();
        this.token = response.getToken();
        this.refreshToken = response.getRefreshToken();
    }

    // Logout
    public void resetState(){
        userEmail = null;
        token = null;
        refreshToken = null;
    }

    // Register

}
