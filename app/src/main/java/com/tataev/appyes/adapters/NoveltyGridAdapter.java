package com.tataev.appyes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tataev.appyes.R;

import java.util.List;

/**
 * Created by louas_000 on 11.10.2015.
 */
public class NoveltyGridAdapter extends BaseAdapter {
    private static List<String> itemPrice;
    private Context context;

    private LayoutInflater l_InflaterUA;

    public NoveltyGridAdapter (Context context, List<String> results){
        itemPrice = results;
        l_InflaterUA = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemPrice.size();
    }

    @Override
    public Object getItem(int position) {
        return itemPrice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.novelty_list_adapter, null);
            holder.noveltyItem = (TextView)convertView.findViewById(R.id.noveltyItem);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.noveltyItem.setText(itemPrice.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView noveltyItem;
    }

}
