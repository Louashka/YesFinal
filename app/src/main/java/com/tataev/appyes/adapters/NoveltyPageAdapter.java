package com.tataev.appyes.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tataev.appyes.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by louas_000 on 11.10.2015.
 */
public class NoveltyPageAdapter extends PagerAdapter {

    private String[] tabTitles;
    private LayoutInflater l_InflaterUA;
    private Context context;
    private List<String> items = Arrays.asList("   US $3.29", "   US $2.59", "   US $2.99", "   US $2.36");

    public NoveltyPageAdapter (Context context, String[] tabTitles){
        this.context = context;
        this.tabTitles = tabTitles;
        l_InflaterUA = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle (int position) {
        return tabTitles[position];
    }

    @Override
    public Object instantiateItem (ViewGroup container, int position){
        View view = l_InflaterUA.inflate(R.layout.novelty_grid_tab_adapter, container, false);
        container.addView(view);
        GridView gridView = (GridView)view.findViewById(R.id.gridViewNovelty);
        gridView.setAdapter(new NoveltyGridAdapter(context, items));
        return view;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }
}
