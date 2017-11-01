package com.thelosers.game.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thelosers.game.R;

import java.util.List;

/**
 * Created by deepak on 24/09/17.
 */

public class displayScreenAdapter extends RecyclerView.Adapter<displayScreenAdapter.MyViewHolder> {

    private List<String> horizontalList;

    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.displayImageviewList);


        }
    }


    public displayScreenAdapter(Context context, List<String> horizontalList) {
        this.context = context;
        this.horizontalList = horizontalList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_screenshot_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //  final String detail = horizontalList.get(position);


        Picasso.with(context)
                .load(horizontalList.get(position))

                .into(holder.imageView);


//
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d("data",detail.getDevelopers()+"");
//                Intent intent = new Intent(context, DisplayUi.class);
//                UtilData utilData = new UtilData(detail.getId(), detail.getName(), detail.getSummary(), detail.getStoryline(), detail.getTotal_rating(), detail.getTotal_rating_count(), detail.getCover(), detail.getDevelopers(), detail.getPublishers(), detail.getPlayer_perspectives(), detail.getGame_modes(), detail.getGenres(), detail.getThemes(), detail.getReleasedatecustom(), detail.getScreenshot(),detail.getWebsitecustomclasses());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("data", utilData);
//                context.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}


