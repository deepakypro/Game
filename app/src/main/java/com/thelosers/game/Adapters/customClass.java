package com.thelosers.game.Adapters;

import java.util.ArrayList;

/**
 * Created by deepak on 23/09/17.
 */

public class customClass {


    public ArrayList<websiteCustomClass> websitecustomclasses;
    public ArrayList<String> screenshot;
    public ArrayList<releaseDateCustom> releasedatecustom;
    public String id;
    public String name;
    public String slug;
    public String url;
    public String created_at;
    public String updated_at;
    public String summary;
    public String storyline;


    public customClass() {

    }



    public customClass(String name,String id) {
        this.id = id;
        this.name = name;
    }


    public customClass(String id, String name, String firstReleaseDate, String summary, String storyline, String total_rating, String total_rating_count, String cover, ArrayList<websiteCustomClass> websitecustomclasses, ArrayList<String> screenshot_cloudinary_id, ArrayList<releaseDateCustom> releasedatecustom, ArrayList<String> themes, ArrayList<String> genres, ArrayList<String> game_modes, ArrayList<String> player_perspectives, ArrayList<String> publishers, ArrayList<String> developers) {

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
        this.firstReleaseDate = firstReleaseDate;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public String firstReleaseDate;
    public int collection;

    public String total_rating;
    public String total_rating_count;

    public ArrayList<String> developers;
    public ArrayList<String> publishers;

    public String cover;
    public ArrayList<String> player_perspectives;
    public ArrayList<String> game_modes;

    public ArrayList<String> genres;
    public ArrayList<String> themes;


    public ArrayList<String> screenshot_cloudinary_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(String total_rating) {
        this.total_rating = total_rating;
    }

    public String getTotal_rating_count() {
        return total_rating_count;
    }

    public void setTotal_rating_count(String total_rating_count) {
        this.total_rating_count = total_rating_count;
    }

    public ArrayList<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(ArrayList<String> developers) {
        this.developers = developers;
    }

    public ArrayList<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(ArrayList<String> publishers) {
        this.publishers = publishers;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ArrayList<String> getPlayer_perspectives() {
        return player_perspectives;
    }

    public void setPlayer_perspectives(ArrayList<String> player_perspectives) {
        this.player_perspectives = player_perspectives;
    }

    public ArrayList<String> getGame_modes() {
        return game_modes;
    }

    public void setGame_modes(ArrayList<String> game_modes) {
        this.game_modes = game_modes;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<String> themes) {
        this.themes = themes;
    }

    public ArrayList<String> getScreenshot_cloudinary_id() {
        return screenshot_cloudinary_id;
    }

    public void setScreenshot_cloudinary_id(ArrayList<String> screenshot_cloudinary_id) {
        this.screenshot_cloudinary_id = screenshot_cloudinary_id;
    }

    public ArrayList<String> getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(ArrayList<String> screenshot) {
        this.screenshot = screenshot;
    }


    public ArrayList<websiteCustomClass> getWebsitecustomclasses() {
        return websitecustomclasses;
    }

    public void setWebsitecustomclasses(ArrayList<websiteCustomClass> websitecustomclasses) {
        this.websitecustomclasses = websitecustomclasses;
    }

    public ArrayList<releaseDateCustom> getReleasedatecustom() {
        return releasedatecustom;
    }

    public void setReleasedatecustom(ArrayList<releaseDateCustom> releasedatecustom) {
        this.releasedatecustom = releasedatecustom;
    }

}
