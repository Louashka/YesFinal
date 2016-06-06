package com.tataev.appyes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tataev.appyes.Goods;
import com.tataev.appyes.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Xava on 28.10.2015.
 */
public class SubCategoriesAdapterGrid extends BaseAdapter {

    Context context;
    ArrayList<Goods> listGoods;

    public SubCategoriesAdapterGrid(Context context, ArrayList<Goods> listGoods) {
        this.context = context;
        this.listGoods = listGoods;
    }



    public int getCount() {
        return listGoods.size();
    }

    public Object getItem(int position) {
        return listGoods.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPrice;
        private TextView textViewLastPrice;
        private TextView textViewFreeDeliver;
        private TextView textViewOrder;
        private TextView textViewNumOfSelled;
        private ImageView imageViewTopForMan;
        private TextView textViewDiscount;
        private TextView textViewAction;
        private TextView textViewTime;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_three_grid, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitleGrid);
            viewHolder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPriceGrid);
            viewHolder.textViewLastPrice = (TextView) convertView.findViewById(R.id.textViewLastPriceGrid);
            viewHolder.textViewLastPrice.getPaint().setStrikeThruText(true);
            viewHolder.textViewFreeDeliver = (TextView) convertView.findViewById(R.id.textViewFreeDeliverGrid);
            viewHolder.textViewOrder = (TextView) convertView.findViewById(R.id.textViewOrderGrid);
            viewHolder.textViewNumOfSelled = (TextView) convertView.findViewById(R.id.textViewNumOfSelledGrid );
            viewHolder.imageViewTopForMan = (ImageView) convertView.findViewById(R.id.imageViewGrid);


            viewHolder.textViewDiscount = (TextView) convertView.findViewById(R.id.textViewDiscountGrid);
            viewHolder.textViewAction = (TextView) convertView.findViewById(R.id.textViewActionGrid);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.textViewTimeGrid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Goods goods = listGoods.get(position);
        String title = goods.getTitle();
        String price = goods.getPrice();
        String lastPrice = goods.getLastPrice();
        String freeDeliver = goods.getFreeDelivering();
        String oredring = goods.getOrdering();
        String numOfSelled = goods.getNumOfSelled();
        String discount =  goods.getDiscount();
        String action  = goods.getAction();
        String time = goods.getTime();
        Bitmap imageIcon = goods.getImageIcon();
        viewHolder.textViewTitle.setText(title+"");
        viewHolder.textViewPrice.setText(price+"");
        viewHolder.textViewLastPrice.setText(lastPrice+"");
        viewHolder.textViewFreeDeliver.setText(freeDeliver+"");
        viewHolder.textViewOrder.setText(oredring+"");
        viewHolder.textViewNumOfSelled.setText(numOfSelled+"");
        viewHolder.textViewDiscount.setText(discount+"");
        viewHolder.textViewAction.setText(action+"");
        viewHolder.textViewTime.setText(time+"");

        viewHolder.imageViewTopForMan.setImageBitmap(imageIcon);

        return convertView;
    }




    public void sortByPrice() {


        Comparator<Goods> comperator = new Comparator<Goods>() {
            @Override
            public int compare(Goods goods1, Goods goods2) {

                return goods1.getPrice().compareToIgnoreCase(
                        goods2.getPrice());
            }
        };
        Collections.sort(listGoods, comperator);

    }

    public void reverceSsortByPrice() {


        Comparator<Goods> comperator = new Comparator<Goods>() {
            @Override
            public int compare(Goods goods1, Goods goods2) {

                return goods1.getPrice().compareToIgnoreCase(
                        goods2.getPrice());
            }
        };
        Collections.sort(listGoods, Collections.reverseOrder(comperator));

    }

    public void sortOrdering() {


        Comparator<Goods> comperator = new Comparator<Goods>() {
            @Override
            public int compare(Goods goods1, Goods goods2) {

                return goods1.getOrdering().compareToIgnoreCase(
                        goods2.getOrdering());
            }
        };
        Collections.sort(listGoods, comperator);

    }



}
