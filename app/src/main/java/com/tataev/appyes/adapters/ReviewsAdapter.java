package com.tataev.appyes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tataev.appyes.R;
import com.tataev.appyes.ReviewsObject;

import java.util.ArrayList;

/**
 * Created by louas_000 on 10.10.2015.
 */
public class ReviewsAdapter extends BaseAdapter{

    private ArrayList<ReviewsObject> adapterList;
    private Context context;
    private TextView[] sizes = new TextView[6];
    private final int width = 700;
    private final int height = 700;
    private int globalPosition;
    public View.OnClickListener listener;

    private LayoutInflater l_InflaterUA;

    public ReviewsAdapter(Context context, ArrayList<ReviewsObject> adapterList, View.OnClickListener listener) {
        this.context = context;
        this.adapterList = adapterList;
        this.listener = listener;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return adapterList.size();
    }

    @Override
    public Object getItem(int position) {

        return adapterList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void setRatingListener (View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //Get product image and scale it to static values
        Bitmap bitmap = adapterList.get(position).getObjectImage();
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        if (convertView == null) {
            holder = new ViewHolder();
            globalPosition = position;
            convertView = l_InflaterUA.inflate(R.layout.reviews_adapter, null);
            //Initialize layout objects
            holder.reviewsUserName = (TextView)convertView.findViewById(R.id.reviewsUserName);
            holder.noveltyImageView = (ImageView) convertView.findViewById(R.id.noveltyImageView);
            holder.typeNovelty = (TextView) convertView.findViewById(R.id.typeNovelty);
            holder.numberNovelty = (TextView) convertView.findViewById(R.id.numberNovelty);
            holder.featuresNovelty = (TextView) convertView.findViewById(R.id.featuresNovelty);
            holder.colorNovelty = (TextView) convertView.findViewById(R.id.colorNovelty);
            holder.sizeXS = (TextView) convertView.findViewById(R.id.sizeXS);
            holder.sizeS = (TextView) convertView.findViewById(R.id.sizeS);
            holder.sizeM = (TextView) convertView.findViewById(R.id.sizeM);
            holder.sizeL = (TextView) convertView.findViewById(R.id.sizeL);
            holder.sizeXL = (TextView) convertView.findViewById(R.id.sizeXL);
            holder.sizeXXL = (TextView) convertView.findViewById(R.id.sizeXXL);
            holder.noveltyPrice = (TextView) convertView.findViewById(R.id.noveltyPrice);
            holder.reviewsTextLike = (TextView) convertView.findViewById(R.id.reviews_text_like);
            holder.reviewsTextNotLike = (TextView) convertView.findViewById(R.id.reviews_text_not_like);
            holder.reviewsTextNotKnow = (TextView) convertView.findViewById(R.id.reviews_text_not_know);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Set special values to layout objects
        holder.reviewsUserName.setText(adapterList.get(position).getObjectUserName());
        holder.noveltyImageView.setImageBitmap(newBitmap);
        holder.typeNovelty.setText(adapterList.get(position).getObjectType() + ", " + adapterList.get(position).getObjectCompany());
        holder.numberNovelty.setText("• №" + adapterList.get(position).getObjectNumber().toString());
        //Setting of product features table
        if (adapterList.get(position).getObjectFeatures() != null) {
            holder.featuresNovelty.setVisibility(View.VISIBLE);
            String features = "";
            int n = adapterList.get(position).getObjectFeatures().size();
            for (int i = 0; i < n; i++) {
                features += "\u2022 " + adapterList.get(position).getObjectFeatures().get(i) + "\n";
            }
            holder.featuresNovelty.setText(features);
        }
        holder.colorNovelty.setText("Цвет: " + adapterList.get(position).getObjectColor());
        //Setting of product sizes table
        sizes[0] = holder.sizeXS;
        sizes[1] = holder.sizeS;
        sizes[2] = holder.sizeM;
        sizes[3] = holder.sizeL;
        sizes[4] = holder.sizeXL;
        sizes[5] = holder.sizeXXL;
        if (adapterList.get(position).getObjectSize() != null) {
            for (int i = 0; i < adapterList.get(position).getObjectSize().size(); i++) {
                try {
                    sizes[i].setText(adapterList.get(position).getObjectSize().get(i));
                    sizes[i].setVisibility(View.VISIBLE);
                } catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }

            }
        }
        holder.noveltyPrice.setText(adapterList.get(position).getObjectPrice().toString() + " руб.");
        if (this.listener != null) {
            holder.reviewsTextLike.setOnClickListener(this.listener);
            holder.reviewsTextNotLike.setOnClickListener(this.listener);
            holder.reviewsTextNotKnow.setOnClickListener(this.listener);
        }

        return convertView;
    }

    //Class with the layout elements that are need to change
    static class ViewHolder {

        TextView reviewsUserName;
        ImageView noveltyImageView;
        TextView typeNovelty;
        TextView numberNovelty;
        TextView featuresNovelty;
        TextView colorNovelty;
        TextView sizeXS;
        TextView sizeS;
        TextView sizeM;
        TextView sizeL;
        TextView sizeXL;
        TextView sizeXXL;
        TextView noveltyPrice;
        TextView reviewsTextLike;
        TextView reviewsTextNotLike;
        TextView reviewsTextNotKnow;
    }
}
