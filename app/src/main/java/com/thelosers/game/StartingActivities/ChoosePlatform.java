package com.thelosers.game.StartingActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.thelosers.game.Home.HomeU;
import com.thelosers.game.R;
import com.thelosers.game.SharedPreferences.PlatformSharedPref;

public class ChoosePlatform extends AppCompatActivity {

    PlatformSharedPref platformSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_platform);

        platformSharedPref = new PlatformSharedPref(getApplicationContext());

        if (platformSharedPref.checkLogin()) {
            startActivity(new Intent(getApplicationContext(), HomeU.class));
            finish();
        }

    }

    public void onClickChooseXbox(View view) {
        platformSharedPref.createSession("Xbox");
        startActivity(new Intent(getApplicationContext(), HomeU.class));
        finish();
    }

    public void onClickChooseWindow(View view) {
        platformSharedPref.createSession("Window");
        startActivity(new Intent(getApplicationContext(), HomeU.class));
        finish();
    }

    public void onClickChoosePlaystation(View view) {
        platformSharedPref.createSession("Playstation");
        startActivity(new Intent(getApplicationContext(), HomeU.class));
        finish();
    }

    public void onClickChooseNintendo(View view) {
        platformSharedPref.createSession("Nintendo");
        startActivity(new Intent(getApplicationContext(), HomeU.class));
        finish();
    }

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Choose your favourite platform", Toast.LENGTH_LONG).show();
    }


}
