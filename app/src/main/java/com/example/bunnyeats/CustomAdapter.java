package com.example.bunnyeats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<YelpItem> allRestaurants;
    private Context context;
    private LayoutInflater inflater;

    public CustomAdapter(List<YelpItem> allRestaurants, Context context){
        this.allRestaurants = allRestaurants;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allRestaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return allRestaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = inflater.inflate(R.layout.yelp_item, null);

        TextView restaurantName = view.findViewById(R.id.restaurantName);
        TextView restaurantRating = view.findViewById(R.id.restaurantRating);
        TextView restaurantAddress = view.findViewById(R.id.restaurantAddress);

        restaurantName.setText(allRestaurants.get(position).getName());
        restaurantRating.setText(allRestaurants.get(position).getRating());
        restaurantAddress.setText(allRestaurants.get(position).getAddress());

        return view;
    }
}
