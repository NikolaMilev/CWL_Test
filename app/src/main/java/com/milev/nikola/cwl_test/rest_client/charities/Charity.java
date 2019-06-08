package com.milev.nikola.cwl_test.rest_client.charities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Charity {

    @SerializedName("name")
    private String name;

    @SerializedName("average_rating")
    private double averageRating;

    @SerializedName("rating_count")
    private int ratingCount;

    @SerializedName("item_types")
    private List<ItemType> itemTypes ;

    public boolean hasItemType(String itemType){
        for(ItemType type : itemTypes){
            if(type.getName().equals(itemType)){
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", " + averageRating);
        sb.append(", " + ratingCount);
        sb.append(", [");
        if(itemTypes != null){
            for(ItemType it : itemTypes){
                sb.append(it.toString()+", ");
            }
        } else {
            sb.append("NULL");
        }
        sb.append("]");

        return sb.toString();
    }
}
