package com.thelosers.game.HelperClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thelosers.game.Adapters.releaseDateCustom;
import com.thelosers.game.Adapters.websiteCustomClass;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by deepak on 23/09/17.
 */

public class UtilData implements Parcelable {


    public String id;
    public String name;

    public String firstReleaseDate;

    public String summary;
    public String storyline;

    public String total_rating;
    public String total_rating_count;
    public String cover;

    //themes,player_perspectives,franchise,game_modes,genres
    public ArrayList<String> developers;
    public ArrayList<String> publishers;

    public ArrayList<String> player_perspectives;
    public ArrayList<String> game_modes;
    public ArrayList<String> genres;
    public ArrayList<String> themes;
    public ArrayList<releaseDateCustom> releasedatecustom;
    public ArrayList<String> screenshot_cloudinary_id;
    public ArrayList<websiteCustomClass> websitecustomclasses;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(summary);
        dest.writeString(storyline);
        dest.writeString(total_rating);
        dest.writeString(total_rating_count);
        dest.writeString(cover);

        Gson gson = new Gson();
        String devjson = gson.toJson(developers);
        dest.writeString(devjson);

        String json1 = gson.toJson(publishers);
        dest.writeString(json1);

        String json2 = gson.toJson(player_perspectives);
        dest.writeString(json2);

        String json3 = gson.toJson(game_modes);
        dest.writeString(json3);

        String json4 = gson.toJson(genres);
        dest.writeString(json4);

        String json5 = gson.toJson(themes);
        dest.writeString(json5);


        String json6 = gson.toJson(releasedatecustom);
        dest.writeString(json6);


        String json7 = gson.toJson(screenshot_cloudinary_id);
        dest.writeString(json7);

        String json8 = gson.toJson(websitecustomclasses);
        dest.writeString(json8);

        dest.writeString(firstReleaseDate);

    }


    public UtilData(String id, String name, String summary, String storyline, String total_rating, String total_rating_count, String cover, ArrayList<String> developers, ArrayList<String> publishers, ArrayList<String> player_perspectives, ArrayList<String> game_modes, ArrayList<String> genres, ArrayList<String> themes, ArrayList<releaseDateCustom> releasedatecustom, ArrayList<String> screenshot_cloudinary_id, ArrayList<websiteCustomClass> websitecustomclasses,String firstReleaseDate) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.storyline = storyline;
        this.total_rating = total_rating;
        this.total_rating_count = total_rating_count;
        this.cover = cover;
        this.developers = developers;
        this.publishers = publishers;
        this.player_perspectives = player_perspectives;
        this.game_modes = game_modes;
        this.genres = genres;
        this.themes = themes;
        this.releasedatecustom = releasedatecustom;
        this.screenshot_cloudinary_id = screenshot_cloudinary_id;
        this.websitecustomclasses = websitecustomclasses;
        this.firstReleaseDate=firstReleaseDate;
    }


    private UtilData(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.summary = in.readString();
        this.storyline = in.readString();
        this.total_rating = in.readString();
        this.total_rating_count = in.readString();
        this.cover = in.readString();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        Type type1 = new TypeToken<ArrayList<String>>() {
        }.getType();
        this.developers = gson.fromJson(in.readString(), type);
        this.publishers = gson.fromJson(in.readString(), type);
        this.player_perspectives = gson.fromJson(in.readString(), type1);
        this.game_modes = gson.fromJson(in.readString(), type1);
        this.genres = gson.fromJson(in.readString(), type1);
        this.themes = gson.fromJson(in.readString(), type1);

        Type type2 = new TypeToken<ArrayList<releaseDateCustom>>() {
        }.getType();
        this.releasedatecustom = gson.fromJson(in.readString(), type2);
        this.screenshot_cloudinary_id = gson.fromJson(in.readString(), type1);

        Type type3 = new TypeToken<ArrayList<websiteCustomClass>>() {
        }.getType();
        this.websitecustomclasses = gson.fromJson(in.readString(), type3);

        this.firstReleaseDate=in.readString();
    }

    public static final Parcelable.Creator<UtilData> CREATOR = new Parcelable.Creator<UtilData>() {

        @Override
        public UtilData createFromParcel(Parcel source) {
            return new UtilData(source);
        }

        @Override
        public UtilData[] newArray(int size) {
            return new UtilData[size];
        }
    };
}
