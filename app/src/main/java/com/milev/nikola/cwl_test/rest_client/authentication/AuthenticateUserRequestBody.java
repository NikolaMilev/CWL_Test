package com.milev.nikola.cwl_test.rest_client.authentication;

public class AuthenticateUserRequestBody {

    private String email;
    private String provider;
    private String scope;
    private String password;

    public AuthenticateUserRequestBody(String email, String password){
        this.email = email;
        this.provider = "email";
        this.scope = "app";
        this.password = password;
    }
}
