package com.milev.nikola.cwl_test.rest_client.authentication;

import com.google.gson.annotations.SerializedName;
import com.milev.nikola.cwl_test.rest_client.User;
import com.milev.nikola.cwl_test.rest_client.authentication.Authorization;

import java.util.List;

public class AuthorizationRegistrationResponse {
    @SerializedName("user")
    private User user;

    @SerializedName("roles")
    private List<Role> roles;

    @Override
    public String toString() {
        return user.toString() + " " + ((roles != null && roles.size() > 0) ? roles.get(0).toString() : "NAL");
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getToken() {
        return (roles != null && roles.size() > 0) ? roles.get(0).getToken() : null;
    }

    public String getRefreshToken() {
        return (roles != null && roles.size() > 0) ? roles.get(0).getRefreshToken() : null;
    }
}
