package com.thelosers.game.Home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.listAdapter;
import com.thelosers.game.Adapters.releaseDateCustom;
import com.thelosers.game.Adapters.websiteCustomClass;
import com.thelosers.game.R;
import com.thelosers.game.SharedPreferences.sharedPref;

import java.util.ArrayList;
import java.util.HashMap;

import static com.thelosers.game.HelperClasses.help.isNetworkAvailable;

public class MyWishlistUi extends AppCompatActivity {

    int numberOfColumns = 2;
    private DatabaseReference mDatabase;
    String username;

    ArrayList<customClass> arrayList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui);

        FirebaseApp.initializeApp(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setFindViewById();
        arrayList = new ArrayList<>();
    }

    private void setFindViewById() {
        progressBar = (ProgressBar) findViewById(R.id.wishlistProgressbar);
        getSharefPref();
    }


    private void getSharefPref() {
        sharedPref sharedpref = new sharedPref(getApplicationContext());
        HashMap<String, String> user = sharedpref.getUserDetails();

        if (user != null) {
            username = user.get(sharedpref.KEY_EMAIL);
            username = username.replace("@", "_").replace(".", "_");
            checkInternetConnection();
        }
    }


    private void checkInternetConnection() {
        RelativeLayout scrollView = (RelativeLayout) findViewById(R.id.homeShowInternetOne);
        View view = findViewById(R.id.homeNoInternet);
        ImageView imageView = (ImageView) view.findViewById(R.id.noInternetImageView);
        imageView.setImageResource(R.drawable.nointernetconnection);
        TextView textView = (TextView) view.findViewById(R.id.noInternetPlayAgain);
        if (isNetworkAvailable(getApplicationContext())) {
            view.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            getUserWishlist(username);
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

    private void getUserWishlist(String username) {
        showProgressBar();
        mDatabase.child("users/wishlist").child(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                customClass customclass = new customClass();
                try {
                    customclass.setName((String) dataSnapshot.child("name").getValue());
                    customclass.setId((String) dataSnapshot.child("id").getValue());
                    customclass.setPlayer_perspectives((ArrayList<String>) dataSnapshot.child("player_perspectives").getValue());
                    customclass.setCover((String) dataSnapshot.child("cover").getValue());
                    customclass.setGame_modes((ArrayList<String>) dataSnapshot.child("game_modes").getValue());
                    customclass.setSummary((String) dataSnapshot.child("summary").getValue());
                    customclass.setStoryline((String) dataSnapshot.child("storyline").getValue());
                    customclass.setGenres((ArrayList<String>) dataSnapshot.child("genres").getValue());
                    customclass.setTotal_rating((String) dataSnapshot.child("total_rating").getValue());
                    customclass.setTotal_rating_count((String) dataSnapshot.child("total_rating_count").getValue());
                    customclass.setPublishers((ArrayList<String>) dataSnapshot.child("publishers").getValue());
                    customclass.setDevelopers((ArrayList<String>) dataSnapshot.child("developers").getValue());
                    customclass.setFirstReleaseDate((String) dataSnapshot.child("firstReleaseDate").getValue());
                    customclass.setThemes((ArrayList<String>) dataSnapshot.child("themes").getValue());
                    customclass.setReleasedatecustom((ArrayList<releaseDateCustom>) dataSnapshot.child("releasedatecustom").getValue());
                    customclass.setScreenshot((ArrayList<String>) dataSnapshot.child("screenshot_cloudinary_id").getValue());
                    customclass.setWebsitecustomclasses((ArrayList<websiteCustomClass>) dataSnapshot.child("websitecustomclasses").getValue());

                } catch (Exception e) {
                    hideProgressBar();
                }

                arrayList.add(customclass);
                hideProgressBar();
                if (arrayList.size() > 0) {
                    RecyclerView horizontal_recycler_view = (RecyclerView) findViewById(R.id.mywishlistRecycler);
                    listAdapter horizontalAdapter = new listAdapter(getApplicationContext(), arrayList, "wishlist");
                    horizontal_recycler_view.setHasFixedSize(true);
                    horizontal_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    horizontal_recycler_view.setAdapter(horizontalAdapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressBar();
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClickBackArrowProfile(View view) {
        finish();

    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
