package com.tataev.appyes;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lou on 04.01.2016.
 */
public class AppController extends Application {
    private int spinnerCountryItem;
    private int spinnerCityItem;
    private int radioGender;
    private int ageFrom;
    private int ageTo;
    private String country;
    private String city;
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public int getSpinnerCountryItem(){
        return spinnerCountryItem;
    }
    public void setSpinnerCountryItem(int spinnerCountryItem){
        this.spinnerCountryItem = spinnerCountryItem;
    }

    public int getSpinnerCityItem(){
        return spinnerCityItem;
    }
    public void setSpinnerCityItem(int spinnerCityItem){
        this.spinnerCityItem = spinnerCityItem;
    }

    public int getRadioGender(){
        return radioGender;
    }
    public void setRadioGender(int radioGender){
        this.radioGender = radioGender;
    }

    public int getAgeFrom(){
        return ageFrom;
    }
    public void setAgeFrom(int ageFrom){
        this.ageFrom = ageFrom;
    }

    public int getAgeTo(){
        return ageTo;
    }
    public void setAgeTo(int ageTo){
        this.ageTo = ageTo;
    }

    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

}
