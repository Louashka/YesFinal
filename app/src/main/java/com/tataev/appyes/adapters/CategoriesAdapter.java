package com.tataev.appyes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tataev.appyes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by louas_000 on 06.01.2016.
 */
public class CategoriesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater l_InflaterUA;
    private List<String> listDataHeader = new ArrayList<String>();
    private HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

    Integer[] imgId = {R.drawable.ic_man_cloth,R.drawable.ic_woman_cl,R.drawable.ic_children_cl,R.drawable.ic_mobiles_acce,
    R.drawable.ic_electronics,R.drawable.ic_instrum_matrial,R.drawable.ic_hom_garden,R.drawable.ic_kitchen_black_24dp,
    R.drawable.ic_home_tech};

    public CategoriesAdapter (Context context){
        this.context = context;
        l_InflaterUA = LayoutInflater.from(context);
        listDataHeader = Arrays.asList(context.getResources().getStringArray(R.array.categories_items));
        listDataChild.put(listDataHeader.get(0), Arrays.asList(context.getResources().getStringArray(R.array.manCloth)));
        listDataChild.put(listDataHeader.get(1), Arrays.asList(context.getResources().getStringArray(R.array.womanCloth)));
        listDataChild.put(listDataHeader.get(2), Arrays.asList(context.getResources().getStringArray(R.array.childCloth)));
        listDataChild.put(listDataHeader.get(3), Arrays.asList(context.getResources().getStringArray(R.array.mobilesAndAccessoriesArray)));
        listDataChild.put(listDataHeader.get(4), Arrays.asList(context.getResources().getStringArray(R.array.electronicsArray)));
        listDataChild.put(listDataHeader.get(5), Arrays.asList(context.getResources().getStringArray(R.array.hoseGoodsArray)));
        listDataChild.put(listDataHeader.get(6), Arrays.asList(context.getResources().getStringArray(R.array.gardenEquipmentsArray)));
        listDataChild.put(listDataHeader.get(7), Arrays.asList(context.getResources().getStringArray(R.array.kitchenEquipmentArray)));
        listDataChild.put(listDataHeader.get(8), Arrays.asList(context.getResources().getStringArray(R.array.technicsForHouseArray)));

        this.imgId = imgId;
      /*  for (int i = 3; i < listDataHeader.size(); i++){
            listDataChild.put(listDataHeader.get(i), Arrays.asList(context.getResources().getStringArray(R.array.otherCloth)));
        }*/
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return listDataChild.get(listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return listDataHeader.get(groupPosition);
    }
public Object getImages(int groupPosition){
    return imgId[groupPosition];
}


    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return listDataChild.get(listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = l_InflaterUA.inflate(R.layout.categories_group_adapter, null);
        }
        final ImageView ivg = (ImageView)convertView.findViewById(R.id.ivg);
        final TextView tv = (TextView)convertView.findViewById(R.id.categories_group_item);
        String groupName = getGroup(groupPosition).toString();



       // tv.setText((String) getGroup(groupPosition));
        tv.setText(groupName);
        ivg.setImageResource((Integer) getImages(groupPosition));


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.categories_child_adapter, null);
            holder.categoriesChildItem = (TextView)convertView.findViewById(R.id.categories_child_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String childText = (String) getChild(groupPosition, childPosition);
        holder.categoriesChildItem.setText(childText);

        return convertView;
    }


    static class ViewHolder {
        private TextView categoriesChildItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

