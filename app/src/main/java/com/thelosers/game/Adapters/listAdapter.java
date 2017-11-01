package com.thelosers.game.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thelosers.game.DisplayActivity.DisplayUi;
import com.thelosers.game.HelperClasses.ReferralUrl;
import com.thelosers.game.HelperClasses.UtilData;
import com.thelosers.game.R;

import java.util.List;

import static com.thelosers.game.HelperClasses.help.getDaysLeft;

/**
 * Created by deepak on 24/09/17.
 */

public class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {

    private List<customClass> horizontalList;

    private Context context;

    String activityName;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView mNameTextView, mDaysTextView;


        public MyViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.listuiImageview);
            mNameTextView = (TextView) view.findViewById(R.id.listuiNameview);
            mDaysTextView = (TextView) view.findViewById(R.id.listuiDays);


        }
    }


    public listAdapter(Context context, List<customClass> horizontalList, String activityName) {
        this.context = context;
        this.horizontalList = horizontalList;
        this.activityName = activityName;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ui_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final customClass detail = horizontalList.get(position);


        Picasso.with(context)
                .load(detail.getCover())
                .fit()

                .into(holder.imageView);

        holder.mNameTextView.setText(detail.getName());

        if (activityName.equals(ReferralUrl.UPCOMING) || activityName.equals(ReferralUrl.XBOX) || activityName.equals(ReferralUrl.PLAYSTATION) || activityName.equals(ReferralUrl.WINDOW) || activityName.equals(ReferralUrl.NINTENDO)) {

            long day=getDaysLeft(detail.getFirstReleaseDate());
            if(day==0){
                holder.mDaysTextView.setText("Today");
            }else {
                holder.mDaysTextView.setText("in " + day + " Days");
            }


        }else {
            holder.mDaysTextView.setVisibility(View.GONE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DisplayUi.class);
                UtilData utilData = new UtilData(detail.getId(), detail.getName(), detail.getSummary(), detail.getStoryline(), detail.getTotal_rating(), detail.getTotal_rating_count(), detail.getCover(), detail.getDevelopers(), detail.getPublishers(), detail.getPlayer_perspectives(), detail.getGame_modes(), detail.getGenres(), detail.getThemes(), detail.getReleasedatecustom(), detail.getScreenshot(), detail.getWebsitecustomclasses(), detail.getFirstReleaseDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", utilData);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}


