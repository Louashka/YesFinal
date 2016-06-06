package com.tataev.appyes.adapters;

/**
 * Created by Xava on 23.10.2015.
 */

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
import com.tataev.appyes.fragments.ProductsList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SubCategoriesAdapter extends BaseAdapter {

    Context context;
    ProductsList productsList;
    ArrayList<Goods> listGoods;

    public SubCategoriesAdapter(Context context, ArrayList<Goods> listGoods) {
        this.context = context;
        this.listGoods = listGoods;
    }

  //  public SubCategoriesAdapter(ProductsList productsList, ArrayList<Goods> listData) {
     //   this.productsList = productsList;
   //     this.listGoods = listData;
//   }

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
            convertView = inflater.inflate(R.layout.row_one, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            viewHolder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
            viewHolder.textViewLastPrice = (TextView) convertView.findViewById(R.id.textViewLastPrice);
            viewHolder.textViewLastPrice.getPaint().setStrikeThruText(true);
            viewHolder.textViewFreeDeliver = (TextView) convertView.findViewById(R.id.textViewFreeDeliver);
            viewHolder.textViewOrder = (TextView) convertView.findViewById(R.id.textViewOrder);
            viewHolder.textViewNumOfSelled = (TextView) convertView.findViewById(R.id.textViewNumOfSelled);
            viewHolder.imageViewTopForMan = (ImageView) convertView.findViewById(R.id.imageViewTopForMan);


            viewHolder.textViewDiscount = (TextView) convertView.findViewById(R.id.textViewDiscount);
            viewHolder.textViewAction = (TextView) convertView.findViewById(R.id.textViewAction);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.textViewTime);
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