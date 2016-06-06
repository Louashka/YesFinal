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
import java.util.List;

/**
 * Created by louas_000 on 22.10.2015.
 */
public class DiscussionsAdapter extends BaseAdapter {

    private List<String> discussListItems = new ArrayList<String>();
    private Context context;
    private Integer discussListImages[] = new Integer[]{
            R.drawable.discuss_draft,
            R.drawable.discuss_draft_bg,
            R.drawable.discuss_draft_bg
    };
    private LayoutInflater l_InflaterUA;

    public DiscussionsAdapter (Context context, List<String> discussListItems) {
        this.context = context;
        this.discussListItems = discussListItems;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return discussListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return discussListItems.get(position);
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
            convertView = l_InflaterUA.inflate(R.layout.discussions_adapter, null);
            holder.discussItem = (TextView)convertView.findViewById(R.id.discussItem);
            holder.imageDiscuss = (ImageView)convertView.findViewById(R.id.imageDiscuss);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.discussItem.setText(discussListItems.get(position));
        holder.imageDiscuss.setImageResource(discussListImages[position]);
        return convertView;
    }

    static class ViewHolder {
        private TextView discussItem;
        private ImageView imageDiscuss;
    }
}
