package com.tataev.appyes.adapters;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.tataev.appyes.AppController;
import com.tataev.appyes.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by louas_000 on 10.10.2015.
 */
public class UsersSearchAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater l_InflaterUA;
    private AppController userGlobalClass;
    private String[] value;
    private int k;
    private int Countries[] = new int[]{
            R.array.city_default,
            R.array.city_armenia,
            R.array.city_azerbaijan,
            R.array.city_byelorussia,
            R.array.city_kazakhstan,
            R.array.city_kyrgyzstan,
            R.array.city_moldavia,
            R.array.city_russia,
            R.array.city_tajikistan,
            R.array.city_turkmenistan,
            R.array.city_ukraine,
            R.array.city_uzbekistan
    };

    public UsersSearchAdapter (Context context){
        this.context = context;
        l_InflaterUA = LayoutInflater.from(context);
        userGlobalClass = (AppController)context.getApplicationContext();
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = l_InflaterUA.inflate(R.layout.list_group_adapter, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = l_InflaterUA.inflate(R.layout.users_search_adapter, null);
            holder.editTextAgeFrom = (Spinner)convertView.findViewById(R.id.editTextAgeFrom);
            holder.editTextAgeTo = (Spinner)convertView.findViewById(R.id.editTextAgeTo);
            holder.spinnerCountry = (Spinner)convertView.findViewById(R.id.spinnerCountry);
            holder.spinnerCity = (Spinner)convertView.findViewById(R.id.spinnerCity);
            holder.userGender = (RadioGroup)convertView.findViewById(R.id.user_gender);
            holder.spinnerCity.setEnabled(false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userGender.check(userGlobalClass.getRadioGender());
        holder.userGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                userGlobalClass.setRadioGender(checkedId);
            }
        });

        holder.spinnerCity.setSelection(userGlobalClass.getSpinnerCityItem());
        holder.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    userGlobalClass.setSpinnerCityItem(position);
                    value = context.getResources().getStringArray(Countries[userGlobalClass.getSpinnerCountryItem()]);
                    userGlobalClass.setCity(value[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.spinnerCountry.setSelection(userGlobalClass.getSpinnerCountryItem());
        holder.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGlobalClass.setSpinnerCountryItem(position);
                userGlobalClass.setCountry(holder.spinnerCountry.getSelectedItem().toString());
                holder.spinnerCity.setAdapter(ArrayAdapter.createFromResource(context,
                        Countries[position], android.R.layout.simple_spinner_item));
                if (position != 0) {
                    holder.spinnerCity.setEnabled(true);
                } else {
                    holder.spinnerCity.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.editTextAgeFrom.setSelection(userGlobalClass.getAgeFrom());
        holder.editTextAgeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        userGlobalClass.setAgeFrom(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        holder.editTextAgeTo.setSelection(userGlobalClass.getAgeTo());
        holder.editTextAgeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGlobalClass.setAgeTo(position);
                if (userGlobalClass.getAgeTo() < userGlobalClass.getAgeFrom()){
                    holder.editTextAgeFrom.setSelection(userGlobalClass.getAgeTo());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class ViewHolder {
        private Spinner editTextAgeFrom;
        private Spinner editTextAgeTo;
        private Spinner spinnerCountry;
        private Spinner spinnerCity;
        private RadioGroup userGender;
    }

}

