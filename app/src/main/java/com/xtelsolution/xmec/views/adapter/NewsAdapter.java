package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.views.activity.NewsDetailActivity;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {
    public static final String TAG_NEWS_URL = "news_url";
    private Context mContext;
    private ArrayList<NewsFeed> data;
//    private onItem
    public NewsAdapter(Context mContext,ArrayList<NewsFeed> data) {
        this.mContext = mContext;
        this.data =data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).tvNewsTitle.setText(data.get(position).getTitle());
            ((NewsViewHolder) holder).tvNewsPubTime.setText(data.get(position).getPubDate());
           Picasso.with(mContext).load(data.get(position).getDescription()).into((((NewsViewHolder) holder).imgNewsPhoto));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext,NewsDetailActivity.class);
                    i.putExtra(TAG_NEWS_URL,data.get(position).getLink());
                    mContext.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNewsPhoto;
        TextView tvNewsTitle, tvNewsPubTime;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imgNewsPhoto = (ImageView) itemView.findViewById(R.id.imgNewsPhoto);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.tvNewsFeedTitle);
            tvNewsPubTime = (TextView) itemView.findViewById(R.id.tvNewsPubTime);
        }
    }
}
