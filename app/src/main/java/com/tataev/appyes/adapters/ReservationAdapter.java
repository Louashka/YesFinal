package com.tataev.appyes.adapters;

import android.content.Context;
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
 * Created by louas_000 on 23.10.2015.
 */
public class ReservationAdapter extends BaseAdapter {

    private List<String> reservListItems = new ArrayList<String>();
    private Context context;
    private int reservListImages[] = new int[]{
            R.drawable.purchase_discuss,
            R.drawable.block_discuss,
            R.drawable.desires_discuss,
            R.drawable.services_rating,
            R.drawable.objects_rating
    };
    private LayoutInflater l_InflaterUA;

    public ReservationAdapter (Context context) {
        this.context = context;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return reservListImages.length;
    }

    @Override
    public Object getItem(int position) {
        return reservListImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        reservListItems = Arrays.asList(context.getResources().getStringArray(R.array.reservation_items));
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.reservation_adapter, null);
            holder.reservItem = (TextView)convertView.findViewById(R.id.reservItem);
            holder.imageReserv = (ImageView)convertView.findViewById(R.id.imageReserv);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.reservItem.setText(reservListItems.get(position));
        holder.imageReserv.setImageResource(reservListImages[position]);
        return convertView;
    }

    static class ViewHolder {
        private TextView reservItem;
        private ImageView imageReserv;
    }

}
