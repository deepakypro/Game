package com.thelosers.game.Home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.squareup.picasso.Picasso;
import com.thelosers.game.AboutUs.Aboutme;
import com.thelosers.game.AboutUs.Credits;
import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.upcomingAdapter;
import com.thelosers.game.DisplayActivity.DisplayUi;
import com.thelosers.game.HelperClasses.CircleTransform;
import com.thelosers.game.HelperClasses.ReferralUrl;
import com.thelosers.game.HelperClasses.UtilData;
import com.thelosers.game.HelperClasses.jsonClass;
import com.thelosers.game.R;
import com.thelosers.game.Search.SearchUi;
import com.thelosers.game.SharedPreferences.PlatformSharedPref;
import com.thelosers.game.SharedPreferences.sharedPref;
import com.thelosers.game.StartingActivities.ChoosePlatform;
import com.thelosers.game.StartingActivities.SignIn;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import static com.thelosers.game.EnableMultiDex.context;
import static com.thelosers.game.HelperClasses.help.getChoosePlatformLogo;
import static com.thelosers.game.HelperClasses.help.getStartEndOFWeek;
import static com.thelosers.game.HelperClasses.help.isNetworkAvailable;
import static com.thelosers.game.HelperClasses.help.openWebPage;

public class HomeU extends AppCompatActivity {

    String platform;

    ArrayList<customClass> upcomingArrayList, todayArraylist;
    ImageView mProfileImageView, mTodayImageview;
    TextView mNameTextView, mEmailTextView, mTodayName;
    APIWrapper wrapper;
    ImageButton mPlatformButton;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_u);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        wrapper = new APIWrapper(getApplicationContext(), getResources().getString(R.string.google_maps_key_one));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getAllViewById();

        upcomingArrayList = new ArrayList<>();
        todayArraylist = new ArrayList<>();

        checkInternetConnection();
    }

    private void checkInternetConnection() {

        ScrollView scrollView = (ScrollView) findViewById(R.id.homeShowInternet);
        View view = findViewById(R.id.homeNoInternet);
        ImageView imageView = (ImageView) view.findViewById(R.id.noInternetImageView);
        imageView.setImageResource(R.drawable.nointernetconnection);
        TextView textView = (TextView) view.findViewById(R.id.noInternetPlayAgain);
        if (isNetworkAvailable(getApplicationContext())) {
            view.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            setToday();
            setUpcoming();
            setPopular();

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

    private void getAllViewById() {
        View view = (View) findViewById(R.id.home_view);
        mProfileImageView = (ImageView) view.findViewById(R.id.home_navigation_profile_picture);
        mNameTextView = (TextView) view.findViewById(R.id.home_navigation_profile_name);
        mEmailTextView = (TextView) view.findViewById(R.id.home_navigation_profile_email);
        mTodayImageview = (ImageView) findViewById(R.id.todayPickImageView);
        mTodayName = (TextView) findViewById(R.id.todayPickName);
        mPlatformButton = (ImageButton) findViewById(R.id.homePlatformLogo);

        mPlatformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!platform.equals("Window")) {
                    showPlatformDialog();
                }

            }
        });
        TextView mWishListTextView = (TextView) findViewById(R.id.home_navigation_wishlist);
        mWishListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyWishlistUi.class));
            }
        });

        TextView mRecentTextView = (TextView) findViewById(R.id.home_navigation_recent);
        mRecentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListUi.class);
                intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.LASTWEEK);
                startActivity(intent);
            }
        });

        TextView mTopTextView = (TextView) findViewById(R.id.home_navigation_top100);
        mTopTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListUi.class);
                intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.TOP100);
                startActivity(intent);
            }
        });

        TextView mChooseTextView = (TextView) findViewById(R.id.home_navigation_choose);
        mChooseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChoosePlatform.class);

                startActivity(intent);
                finish();
            }
        });

        TextView mAboutTextView = (TextView) findViewById(R.id.home_navigation_About);
        mAboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Aboutme.class);

                startActivity(intent);
            }
        });

        TextView mCreditsTextView = (TextView) findViewById(R.id.home_navigation_credits);
        mCreditsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Credits.class);

                startActivity(intent);
            }
        });

        TextView mLogoutTextView = (TextView) findViewById(R.id.home_navigation_logout);
        mLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                sharedPref sharedpref = new sharedPref(getApplicationContext());
                                sharedpref.logoutUser();
                                PlatformSharedPref platformSharedPref = new PlatformSharedPref(getApplicationContext());
                                platformSharedPref.logoutUser();
                                startActivity(new Intent(getApplicationContext(), SignIn.class));
                                finish();
                            }
                        });
            }
        });

        getSharefPref();

    }

    public void onclick_menu_show(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }


    private void getSharefPref() {
        sharedPref sharedpref = new sharedPref(getApplicationContext());
        HashMap<String, String> user = sharedpref.getUserDetails();

        if (sharedpref.checkLogin()) {
            if (user != null) {
                Picasso.with(getApplicationContext())
                        .load(user.get(sharedpref.KEY_IMAGE_URL))
                        .transform(new CircleTransform())
                        .into(mProfileImageView);

                mNameTextView.setText(user.get(sharedpref.KEY_NAME));
                mEmailTextView.setText(user.get(sharedpref.KEY_EMAIL));
            }
        } else {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        }


        PlatformSharedPref platformSharedPref = new PlatformSharedPref(getApplicationContext());
        HashMap<String, String> platformHashMap = platformSharedPref.getUserDetails();

        if (platformSharedPref.checkLogin()) {

            if (platformHashMap != null) {


                platform = platformHashMap.get(platformSharedPref.KEY_NAME);
                int mode = getChoosePlatformLogo(platform);
                mPlatformButton.setImageResource(mode);
            }
        } else {
            startActivity(new Intent(getApplicationContext(), ChoosePlatform.class));
            finish();
        }
    }


    private void setUpcoming() {

        String startingweekday = "", endweekday = "";
        String returndate = getStartEndOFWeek();

        StringTokenizer stringTokenizer = new StringTokenizer(returndate, "&%&%&%");
        while (stringTokenizer.hasMoreTokens()) {
            startingweekday = stringTokenizer.nextToken();
            endweekday = stringTokenizer.nextToken();

        }

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.homeUpcomingProgrebar);
        progressBar.setVisibility(View.VISIBLE);

        Parameters parameters = new Parameters()
                .addLimit("10")
                .addFilter("[release_dates.platform][any]=48,49,6")
                .addFilter("[release_dates.date][gte]=" + startingweekday)
                .addFilter("[release_dates.date][lte]=" + endweekday)
                .addOrder("release_dates.date:desc:min")
                .addOrder("popularity:desc")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


        wrapper.games(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {

                progressBar.setVisibility(View.INVISIBLE);
                upcomingArrayList = jsonClass.getUpcomingJsonSEVEN(jsonArray);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeU.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView horizontal_recycler_view = (RecyclerView) findViewById(R.id.homeRecycler);
                upcomingAdapter horizontalAdapter = new upcomingAdapter(getApplicationContext(), upcomingArrayList, ReferralUrl.UPCOMING);
                horizontal_recycler_view.setHasFixedSize(true);
                horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

                horizontal_recycler_view.setAdapter(horizontalAdapter);

            }

            @Override
            public void onError(VolleyError volleyError) {

                progressBar.setVisibility(View.INVISIBLE);
                Log.d("error", volleyError + "");
            }
        });
    }


    private void setToday() {

        Parameters parameters = new Parameters()
                .addLimit("1")
                .addIds("36662")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


        wrapper.games(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {

                todayArraylist = jsonClass.getUpcomingJson(jsonArray);

                if (todayArraylist.size() > 0) {

                    Picasso.with(getApplicationContext())
                            .load(todayArraylist.get(0).getCover())
                            .fit()
                            .into(mTodayImageview);


                    mTodayName.setText(todayArraylist.get(0).getName());

                    mTodayImageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), DisplayUi.class);
                            UtilData utilData = new UtilData(todayArraylist.get(0).getId(), todayArraylist.get(0).getName(), todayArraylist.get(0).getSummary(), todayArraylist.get(0).getStoryline(), todayArraylist.get(0).getTotal_rating(), todayArraylist.get(0).getTotal_rating_count(), todayArraylist.get(0).getCover(), todayArraylist.get(0).getDevelopers(), todayArraylist.get(0).getPublishers(), todayArraylist.get(0).getPlayer_perspectives(), todayArraylist.get(0).getGame_modes(), todayArraylist.get(0).getGenres(), todayArraylist.get(0).getThemes(), todayArraylist.get(0).getReleasedatecustom(), todayArraylist.get(0).getScreenshot(), todayArraylist.get(0).getWebsitecustomclasses(), todayArraylist.get(0).getFirstReleaseDate());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("data", utilData);
                            startActivity(intent);
                        }
                    });

                }

            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d("error", volleyError + "");
            }
        });
    }

    private void setPopular() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.homePopularProgressbar);
        progressBar.setVisibility(View.VISIBLE);

        Parameters parameters = new Parameters()
                .addLimit("10")
                .addFilter("[release_dates.platform][any]=48,49,6")
                .addOrder("popularity:desc")
                .addFilter("[rating][gte]=80")
                .addFilter("[total_rating_count][gte]=100")
                .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


        wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                progressBar.setVisibility(View.INVISIBLE);
                ArrayList<customClass> arrayList = jsonClass.getUpcomingJson(jsonArray);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeU.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView horizontal_recycler_view = (RecyclerView) findViewById(R.id.popularhomeRecycler);
                upcomingAdapter horizontalAdapter = new upcomingAdapter(getApplicationContext(), arrayList, ReferralUrl.DATA);
                horizontal_recycler_view.setHasFixedSize(true);
                horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                horizontal_recycler_view.setAdapter(horizontalAdapter);
            }

            @Override
            public void onError(VolleyError volleyError) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("error", volleyError + "");
            }
        });

    }


    public void onClickSearchActivity(View view) {
        startActivity(new Intent(getApplicationContext(), SearchUi.class));
        // finish();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public void onclickMoreUpcoming(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.UPCOMING);
        startActivity(intent);
    }

    public void onClickMorePopular(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.POPULAR);
        startActivity(intent);
    }

    public void onClickXboxSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.XBOX);
        startActivity(intent);
    }

    public void onClickNintendoSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.NINTENDO);
        startActivity(intent);
    }

    public void onClickWindowSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.WINDOW);
        startActivity(intent);
    }

    public void onClickPlaystationSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), ListUi.class);
        intent.putExtra(ReferralUrl.LISTACTIVITYNAME, ReferralUrl.PLAYSTATION);
        startActivity(intent);
    }


    public void showPlatformDialog() {
        final Dialog dialog = new Dialog(HomeU.this);

        if (platform.equals(ReferralUrl.XBOX)) {
            dialog.setContentView(R.layout.platform_information_xbox);
        } else if (platform.equals(ReferralUrl.NINTENDO)) {
            dialog.setContentView(R.layout.platform_information_nintendo);
        } else if (platform.equals(ReferralUrl.PLAYSTATION)) {
            dialog.setContentView(R.layout.platform_information_playstation);
        }
        dialog.setCancelable(false);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        ImageButton imageButton = (ImageButton) dialog.findViewById(R.id.platformClose);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView textView = (TextView) dialog.findViewById(R.id.platformOfficialSite);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (platform.equals(ReferralUrl.XBOX)) {
                    openWebPage(context, "https://www.xbox.com");
                } else if (platform.equals(ReferralUrl.NINTENDO)) {
                    openWebPage(context, "http://www.nintendo.com/switch");
                } else if (platform.equals(ReferralUrl.PLAYSTATION)) {
                    openWebPage(context, "https://www.playstation.com");
                }

            }
        });


        dialog.show();
    }
}
