package com.milev.nikola.cwl_test.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.rest_client.RestManager;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCharityName;
        TextView textViewCharityRating;
        ImageView imageViewBackgroundImage;

        ViewHolder(View itemView){
            super(itemView);

            textViewCharityName = itemView.findViewById(R.id.textViewCharityName);
            textViewCharityRating = itemView.findViewById(R.id.textViewCharityRating);
            imageViewBackgroundImage = itemView.findViewById(R.id.imageViewBackgroundImage);
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

        textViewCharityName.setText(charity.getName());
        textViewCharityRating.setText(String.format("%d %s  |  %.1f", charity.getRatingCount(), "Ratings", charity.getAverageRating()));

        String url = charity.getPhotoURL();

        if(url != null){
//            Log.d("CharityAdapter", "Url!" + RestManager.BASE_URL_IMG + url);
            Picasso.get().load(RestManager.BASE_URL_IMG + url).fit().into(imageViewBackgroundImage);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return charities.size();
    }


}
