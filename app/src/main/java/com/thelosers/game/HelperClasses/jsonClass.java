package com.thelosers.game.HelperClasses;

import android.util.Log;

import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.releaseDateCustom;
import com.thelosers.game.Adapters.websiteCustomClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.thelosers.game.HelperClasses.help.getDaysLeft;

/**
 * Created by deepak on 23/09/17.
 */

public class jsonClass {

    public static ArrayList<customClass> getUpcomingJson(JSONArray response) {

        ArrayList<releaseDateCustom> releaseArrayList = null;
        ArrayList<String> devArrayList = null;
        ArrayList<String> sceenShot = null;
        ArrayList<String> pubArrayList = null;
        ArrayList<String> game_modeArrayList = null;
        ArrayList<String> player_perspectivesArrayList = null;
        ArrayList<String> themeArrayList = null;
        ArrayList<websiteCustomClass> websiteArraylist = null;

        ArrayList<String> genreArrayList = null;
        ArrayList<customClass> arrayList = new ArrayList<>();

        try {


            JSONArray currentEventArray = response;
            for (int i = 0; i < currentEventArray.length(); i++) {
                JSONObject currentEvent = currentEventArray.getJSONObject(i);
                JSONObject cover = null;
                String id = currentEvent.getString("id");


                String rating = "";
                String releaseUnix = "";
                String rating_count = "";
                String summary = "", image = "";
                String name = currentEvent.getString("name");
                if (currentEvent.has("cover")) {
                    cover = currentEvent.getJSONObject("cover");
                    image = cover.getString("cloudinary_id");
                }
                if (currentEvent.has("summary")) {
                    summary = currentEvent.getString("summary");
                }
                String storyline = "";
                if (currentEvent.has("storyline")) {
                    storyline = currentEvent.getString("storyline");
                }

                if (currentEvent.has("first_release_date")) {
                    releaseUnix = currentEvent.getString("first_release_date");

                }
                if (currentEvent.has("total_rating")) {
                    rating = currentEvent.getString("total_rating");
                }
                if (currentEvent.has("total_rating_count")) {
                    rating_count = currentEvent.getString("total_rating_count");
                }
                //  String popularity = currentEvent.getString("popularity");
                // String category = currentEvent.getString("category");

                if (currentEvent.has("developers")) {
                    devArrayList = new ArrayList<>();
                    JSONArray dev = currentEvent.getJSONArray("developers");
                    for (int j = 0; j < dev.length(); j++) {
                        JSONObject releaseEvent = dev.getJSONObject(j);
                        devArrayList.add(releaseEvent.getString("name"));


                    }
                }
                if (currentEvent.has("publishers")) {
                    pubArrayList = new ArrayList<>();
                    JSONArray pub = currentEvent.getJSONArray("publishers");
                    for (int k = 0; k < pub.length(); k++) {
                        JSONObject releaseEvent = pub.getJSONObject(k);
                        pubArrayList.add(releaseEvent.getString("name"));

                    }
                }



                if (currentEvent.has("game_modes")) {
                    game_modeArrayList = new ArrayList<>();
                    JSONArray game_mode = currentEvent.getJSONArray("game_modes");
                    for (int j = 0; j < game_mode.length(); j++) {
                        JSONObject releaseEvent = game_mode.getJSONObject(j);

                        game_modeArrayList.add(releaseEvent.getString("name"));
                    }
                }


                if (currentEvent.has("player_perspectives")) {

                    player_perspectivesArrayList = new ArrayList<>();
                    JSONArray player_per = currentEvent.getJSONArray("player_perspectives");
                    for (int k = 0; k < player_per.length(); k++) {
                        JSONObject releaseEvent = player_per.getJSONObject(k);
                        player_perspectivesArrayList.add(releaseEvent.getString("name"));
                    }
                }


                if (currentEvent.has("themes")) {
                    themeArrayList = new ArrayList<>();
                    JSONArray theme = currentEvent.getJSONArray("themes");
                    for (int j = 0; j < theme.length(); j++) {

                        JSONObject releaseEvent = theme.getJSONObject(j);
                        themeArrayList.add(releaseEvent.getString("name"));


                    }
                }


                if (currentEvent.has("genres")) {
                    genreArrayList = new ArrayList<>();
                    JSONArray genre = currentEvent.getJSONArray("genres");
                    for (int k = 0; k < genre.length(); k++) {
                        JSONObject releaseEvent = genre.getJSONObject(k);
                        genreArrayList.add(releaseEvent.getString("name"));


                    }
                }


                if (currentEvent.has("screenshots")) {

                    sceenShot = new ArrayList<>();
                    JSONArray screen = currentEvent.getJSONArray("screenshots");
                    for (int k = 0; k < screen.length(); k++) {
                        JSONObject releaseEvent = screen.getJSONObject(k);
                        sceenShot.add("https://images.igdb.com/igdb/image/upload/t_1080p/" + releaseEvent.getString("cloudinary_id") + ".jpg");
                    }
                }


                if (currentEvent.has("release_dates")) {
                    releaseArrayList = new ArrayList<>();
                    JSONArray releaseDate = currentEvent.getJSONArray("release_dates");
                    for (int k = 0; k < releaseDate.length(); k++) {

                        releaseDateCustom real = new releaseDateCustom();
                        JSONObject releaseEvent = releaseDate.getJSONObject(k);

                        real.setHuman(releaseEvent.getString("human"));
                        real.setPlatform(releaseEvent.getInt("platform"));
                        releaseArrayList.add(real);
                    }
                }


                if (currentEvent.has("websites")) {
                    websiteArraylist = new ArrayList<>();
                    JSONArray website = currentEvent.getJSONArray("websites");
                    for (int k = 0; k < website.length(); k++) {
                        websiteCustomClass real = new websiteCustomClass();
                        JSONObject releaseEvent = website.getJSONObject(k);
                        int category = releaseEvent.getInt("category");
                        String name12 = help.getWebsiteName(category);
                        if (name12 != null) {
                            real.setCategory(category);
                            real.setUrl(releaseEvent.getString("url"));
                            websiteArraylist.add(real);
                        }

                    }
                }

                customClass customclass = new customClass();
                customclass.setName(name);
                customclass.setId(id);
                customclass.setPlayer_perspectives(player_perspectivesArrayList);
                customclass.setCover("https://images.igdb.com/igdb/image/upload/t_1080p/" + image + ".jpg");
                customclass.setGame_modes(game_modeArrayList);
                customclass.setSummary(summary);
                customclass.setStoryline(storyline);
                customclass.setGenres(genreArrayList);
                customclass.setTotal_rating(rating);
                customclass.setTotal_rating_count(rating_count);
                customclass.setPublishers(pubArrayList);
                customclass.setDevelopers(devArrayList);
                customclass.setFirstReleaseDate(releaseUnix);
                customclass.setThemes(themeArrayList);
                customclass.setReleasedatecustom(releaseArrayList);
                customclass.setScreenshot(sceenShot);
                customclass.setWebsitecustomclasses(websiteArraylist);

                arrayList.add(customclass);


            }


        } catch (Exception e) {

            Log.d("error", e.toString() + "");

        }
        return arrayList;

    }


    public static ArrayList<customClass> getUpcomingJsonSEVEN(JSONArray response) {

        ArrayList<releaseDateCustom> releaseArrayList = null;
        ArrayList<String> devArrayList = null;
        ArrayList<String> sceenShot = null;
        ArrayList<String> pubArrayList = null;
        ArrayList<String> game_modeArrayList = null;
        ArrayList<String> player_perspectivesArrayList = null;
        ArrayList<String> themeArrayList = null;
        ArrayList<websiteCustomClass> websiteArraylist = null;

        ArrayList<String> genreArrayList = null;
        ArrayList<customClass> arrayList = new ArrayList<>();

        try {


            JSONArray currentEventArray = response;
            for (int i = 0; i < currentEventArray.length(); i++) {
                JSONObject currentEvent = currentEventArray.getJSONObject(i);
                String releaseUnix = "";
                if (currentEvent.has("first_release_date")) {
                    releaseUnix = currentEvent.getString("first_release_date");


                    if (getDaysLeft(releaseUnix) < 7) {


                        JSONObject cover = null;
                        String id = currentEvent.getString("id");


                        String rating = "";

                        String rating_count = "";
                        String summary = "", image = "";
                        String name = currentEvent.getString("name");
                        if (currentEvent.has("cover")) {
                            cover = currentEvent.getJSONObject("cover");
                            image = cover.getString("cloudinary_id");
                        }
                        if (currentEvent.has("summary")) {
                            summary = currentEvent.getString("summary");
                        }
                        String storyline = "";
                        if (currentEvent.has("storyline")) {
                            storyline = currentEvent.getString("storyline");
                        }


                        if (currentEvent.has("total_rating")) {
                            rating = currentEvent.getString("total_rating");
                        }
                        if (currentEvent.has("total_rating_count")) {
                            rating_count = currentEvent.getString("total_rating_count");
                        }
                        //  String popularity = currentEvent.getString("popularity");
                        // String category = currentEvent.getString("category");

                        if (currentEvent.has("developers")) {
                            devArrayList = new ArrayList<>();
                            JSONArray dev = currentEvent.getJSONArray("developers");
                            for (int j = 0; j < dev.length(); j++) {
                                JSONObject releaseEvent = dev.getJSONObject(j);
                                devArrayList.add(releaseEvent.getString("name"));


                            }
                        }
                        if (currentEvent.has("publishers")) {
                            pubArrayList = new ArrayList<>();
                            JSONArray pub = currentEvent.getJSONArray("publishers");
                            for (int k = 0; k < pub.length(); k++) {

                                JSONObject releaseEvent = pub.getJSONObject(k);
                                pubArrayList.add(releaseEvent.getString("name"));

                            }
                        }


                        if (currentEvent.has("game_modes")) {
                            game_modeArrayList = new ArrayList<>();
                            JSONArray game_mode = currentEvent.getJSONArray("game_modes");
                            for (int j = 0; j < game_mode.length(); j++) {
                                JSONObject releaseEvent = game_mode.getJSONObject(j);
                                game_modeArrayList.add(releaseEvent.getString("name"));
                            }
                        }


                        if (currentEvent.has("player_perspectives")) {

                            player_perspectivesArrayList = new ArrayList<>();
                            JSONArray player_per = currentEvent.getJSONArray("player_perspectives");
                            for (int k = 0; k < player_per.length(); k++) {
                                JSONObject releaseEvent = player_per.getJSONObject(k);
                                player_perspectivesArrayList.add(releaseEvent.getString("name"));
                            }
                        }


                        if (currentEvent.has("themes")) {
                            themeArrayList = new ArrayList<>();
                            JSONArray theme = currentEvent.getJSONArray("themes");
                            for (int j = 0; j < theme.length(); j++) {

                                JSONObject releaseEvent = theme.getJSONObject(j);

                                themeArrayList.add(releaseEvent.getString("name"));


                            }
                        }


                        if (currentEvent.has("genres")) {
                            genreArrayList = new ArrayList<>();
                            JSONArray genre = currentEvent.getJSONArray("genres");
                            for (int k = 0; k < genre.length(); k++) {
                                JSONObject releaseEvent = genre.getJSONObject(k);
                                genreArrayList.add(releaseEvent.getString("name"));


                            }
                        }


                        if (currentEvent.has("screenshots")) {

                            sceenShot = new ArrayList<>();
                            JSONArray screen = currentEvent.getJSONArray("screenshots");
                            for (int k = 0; k < screen.length(); k++) {
                                JSONObject releaseEvent = screen.getJSONObject(k);
                                sceenShot.add("https://images.igdb.com/igdb/image/upload/t_1080p/" + releaseEvent.getString("cloudinary_id") + ".jpg");
                            }
                        }


                        if (currentEvent.has("release_dates")) {
                            releaseArrayList = new ArrayList<>();
                            JSONArray releaseDate = currentEvent.getJSONArray("release_dates");
                            for (int k = 0; k < releaseDate.length(); k++) {

                                releaseDateCustom real = new releaseDateCustom();
                                JSONObject releaseEvent = releaseDate.getJSONObject(k);

                                real.setHuman(releaseEvent.getString("human"));
                                real.setPlatform(releaseEvent.getInt("platform"));
                                releaseArrayList.add(real);
                            }
                        }


                        if (currentEvent.has("websites")) {
                            websiteArraylist = new ArrayList<>();
                            JSONArray website = currentEvent.getJSONArray("websites");
                            for (int k = 0; k < website.length(); k++) {
                                websiteCustomClass real = new websiteCustomClass();
                                JSONObject releaseEvent = website.getJSONObject(k);
                                int category = releaseEvent.getInt("category");
                                String name12 = help.getWebsiteName(category);
                                if (name12 != null) {
                                    real.setCategory(category);
                                    real.setUrl(releaseEvent.getString("url"));
                                    websiteArraylist.add(real);
                                }

                            }
                        }

                        customClass customclass = new customClass();
                        customclass.setName(name);
                        customclass.setId(id);
                        customclass.setPlayer_perspectives(player_perspectivesArrayList);
                        customclass.setCover("https://images.igdb.com/igdb/image/upload/t_1080p/" + image + ".jpg");
                        customclass.setGame_modes(game_modeArrayList);
                        customclass.setSummary(summary);
                        customclass.setStoryline(storyline);
                        customclass.setGenres(genreArrayList);
                        customclass.setTotal_rating(rating);
                        customclass.setTotal_rating_count(rating_count);
                        customclass.setPublishers(pubArrayList);
                        customclass.setDevelopers(devArrayList);
                        customclass.setFirstReleaseDate(releaseUnix);
                        customclass.setThemes(themeArrayList);
                        customclass.setReleasedatecustom(releaseArrayList);
                        customclass.setScreenshot(sceenShot);
                        customclass.setWebsitecustomclasses(websiteArraylist);

                        arrayList.add(customclass);

                    }
                }
            }


        } catch (Exception e) {

            Log.d("error", e.toString() + "");

        }
        return arrayList;

    }


    public static ArrayList<customClass> getSearchJson(JSONArray response) {


        ArrayList<customClass> arrayList = new ArrayList<>();

        try {


            JSONArray currentEventArray = response;
            for (int i = 0; i < currentEventArray.length(); i++) {
                JSONObject currentEvent = currentEventArray.getJSONObject(i);

                String id = currentEvent.getString("id");
                String name = currentEvent.getString("name");

                customClass customclass = new customClass();
                customclass.setName(name);
                customclass.setId(id);

                arrayList.add(customclass);


            }


        } catch (Exception e) {

            Log.d("error", e.toString() + "");

        }
        return arrayList;

    }

}
