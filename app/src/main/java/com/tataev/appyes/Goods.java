package com.tataev.appyes;

import android.graphics.Bitmap;

/**
 * Created by Xava on 23.10.2015.
 */
public class Goods {
    private String title;
    private String price;
    private String lastPrice;
    private String freeDelivering;
    private  String ordering;
    private String numOfSelled;
    private String discount;
    private String action;
    private String time;
    private Bitmap imageIcon;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price= price;
    }
    public String  getLastPrice(){
        return  lastPrice;
    }
    public void setLastPrice(String lastPrice){
        this.lastPrice=lastPrice;
    }
    public String getFreeDelivering(){
        return freeDelivering;
    }
    public  void setFreeDelivering(String freeDelivering){
        this.freeDelivering = freeDelivering;
    }
    public String getOrdering(){
        return ordering;
    }
    public void setOrdering(String ordering){
        this.ordering=ordering;
    }
    public String getNumOfSelled(){
        return  numOfSelled;
    }
    public void setNumOfSelled(String numOfSelled){
        this.numOfSelled = numOfSelled;
    }



    public Bitmap getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(Bitmap imageIcon) {
        this.imageIcon = imageIcon;
    }


    public String getDiscount(){
        return discount;
    }
    public void  setDiscount(String  discount) {
        this.discount = discount;
    }
    public String  getAction(){
        return  action;
    }
    public void setAction(String action ){
        this.action = action;
    }
    public String getTime(){
        return time;
    }
    public void  setTime(String time){
        this.time = time;
    }
}
