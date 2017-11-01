package com.thelosers.game.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thelosers.game.R;

import java.util.List;

import static com.thelosers.game.HelperClasses.help.getWebsiteIcon;
import static com.thelosers.game.HelperClasses.help.getWebsiteName;
import static com.thelosers.game.HelperClasses.help.openWebPage;

/**
 * Created by deepak on 24/09/17.
 */

public class websiteCustomAdapter extends BaseAdapter {
    private Context context;
    private List<websiteCustomClass> rowItems;


    public websiteCustomAdapter(Context context, List<websiteCustomClass> items) {
        this.context = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        TextView name;
        ImageView imageView;
        LinearLayout linearLayout;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final websiteCustomAdapter.ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.website_adapter, null);
            holder = new websiteCustomAdapter.ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.websiteadpaterNAme);
            holder.imageView = (ImageView) convertView.findViewById(R.id.websiteadpaterImage);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.websiteLinearLayout);


            convertView.setTag(holder);
        } else {
            holder = (websiteCustomAdapter.ViewHolder) convertView.getTag();
        }

        final websiteCustomClass detail = rowItems.get(position);
        String name = getWebsiteName(detail.getCategory());
        if (name != null) {

            holder.imageView.setImageResource(getWebsiteIcon(detail.getCategory()));
            holder.name.setText(name);
            convertView.setTag(holder);


        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(detail.getUrl()!=null){
                    openWebPage(context,detail.getUrl());
                }
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




