package com.milev.nikola.cwl_test.rest_client;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;


    public String getId() {
        return id;
    };
    public String getFirstName() {
        return firstName;
    };
    public String getLastName() {
        return lastName;
    };
    public String getEmail() {
        return email;
    };
    public String getPhone() {
        return phone;
    };

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email + " " + phone ;
    }
}
