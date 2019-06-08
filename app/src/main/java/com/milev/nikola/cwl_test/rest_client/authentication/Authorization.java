package com.milev.nikola.cwl_test.rest_client.authentication;

import com.google.gson.annotations.SerializedName;

public class Authorization {

    @SerializedName("type")
    private String appUser;

    @SerializedName("token")
    private String token;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return appUser + " " + token + " " + refreshToken ;
    }
}
