package com.tataev.appyes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by louas_000 on 10.10.2015.
 */
public class UsersList implements Parcelable{

    String userId;
    String userLogin;
    String userPhoto;
    String userName;
    Boolean userHistory;
    Boolean userRecommendations;

    public UsersList (String userId, String userLogin, String userPhoto, String userName, Boolean userHistory, Boolean userRecommendations) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userPhoto = userPhoto;
        this.userName= userName;
        this.userHistory = userHistory;
        this.userRecommendations = userRecommendations;
    }

    protected UsersList(Parcel in) {
        userId = in.readString();
        userLogin = in.readString();
        userPhoto = in.readString();
        userName = in.readString();
        userHistory = in.readByte() != 0;
        userRecommendations = in.readByte() != 0;
    }

    public static final Creator<UsersList> CREATOR = new Creator<UsersList>() {
        @Override
        public UsersList createFromParcel(Parcel in) {
            return new UsersList(in);
        }

        @Override
        public UsersList[] newArray(int size) {
            return new UsersList[size];
        }
    };

    public String getUserId (){

        return this.userId;
    }

    public void setUserId (String userId){

        this.userId = userId;
    }

    public String getUserLogin (){

        return this.userLogin;
    }

    public void setUserLogin (String userLogin){

        this.userLogin = userLogin;
    }

    public String getUserPhoto (){

        return this.userPhoto;
    }

    public void setUserPhoto (String userPhoto){

        this.userPhoto = userPhoto;
    }

    public String getUserName (){

        return this.userName;
    }

    public void setUserName (String userName){

        this.userName = userName;
    }

    public Boolean getUserHistory (){

        return this.userHistory;
    }

    public void setUserHistory (Boolean userHistory){

        this.userHistory = userHistory;
    }

    public Boolean getUserRecommendations (){

        return this.userRecommendations;
    }

    public void setUserRecommendations (Boolean userRecommendations){

        this.userRecommendations = userRecommendations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userPhoto);
        dest.writeString(userName);
        dest.writeByte((byte) (userHistory ? 1 : 0));
        dest.writeByte((byte) (userRecommendations ? 1 : 0));
    }
}
