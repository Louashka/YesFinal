package com.tataev.appyes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tataev.appyes.R;
import com.tataev.appyes.RoundImage;
import com.tataev.appyes.UsersList;

import java.util.ArrayList;

/**
 * Created by louas_000 on 10.10.2015.
 */
public class UsersAdapter extends BaseAdapter {

    private ArrayList<UsersList> usersList;
    private Context context;
    private LayoutInflater l_InflaterUA;

    public UsersAdapter (Context context, ArrayList<UsersList> usersList) {
        this.context = context;
        this.usersList = usersList;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RoundImage roundedImage = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.users_list_adapter, null);
            holder.imageUsersAva = (ImageView)convertView.findViewById(R.id.imageUsersAva);
            holder.usersName = (TextView)convertView.findViewById(R.id.usersName);
            holder.usersBasket = (ImageView)convertView.findViewById(R.id.usersBasket);
            holder.usersLike = (ImageView)convertView.findViewById(R.id.usersLike);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            byte[] decodedString = Base64.decode(usersList.get(position).getUserPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            roundedImage = new RoundImage(decodedByte, 180, 180);
            holder.imageUsersAva.setImageDrawable(roundedImage);
        } catch (Exception e) {
            holder.imageUsersAva.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.users_logo_default), 180, 180));
            e.printStackTrace();
        }

        holder.usersName.setText(usersList.get(position).getUserName());
        if (usersList.get(position).getUserHistory()){
            holder.usersBasket.setImageResource(R.drawable.basket_ok);
        } else {
            holder.usersBasket.setImageResource(R.drawable.basket);
        }
        if (usersList.get(position).getUserRecommendations()){
            holder.usersLike.setImageResource(R.drawable.like_ok);
        } else {
            holder.usersLike.setImageResource(R.drawable.basket);
        }
        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageUsersAva;
        public TextView usersName;
        public ImageView usersBasket;
        public ImageView usersLike;
    }
}

