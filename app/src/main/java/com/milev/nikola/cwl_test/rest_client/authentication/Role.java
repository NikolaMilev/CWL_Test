package com.milev.nikola.cwl_test.rest_client.authentication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Role {
    @SerializedName("authorization")
    private Authorization authorization;

    @Override
    public String toString() {
        return authorization.toString();
    }

    public String getToken(){
        return authorization.getToken();
    }

    public String getRefreshToken(){
        return authorization.getRefreshToken();
    }
}
