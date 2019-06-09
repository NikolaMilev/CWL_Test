package com.milev.nikola.cwl_test.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.rest_client.RestManager;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCharityName;
        TextView textViewCharityRating;
        ImageView imageViewBackgroundImage;

        ImageView imageViewFurniture;
        ImageView imageViewClothes;
        ImageView imageViewElectronics ;
        ImageView imageViewAppliances;
        ImageView imageViewHousehold ;
        ImageView imageViewBooks ;
        ImageView imageViewMisc;

        TextView textViewFurniture ;
        TextView textViewClothes ;
        TextView textViewElectronics;
        TextView textViewAppliances ;
        TextView textViewHousehold;
        TextView textViewBooks ;
        TextView textViewMisc ;

        int colorDarkGray;
        int colorInactiveGray;

        ViewHolder(View itemView){
            super(itemView);

            textViewCharityName = itemView.findViewById(R.id.textViewCharityName);
            textViewCharityRating = itemView.findViewById(R.id.textViewCharityRating);
            imageViewBackgroundImage = itemView.findViewById(R.id.imageViewBackgroundImage);

            imageViewFurniture = itemView.findViewById(R.id.imageViewFurniture);
            imageViewClothes = itemView.findViewById(R.id.imageViewClothes);
            imageViewElectronics = itemView.findViewById(R.id.imageViewElectronics);
            imageViewAppliances = itemView.findViewById(R.id.imageViewAppliances);
            imageViewHousehold = itemView.findViewById(R.id.imageViewHousehold);
            imageViewBooks = itemView.findViewById(R.id.imageViewBooks);
            imageViewMisc = itemView.findViewById(R.id.imageViewMisc);

            textViewFurniture = itemView.findViewById(R.id.textViewFurniture);
            textViewClothes = itemView.findViewById(R.id.textViewClothes);
            textViewElectronics = itemView.findViewById(R.id.textViewElectronics);
            textViewAppliances = itemView.findViewById(R.id.textViewAppliances);
            textViewHousehold = itemView.findViewById(R.id.textViewHousehold);
            textViewBooks = itemView.findViewById(R.id.textViewBooks);
            textViewMisc = itemView.findViewById(R.id.textViewMisc);
        }
    }

    private List<Charity> charities;

    public CharityAdapter(List<Charity> charities){
        super();
        this.charities = charities;
    }


    @Override
    public CharityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        viewHolder.colorDarkGray = context.getResources().getColor(R.color.darkGray);
        viewHolder.colorInactiveGray = context.getResources().getColor(R.color.inactiveGray);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CharityAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Charity charity = charities.get(position);

        // Set item views based on your views and data model
        TextView textViewCharityName = viewHolder.textViewCharityName;
        TextView textViewCharityRating = viewHolder.textViewCharityRating;
        ImageView imageViewBackgroundImage = viewHolder.imageViewBackgroundImage;

        // Set the name
        textViewCharityName.setText(charity.getName());

        // Set the rating string
        textViewCharityRating.setText(String.format("%d %s  |  %.1f", charity.getRatingCount(), "Ratings", charity.getAverageRating()));


        // Set the icon and text colors BOILERPLATE
        //Furniture
        if(charity.acceptsFurniture()){
            viewHolder.imageViewFurniture.setImageResource(R.drawable.cwl_furniture_active);
            viewHolder.textViewFurniture.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewFurniture.setImageResource(R.drawable.cwl_furniture_inactive);
            viewHolder.textViewFurniture.setTextColor(viewHolder.colorInactiveGray);
        }

        // Clothing / Clothes
        if(charity.acceptsClothes()){
            viewHolder.imageViewClothes.setImageResource(R.drawable.cwl_clothing_active);
            viewHolder.textViewClothes.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewClothes.setImageResource(R.drawable.cwl_clothing_inactive);
            viewHolder.textViewClothes.setTextColor(viewHolder.colorInactiveGray);
        }

        // Electronics
        if(charity.acceptsElectronics()){
            viewHolder.imageViewElectronics.setImageResource(R.drawable.cwl_elecronics_active);
            viewHolder.textViewElectronics.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewElectronics.setImageResource(R.drawable.cwl_elecronics_inactive);
            viewHolder.textViewElectronics.setTextColor(viewHolder.colorInactiveGray);
        }

        // Appliances
        if(charity.acceptsAppliances()){
            viewHolder.imageViewAppliances.setImageResource(R.drawable.cwl_appliances_active);
            viewHolder.textViewAppliances.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewAppliances.setImageResource(R.drawable.cwl_appliances_inactive);
            viewHolder.textViewAppliances.setTextColor(viewHolder.colorInactiveGray);
        }

        // Household
        if(charity.acceptsHousehold()){
            viewHolder.imageViewHousehold.setImageResource(R.drawable.cwl_household_active);
            viewHolder.textViewHousehold.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewHousehold.setImageResource(R.drawable.cwl_household_inactive);
            viewHolder.textViewHousehold.setTextColor(viewHolder.colorInactiveGray);
        }

        // Books
        if(charity.acceptsBooks()){
            viewHolder.imageViewBooks.setImageResource(R.drawable.cwl_books_active);
            viewHolder.textViewBooks.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewBooks.setImageResource(R.drawable.cwl_books_inactive);
            viewHolder.textViewBooks.setTextColor(viewHolder.colorInactiveGray);
        }

        // Misc
        if(charity.acceptsMisc()){
            viewHolder.imageViewMisc.setImageResource(R.drawable.cwl_miscellaneous_active);
            viewHolder.textViewMisc.setTextColor(viewHolder.colorDarkGray);
        } else {
            viewHolder.imageViewMisc.setImageResource(R.drawable.cwl_miscellaneous_inactive);
            viewHolder.textViewMisc.setTextColor(viewHolder.colorInactiveGray);
        }

        // End


        // Set the photo, if there is one
        String url = charity.getPhotoURL();
        if(url != null){
            Picasso.get().load(RestManager.BASE_URL_IMG + url).fit().into(imageViewBackgroundImage);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return charities.size();
    }


}
