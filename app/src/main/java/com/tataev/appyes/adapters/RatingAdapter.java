package com.tataev.appyes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tataev.appyes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by louas_000 on 21.10.2015.
 */
public class RatingAdapter extends BaseAdapter {

    private List<String> ratingListItems = new ArrayList<String>();
    private Context context;
    private int ratingListImages[] = new int[]{
            R.drawable.shop_rating,
            R.drawable.customers_rating,
            R.drawable.products_rating,
            R.drawable.services_rating,
            R.drawable.categories_rating,
            R.drawable.objects_rating
    };
    private LayoutInflater l_InflaterUA;

    public RatingAdapter (Context context) {
        this.context = context;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ratingListImages.length;
    }

    @Override
    public Object getItem(int position) {
        return ratingListImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ratingListItems = Arrays.asList(context.getResources().getStringArray(R.array.ratings));
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.rating_adapter, null);
            holder.ratingItem = (TextView)convertView.findViewById(R.id.ratingItem);
            holder.imageRating = (ImageView)convertView.findViewById(R.id.imageRating);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ratingItem.setText(ratingListItems.get(position));
        holder.imageRating.setImageResource(ratingListImages[position]);
        return convertView;
    }

    static class ViewHolder {
        private TextView ratingItem;
        private ImageView imageRating;
    }
}
