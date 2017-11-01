package com.thelosers.game.Search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.thelosers.game.Adapters.customClass;
import com.thelosers.game.Adapters.searchAdapter;
import com.thelosers.game.R;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.thelosers.game.HelperClasses.help.isNetworkAvailable;
import static com.thelosers.game.HelperClasses.jsonClass.getUpcomingJson;

public class SearchUi extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText mSearchEdit;
    ListView listView;

    ArrayList<customClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        setContentView(R.layout.activity_search_ui);
        mSearchEdit = (EditText) findViewById(R.id.searchEditText);
        listView = (ListView) findViewById(R.id.searchListview);

    }

    public void searchButton(View view) {
        String search = mSearchEdit.getText().toString();

        if (isNetworkAvailable(getApplicationContext())) {
            if (!search.isEmpty()) {

                APIWrapper wrapper = new APIWrapper(getApplicationContext(), getResources().getString(R.string.google_maps_key_one));
                Parameters parameters = new Parameters()
                        .addLimit("10")
                        .addSearch(search)
                        .addFields("name,id,cover,summary,release_dates,total_rating,total_rating_count,first_release_date,screenshots,websites,storyline,themes.name,player_perspectives.name,franchise.name,game_modes.name,genres.name,developers.name,publishers.name")
                        .addExpand("themes,player_perspectives,franchise,game_modes,genres,developers,publishers");


                wrapper.search(APIWrapper.Endpoint.GAMES, parameters, new onSuccessCallback() {
                    @Override
                    public void onSuccess(JSONArray jsonArray) {
                        arrayList = getUpcomingJson(jsonArray);

                        if (arrayList.size() > 0) {
                            searchAdapter adapter = new searchAdapter(getApplicationContext(), arrayList);
                            listView.setAdapter(adapter);
                            // ListUtils.setDynamicHeight(listView);
                            listView.setOnItemClickListener(SearchUi.this);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {

                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = listView.getAdapter().getItem(position);
        String s = obj.toString();

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }
}

