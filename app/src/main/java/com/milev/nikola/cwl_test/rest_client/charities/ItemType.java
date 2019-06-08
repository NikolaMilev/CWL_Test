package com.milev.nikola.cwl_test.rest_client.charities;

import com.google.gson.annotations.SerializedName;

public class ItemType {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item: " + name;
    }
}
