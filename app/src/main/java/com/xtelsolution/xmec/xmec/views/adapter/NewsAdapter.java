package com.xtelsolution.xmec.xmec.views.adapter;

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
import com.xtelsolution.xmec.xmec.views.activity.NewsDetailActivity;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<NewsFeed> data;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).tvNewsTitle.setText(data.get(position).getTitle());
            ((NewsViewHolder) holder).tvNewsPubTime.setText(data.get(position).getPubDate());
            Picasso.with(mContext).load(data.get(position).getDescription()).into((((NewsViewHolder) holder).imgNewsPhoto));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
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
