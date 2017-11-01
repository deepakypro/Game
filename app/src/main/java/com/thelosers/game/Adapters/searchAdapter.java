package com.thelosers.game.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thelosers.game.DisplayActivity.DisplayUi;
import com.thelosers.game.HelperClasses.UtilData;
import com.thelosers.game.R;

import java.util.List;

/**
 * Created by deepak on 23/09/17.
 */

public class searchAdapter extends BaseAdapter {
    private Context context;
    private List<customClass> rowItems;

    public searchAdapter(Context context, List<customClass> items) {
        this.context = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        TextView name;

        // ImageView image1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_listview, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.searchListviewName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final customClass detail = rowItems.get(position);
        convertView.setTag(holder);
        holder.name.setText(detail.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DisplayUi.class);
                UtilData utilData = new UtilData(detail.getId(), detail.getName(), detail.getSummary(), detail.getStoryline(), detail.getTotal_rating(), detail.getTotal_rating_count(), detail.getCover(), detail.getDevelopers(), detail.getPublishers(), detail.getPlayer_perspectives(), detail.getGame_modes(), detail.getGenres(), detail.getThemes(), detail.getReleasedatecustom(), detail.getScreenshot(),detail.getWebsitecustomclasses(),detail.getFirstReleaseDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", utilData);
                context.startActivity(intent);

            }
        });

        return convertView;
    }


    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}

