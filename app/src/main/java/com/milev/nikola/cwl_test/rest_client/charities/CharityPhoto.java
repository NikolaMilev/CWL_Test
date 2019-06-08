package com.milev.nikola.cwl_test.rest_client.charities;

import com.google.gson.annotations.SerializedName;

public class CharityPhoto {

    @SerializedName("url")
    String url;

    String getPhotoURL(){
        return url;
    }
}
