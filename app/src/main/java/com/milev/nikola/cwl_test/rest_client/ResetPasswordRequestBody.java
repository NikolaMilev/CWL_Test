package com.milev.nikola.cwl_test.rest_client;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequestBody {
    @SerializedName("email")
    String email;

    ResetPasswordRequestBody(String email){
        this.email = email;
    }
}
