package com.thelosers.game.AboutUs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.thelosers.game.HelperClasses.help;
import com.thelosers.game.R;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void onClickCreditsClose(View view) {
        finish();
    }

    public void onclickOpenIGDB(View view) {
        help.openWebPage(getApplicationContext(),"https://www.igdb.com/");
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
