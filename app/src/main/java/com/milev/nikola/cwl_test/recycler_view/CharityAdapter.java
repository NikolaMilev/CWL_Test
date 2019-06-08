package com.milev.nikola.cwl_test.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milev.nikola.cwl_test.R;
import com.milev.nikola.cwl_test.rest_client.charities.Charity;

import java.util.List;

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ViewHolder(View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
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
        View contactView = inflater.inflate(R.layout.item_view, parent, false);

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
        TextView textView = viewHolder.textViewName;

        textView.setText(charity.getName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return charities.size();
    }


}
