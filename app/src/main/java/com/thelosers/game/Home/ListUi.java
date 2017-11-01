package com.thelosers.game.Home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.androidnetworking.AndroidNetworking;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.listAdapter;
import com.thelosers.game.HelperClasses.ReferralUrl;
import com.thelosers.game.HelperClasses.jsonClass;
import com.thelosers.game.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.thelosers.game.HelperClasses.help.getLastEndOFWeek;
import static com.thelosers.game.HelperClasses.help.getStartEndOFWeek;
import static com.thelosers.game.HelperClasses.help.isNetworkAvailable;

public class ListUi extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar progressBar;
    Parameters parameters;
    TextView mActivityName;
    ArrayList<customClass> arrayList;
    Spinner spinner;
    String activityName, ItemSelected;
    APIWrapper wrapper;
    listAdapter horizontalAdapter;
    final int numberOfColumns = 2;
    RecyclerView horizontal_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ui);
        arrayList = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.upcomingSpinner);
        mActivityName = (TextView) findViewById(R.id.upcomingListUi);
        progressBar = (ProgressBar) findViewById(R.id.listProgressBar);
        wrapper = new APIWrapper(getApplicationContext(), getResources().getString(R.string.google_maps_key_one));
        spinner.setOnItemSelectedListener(this);
        checkInternetConnection();

    }


    private void checkInternetConnection() {

        RelativeLayout scrollView = (RelativeLayout) findViewById(R.id.homeInternet);
        View view = findViewById(R.id.homeNoInternet);
        ImageView imageView = (ImageView) view.findViewById(R.id.noInternetImageView);
        imageView.setImageResource(R.drawable.nointernetconnection);
        TextView textView = (TextView) view.findViewById(R.id.noInternetPlayAgain);
        if (isNetworkAvailable(getApplicationContext())) {
            view.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            AndroidNetworking.initialize(getApplicationContext());
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {

                activityName = bundle.getString(ReferralUrl.LISTACTIVITYNAME);
                mActivityName.setText(activityName);

                if (activityName.equals(ReferralUrl.UPCOMING)) {
                    setUpcoming();
                    spinner.setVisibility(View.INVISIBLE);
                } else if (activityName.equals(ReferralUrl.POPULAR)) {
                    setPopular();
                    spinner.setVisibility(View.INVISIBLE);
                    //  setNormalFilter();
                } else if (activityName.equals(ReferralUrl.XBOX)) {
                    //setPlatform(49, ReferralUrl.UPCOMING);
                    setPlatformFilter();
                } else if (activityName.equals(ReferralUrl.PLAYSTATION)) {
                    // setPlatform(48, ReferralUrl.UPCOMING);
                    setPlatformFilter();
                } else if (activityName.equals(ReferralUrl.NINTENDO)) {
                    // setPlatform(130, ReferralUrl.UPCOMING);
                    setPlatformFilter();
                } else if (activityName.equals(ReferralUrl.WINDOW)) {
                    // setPlatform(6, ReferralUrl.UPCOMING);
                    setPlatformFilter();
                } else if (activityName.equals(ReferralUrl.LASTWEEK)) {
                    setLastWeek();
                    spinner.setVisibility(View.INVISIBLE);
                } else if (activityName.equals(ReferralUrl.TOP100)) {
                    setTop100();
                    spinner.setVisibility(View.INVISIBLE);
                }

            }

        } else {

            scrollView.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.nointernetconnection);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkInternetConnection();
                }
            });
        }
    }

    private void setPlatformFilter() {
        List<String> days = new ArrayList<String>();

        days.add(ReferralUrl.POPULAR);
        days.add(ReferralUrl.UPCOMING);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    private void setUpcoming() {
        String startingweekday = "", endweekday = "";
        String returndate = getStartEndOFWeek();

        StringTokenizer stringTokenizer = new StringTokenizer(returndate, "&%&%&%");
        while (stringTokenizer.hasMoreTokens()) {
            startingweekday = stringTokenizer.nextToken();
            endweekday = stringTokenizer.nextToken();

        }
        setUpcomingLastWeek(startingweekday, endweekday);
    }

    private void setLastWeek() {
        String startingweekday = "", endweekday = "";
        String returndate = getLastEndOFWeek();

        StringTokenizer stringTokenizer = new StringTokenizer(returndate, "&%&%&%");
        while (stringTokenizer.hasMoreTokens()) {
            startingweekday = stringTokenizer.nextToken();
            endweekday = stringTokenizer.nextToken();

        }
        setUpcomingLastWeek(endweekday, startingweekday);
    }

    private void setUpcomingLastWeek(String startingweekday, String endweekday) {
        parameters = new Parameters()
                .addLimit("50")
                .addFilter("[release_dates.platform][any]=48,49,6")
                .addFilter("[release_dates.date][gt]=" + startingweekday)
                .addFilter("[release_dates.date][lt]=" + endweekday)
                .addOrder("release_dates.date:desc:min")
                .addOrder("release_dates.date:asc")
                .addOrder("popularity:desc")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


        setWrapperSEVEN();
    }


    private void setPlatform(String code, String filter) {
        String startingweekday = "", endweekday = "";
        String returndate = getStartEndOFWeek();
        StringTokenizer stringTokenizer = new StringTokenizer(returndate, "&%&%&%");
        while (stringTokenizer.hasMoreTokens()) {
            startingweekday = stringTokenizer.nextToken();
            endweekday = stringTokenizer.nextToken();
        }

        if (filter.equals(ReferralUrl.UPCOMING)) {
            parameters = new Parameters()
                    .addLimit("50")
                    .addFilter("[release_dates.platform][any]=" + code)
                    .addFilter("[release_dates.date][gte]=" + startingweekday)
                    .addFilter("[release_dates.date][lte]=" + endweekday)
                    .addOrder("release_dates.date:desc:min")
                    .addOrder("release_dates.date:asc")

                    .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                    .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");

            setWrapperSEVEN();
        } else if (filter.equals(ReferralUrl.POPULAR)) {
            parameters = new Parameters()
                    .addLimit("50")
                    .addFilter("[release_dates.platform][any]=" + code)
                    .addOrder("popularity:desc")
                    .addFilter("[rating][gte]=80")
                    .addFilter("[total_rating_count][gte]=100")
                    .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                    .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");

            setWrapperNormalPlatform();
        }

    }

    private void setTop100() {

        parameters = new Parameters()
                .addLimit("50")
                .addFilter("[release_dates.platform][any]=48,49,6")
                .addOrder("rating:desc")
                .addFilter("[rating][gte]=80")
                .addFilter("[total_rating_count][gte]=100")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


        showProgressBar();
        wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("success", jsonArray + "");
                arrayList = jsonClass.getUpcomingJson(jsonArray);

                hideProgressBar();
                if (arrayList.size() > 0) {
                    horizontal_recycler_view = (RecyclerView) findViewById(R.id.recyclerListUi);
                    horizontalAdapter = new listAdapter(getApplicationContext(), arrayList, activityName);
                    horizontal_recycler_view.setHasFixedSize(true);
                    horizontal_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    horizontal_recycler_view.setAdapter(horizontalAdapter);
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideProgressBar();

            }
        });

    }

    private void setPopular() {

        parameters = new Parameters()
                .addLimit("50")
                .addFilter("[release_dates.platform][any]=48,49,6")
                .addOrder("popularity:desc")
                .addFilter("[rating][gte]=80")
                .addFilter("[total_rating_count][gte]=100")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");

        showProgressBar();

        setWrapperNormal();

    }

    private void setWrapperNormalPlatform() {
        showProgressBar();

        wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                arrayList = jsonClass.getUpcomingJson(jsonArray);

                hideProgressBar();
                if (arrayList.size() > 0) {
                    horizontal_recycler_view = (RecyclerView) findViewById(R.id.recyclerListUi);
                    horizontalAdapter = new listAdapter(getApplicationContext(), arrayList, ReferralUrl.DATA);
                    horizontal_recycler_view.setHasFixedSize(true);
                    horizontal_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    horizontal_recycler_view.setAdapter(horizontalAdapter);
                }

            }

            @Override
            public void onError(VolleyError volleyError) {
                hideProgressBar();
            }
        });
    }

    private void setWrapperNormal() {

        wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                arrayList = jsonClass.getUpcomingJson(jsonArray);
                hideProgressBar();
                if (arrayList.size() > 0) {
                    horizontal_recycler_view = (RecyclerView) findViewById(R.id.recyclerListUi);
                    horizontalAdapter = new listAdapter(getApplicationContext(), arrayList, activityName);
                    horizontal_recycler_view.setHasFixedSize(true);
                    horizontal_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    horizontal_recycler_view.setAdapter(horizontalAdapter);
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideProgressBar();
            }
        });
    }

    private void setWrapperSEVEN() {

        showProgressBar();
        wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                arrayList = jsonClass.getUpcomingJsonSEVEN(jsonArray);
                hideProgressBar();
                if (arrayList.size() > 0) {
                    horizontal_recycler_view = (RecyclerView) findViewById(R.id.recyclerListUi);
                    horizontalAdapter = new listAdapter(getApplicationContext(), arrayList, activityName);
                    horizontal_recycler_view.setHasFixedSize(true);
                    horizontal_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    horizontal_recycler_view.setAdapter(horizontalAdapter);
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideProgressBar();

            }
        });
    }

    public void onClickBackArrow(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ItemSelected = adapterView.getItemAtPosition(position).toString();
        if (activityName.equals(ReferralUrl.XBOX)) {
            clearArrayList();
            setPlatform("11,12,49", ItemSelected);
        } else if (activityName.equals(ReferralUrl.PLAYSTATION)) {
            clearArrayList();
            setPlatform("48,7,8,9", ItemSelected);
        } else if (activityName.equals(ReferralUrl.NINTENDO)) {
            clearArrayList();
            setPlatform("130", ItemSelected);
        } else if (activityName.equals(ReferralUrl.WINDOW)) {
            clearArrayList();
            setPlatform("6", ItemSelected);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void clearArrayList() {
        if (!arrayList.isEmpty()) {
            horizontalAdapter.notifyDataSetChanged();
            arrayList.clear();
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
