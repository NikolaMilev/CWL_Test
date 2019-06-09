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

    @SerializedName("photo")
    private CharityPhoto charityPhoto;

    public boolean hasItemType(String itemType){
        for(ItemType type : itemTypes){
            if(type.getName().equalsIgnoreCase(itemType)){
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return this.name;
    }

    public int getRatingCount(){
        return ratingCount;
    }

    public double getAverageRating(){
       return averageRating;
    }

    public String getPhotoURL(){
        if(charityPhoto != null){
            return charityPhoto.getPhotoURL();
        } else {
            return null;
        }
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

    public boolean acceptsFurniture(){
        return this.hasItemType("furniture");
    }

    public boolean acceptsClothes(){
        return this.hasItemType("clothing");
    }

    public boolean acceptsElectronics(){
        return this.hasItemType("electronics");
    }

    public boolean acceptsAppliances(){
        return this.hasItemType("appliances");
    }

    public boolean acceptsHousehold(){
        return this.hasItemType("household");
    }

    public boolean acceptsBooks(){
        return this.hasItemType("books");
    }

    public boolean acceptsMisc(){
        return this.hasItemType("miscellaneous");
    }
}
