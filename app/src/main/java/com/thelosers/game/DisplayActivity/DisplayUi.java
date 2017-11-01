package com.thelosers.game.DisplayActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.displayScreenAdapter;
import com.thelosers.game.Adapters.releaseDateCustom;
import com.thelosers.game.Adapters.websiteCustomAdapter;
import com.thelosers.game.Adapters.websiteCustomClass;
import com.thelosers.game.HelperClasses.UtilData;
import com.thelosers.game.HelperClasses.help;
import com.thelosers.game.R;
import com.thelosers.game.SharedPreferences.sharedPref;
import com.thelosers.game.StartingActivities.SignIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.thelosers.game.HelperClasses.help.addToCalender;
import static com.thelosers.game.HelperClasses.help.getPlatform;
import static com.thelosers.game.HelperClasses.help.shareTextUrl;

public class DisplayUi extends AppCompatActivity {

    RecyclerView recyclerView;
    RatingBar ratingBar;
    ImageView coverImageView, heartImageView, addReminderImageview;
    TextView mReleaseDateTextView, mToolbarNAme, mReleasePlatFormTextView, mGameNameTextView, mDescriptionTextView, mStoryTellerTextView, mDevelopersTextView, mPublishersTextView, mGamesModesTextView, mGenreTextView, mThemeTextView, mPlayerPerspectivesTextView, mTotalUsersTextView;
    String cover, ReleaseDate, GameName, Description, StoryTeller, TotalUsers, id,username,Rating;
    boolean isHearted = false;
    private DatabaseReference mDatabase;
    ArrayList<String> devArrayList, pubArrayList;
    ArrayList<String> playArrayList, gameArrayList, genArrayList, themeArrayList;
    ArrayList<releaseDateCustom> relArrayList;
    ArrayList<String> screenArrayList;
    ArrayList<websiteCustomClass> webArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ui);

        FirebaseApp.initializeApp(getApplicationContext());
        findViewById();
        getSharefPref();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        UtilData utilData = getIntent().getParcelableExtra("data");
        if (utilData != null) {
            id = utilData.id;
            IsAlreadyHearted();
            GameName = utilData.name;
            Description = utilData.summary;
            StoryTeller = utilData.storyline;
            Rating = utilData.total_rating;
            TotalUsers = utilData.total_rating_count;
            cover = utilData.cover;

            devArrayList = utilData.developers;
            pubArrayList = utilData.publishers;
            playArrayList = utilData.player_perspectives;
            gameArrayList = utilData.game_modes;
            genArrayList = utilData.genres;
            themeArrayList = utilData.themes;
            relArrayList = utilData.releasedatecustom;
            screenArrayList = utilData.screenshot_cloudinary_id;
            webArrayList = utilData.websitecustomclasses;

            ReleaseDate = utilData.firstReleaseDate;

            Log.d("check",ReleaseDate+"");
            if (screenArrayList != null)
                setScreenShots(screenArrayList);

            if (devArrayList != null)
                mDevelopersTextView.setText(getValue(devArrayList).trim());

            if (pubArrayList != null)
                mPublishersTextView.setText(getValue(pubArrayList).trim());
            if (playArrayList != null)
                mPlayerPerspectivesTextView.setText(getValue(playArrayList).trim());
            if (gameArrayList != null)
                mGamesModesTextView.setText(getValue(gameArrayList).trim());
            if (genArrayList != null)
                mGenreTextView.setText(getValue(genArrayList).trim());

            if (themeArrayList != null)
                mThemeTextView.setText(getValue(themeArrayList).trim());

            if (relArrayList != null)
                mReleasePlatFormTextView.setText(getReleaseDate(relArrayList).trim());

            if (webArrayList != null)
                webSites(webArrayList);

            setText();
            if (help.checkDate(help.getReleaseDate(ReleaseDate))) {
                addReminderImageview.setVisibility(View.VISIBLE);
            }
        }
    }

    private void findViewById() {
        devArrayList = new ArrayList<>();
        pubArrayList = new ArrayList<>();
        playArrayList = new ArrayList<>();
        gameArrayList = new ArrayList<>();
        genArrayList = new ArrayList<>();
        themeArrayList = new ArrayList<>();
        relArrayList = new ArrayList<>();
        screenArrayList = new ArrayList<>();
        webArrayList = new ArrayList<>();

        addReminderImageview = (ImageView) findViewById(R.id.displayAddReminder);
        heartImageView = (ImageView) findViewById(R.id.heartwhiteDisplay);
        mToolbarNAme = (TextView) findViewById(R.id.displayGameNAme);
        mReleasePlatFormTextView = (TextView) findViewById(R.id.relaseDateCompleteDisplay);
        recyclerView = (RecyclerView) findViewById(R.id.displayRecycler);
        ratingBar = (RatingBar) findViewById(R.id.displayRating);
        coverImageView = (ImageView) findViewById(R.id.coverDisplay);
        mReleaseDateTextView = (TextView) findViewById(R.id.releaseDateDisplay);
        mGameNameTextView = (TextView) findViewById(R.id.displayName);
        mDescriptionTextView = (TextView) findViewById(R.id.descripitonCompleteDisplay);
        mStoryTellerTextView = (TextView) findViewById(R.id.storyTellerCompleteDisplay);
        mDevelopersTextView = (TextView) findViewById(R.id.developerTextDisplay);
        mPublishersTextView = (TextView) findViewById(R.id.publisherTextDisplay);
        mGamesModesTextView = (TextView) findViewById(R.id.GamesModesTextDisplay);
        mGenreTextView = (TextView) findViewById(R.id.GenreTextDisplay);
        mThemeTextView = (TextView) findViewById(R.id.ThemeTextDisplay);
        mPlayerPerspectivesTextView = (TextView) findViewById(R.id.playerPerspectivesTextDisplay);
        mTotalUsersTextView = (TextView) findViewById(R.id.displayCount);


        addReminderImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String unixSeconds = ReleaseDate.substring(0, ReleaseDate.length() - 3);
                addToCalender(getApplicationContext(), GameName, Description, ReleaseDate, ReleaseDate);
            }
        });
        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHearted) {
                    isHearted = false;
                    removeWishList();
                } else {
                    isHearted = true;
                    updateWishList();
                    updateBestGame();
                }
            }
        });

    }

    private void setText() {

        try {
            float d = (float) ((Float.parseFloat(Rating) * 5) / 100);
            ratingBar.setRating(d);
        } catch (Exception e) {
            ratingBar.setRating(0);
        }
        mTotalUsersTextView.setText("( " + (TotalUsers + " users") + " )");
        mStoryTellerTextView.setText(StoryTeller);
        mToolbarNAme.setText(GameName);
        mGameNameTextView.setText(GameName);
        mDescriptionTextView.setText(Description);
        mTotalUsersTextView.setText(TotalUsers + " users");
        Picasso.with(getApplicationContext())
                .load(cover)
                .into(coverImageView);

        mReleaseDateTextView.setText(help.getReleaseDate(ReleaseDate));

    }


    private String getReleaseDate(ArrayList<releaseDateCustom> arrayList) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayList.size(); i++) {
            String date = arrayList.get(i).getHuman();
            String platform = getPlatform(arrayList.get(i).getPlatform());
            sb.append(date).append(" ( ").append(platform).append(" ) ").append("\n");

        }

        return sb.toString();
    }


    private String getValue(ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder();
        if ((arrayList.size() > 0 && !arrayList.isEmpty())) {


            for (String count : arrayList) {
                sb.append(count).append("\n");
            }


        }
        return sb.toString();
    }


    private void setScreenShots(ArrayList<String> arrayList) {
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(DisplayUi.this, LinearLayoutManager.HORIZONTAL, false);

        if (arrayList.size() > 0) {
            displayScreenAdapter horizontalAdapter = new displayScreenAdapter(getApplicationContext(), arrayList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            recyclerView.setAdapter(horizontalAdapter);
        }

    }

    public void onClickShareGame(View view) {
        shareTextUrl(getApplicationContext(), GameName + " in", "");
    }

    public static class ListUtils {
        public static void setDynamicHeight(GridView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getColumnWidth() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    public void onClickBackActivity(View view) {
        finish();
    }

    private void webSites(ArrayList<websiteCustomClass> arrayList) {
        GridView horizontal_recycler_view = (GridView) findViewById(R.id.websitedisplay);
        websiteCustomAdapter horizontalAdapter = new websiteCustomAdapter(getApplicationContext(), arrayList);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
        ListUtils.setDynamicHeight(horizontal_recycler_view);
    }

    private void getSharefPref() {
        sharedPref sharedpref = new sharedPref(getApplicationContext());
        HashMap<String, String> user = sharedpref.getUserDetails();

        if (sharedpref.checkLogin()) {
        if (user != null) {
            username = user.get(sharedpref.KEY_EMAIL);
            username = username.replace("@", "_").replace(".", "_");

        }}else {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        }
    }

    private void updateWishList() {

        customClass customclass = new customClass(id, GameName, ReleaseDate, Description, StoryTeller, Rating, TotalUsers, cover, webArrayList, screenArrayList, relArrayList, themeArrayList, genArrayList, gameArrayList, playArrayList, pubArrayList, devArrayList);

        mDatabase.child("users/wishlist").child(username).child(id).setValue(customclass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    heartImageView.setImageResource(R.drawable.heartred);
                } else {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void removeWishList() {

        mDatabase.child("users/wishlist").child(username).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    heartImageView.setImageResource(R.drawable.heartwhite);
                } else {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateBestGame() {
        Map<String, String> childUpdates = new HashMap<>();
        childUpdates.put("username", username);
        mDatabase.child("/bestgame/selectedbyuser/").child(id).push().setValue(childUpdates);

    }

    private void IsAlreadyHearted() {
        mDatabase.child("users/wishlist").child(username).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    isHearted = true;
                    heartImageView.setImageResource(R.drawable.heartred);
                } else {
                    isHearted = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
