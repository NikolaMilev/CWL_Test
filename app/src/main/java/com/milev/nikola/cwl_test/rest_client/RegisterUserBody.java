package com.milev.nikola.cwl_test.rest_client;

import com.google.gson.annotations.SerializedName;

public class RegisterUserBody {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("provider")
    private String provider;

    public RegisterUserBody(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.provider = "email";
    }
}
