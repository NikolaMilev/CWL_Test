package com.milev.nikola.cwl_test.rest_client;

import com.milev.nikola.cwl_test.rest_client.authentication.AuthenticateUserRequestBody;
import com.milev.nikola.cwl_test.rest_client.authentication.AuthorizationRegistrationResponse;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReSupplyAPI {

    @POST("users/authenticate")
    Call<AuthorizationRegistrationResponse> authenticateEmail(@Body AuthenticateUserRequestBody user);

    @GET("charities")
    Call<List<Charity>> getCharities(@Query("zip") String zip, @Header("Authorization") String token) ;

    @POST("users/reset_password")
    Call<Void> resetPassword(@Body ResetPasswordRequestBody resetPasswordRequestBody) ;

    @POST("users")
    Call<AuthorizationRegistrationResponse> registerUser(@Body RegisterUserBody registerUserBody) ;


}
