package com.aashish.dell.findmearent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aashish.dell.findmearent.model.VacantPlaces;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.aashish.dell.findmearent.volleycustomlistview.AppController;
import com.aashish.dell.findmearent.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<VacantPlaces> vacantPlaceItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<VacantPlaces> vacantplaceitems) {
        this.activity = activity;
        this.vacantPlaceItems = vacantplaceitems;
    }

    @Override
    public int getCount() {
        return vacantPlaceItems.size();
    }

    @Override
    public Object getItem(int location) {
        return vacantPlaceItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail1);
        //TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView area = (TextView) convertView.findViewById(R.id.area1);
        TextView date = (TextView) convertView.findViewById(R.id.date1);
        TextView rate = (TextView) convertView.findViewById(R.id.rate1);

        TextView location = (TextView) convertView.findViewById(R.id.location1);

        // getting billionaires data for the row
        VacantPlaces m = vacantPlaceItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // name
       // placeid.setText(m.getPlaceid());

        // Wealth Source
        area.setText(String.valueOf(m.getArea()));

        date.setText(String.valueOf(m.getDate()));
        rate.setText(String.valueOf(m.getRate()));

        // release year
        location.setText(String.valueOf(m.getLocation()));

        return convertView;
    }

}
