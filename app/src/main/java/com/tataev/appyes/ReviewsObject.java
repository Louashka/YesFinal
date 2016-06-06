package com.tataev.appyes;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by louas_000 on 10.10.2015.
 */
public class ReviewsObject {
    //Build items for ListView in Reviews fragment

    private String objectUserName;
    private Bitmap objectImage;
    private String objectType;
    private String objectCompany;
    private Integer objectNumber;
    private ArrayList<String> objectFeatures;
    private String objectColor;
    private ArrayList<String> objectSize;
    private Double objectPrice;
    private Integer firstValue;
    private Integer secondValue;
    private Integer thirdValue;


    public String getObjectUserName (){
        return this.objectUserName;
    }

    public void setObjectUserName (String objectUserName){
        this.objectUserName = objectUserName;
    }

    public Bitmap getObjectImage (){
        return this.objectImage;
    }

    public void setObjectImage (Bitmap objectImage){
        this.objectImage = objectImage;
    }

    public String getObjectType (){
        return this.objectType;
    }

    public void setObjectType (String objectType){
        this.objectType = objectType;
    }

    public String getObjectCompany (){
        return this.objectCompany;
    }

    public void setObjectCompany (String objectCompany){
        this.objectCompany = objectCompany;
    }

    public Integer getObjectNumber (){
        return this.objectNumber;
    }

    public void setObjectNumber (Integer objectNumber){
        this.objectNumber = objectNumber;
    }

    public ArrayList<String> getObjectFeatures (){
        return this.objectFeatures;
    }

    public void setObjectFeatures (ArrayList<String> objectFeatures){
        this.objectFeatures = objectFeatures;
    }

    public String getObjectColor (){
        return this.objectColor;
    }

    public void setObjectColor (String objectColor){
        this.objectColor = objectColor;
    }

    public ArrayList<String> getObjectSize (){
        return this.objectSize;
    }

    public void setObjectSize (ArrayList<String> objectSize){
        this.objectSize = objectSize;
    }

    public Double getObjectPrice (){
        return this.objectPrice;
    }

    public void setObjectPrice (Double objectPrice){
        this.objectPrice = objectPrice;
    }

    public Integer getObjectFirstValue (){
        return this.firstValue;
    }

    public void setObjectFirstValue (Integer firstValue){
        this.firstValue = firstValue;
    }

    public Integer getObjectSecondValue (){
        return this.secondValue;
    }

    public void setObjectSecondValue (Integer secondValue){
        this.secondValue = secondValue;
    }

    public Integer getObjectThirdValue (){
        return this.thirdValue;
    }

    public void setObjectThirdValue (Integer thirdValue){
        this.thirdValue = thirdValue;
    }
}
