package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;

/**
 * Created by phimau on 1/18/2017.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public NewsFeedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_news_feed, null);
        return new NewsFeedAdapter.NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        private NewsFeedViewHolder(View itemView) {
            super(itemView);
        }
    }
}
