package com.task.dd.greenbox.Activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/10 上午1:12
 * 描述:
 */
public class Moment implements Parcelable {
    public String content;

    public int getResid() {
        return Resid;
    }

    public void setResid(int resid) {
        Resid = resid;
    }

    public int Resid;
    public String username;
    public ArrayList<String> photos;
    public List<Moment> moments;
    public String time;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }


    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.username);
        dest.writeStringList(this.photos);

    }



    public Moment(String content,String username, ArrayList<String> photos) {
        this.content = content;
        this.username=username;
        this.photos = photos;

    }
    public Moment(){}


    protected Moment(Parcel in) {
        this.content = in.readString();
        this.username=in.readString();
        this.photos = in.createStringArrayList();

    }

    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable.Creator<Moment>() {
        @Override
        public Moment createFromParcel(Parcel source) {
            return new Moment(source);
        }

        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
}