package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Article;

import java.util.Date;
import java.util.List;

/**
 * Created by phimau on 1/18/2017.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter {
    private List<Article> data;
    private Context mContext;

    public NewsFeedAdapter(Context mContext, List<Article> articles) {
        this.mContext = mContext;
        this.data = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_feed, parent, false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        private NewsFeedViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Article article) {
        insert(article, data.size());
    }

    public void insert(Article article, int position) {
        data.add(position, article);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Article> articles) {
        int startIndex = data.size();
        data.addAll(startIndex, articles);
        notifyItemRangeInserted(startIndex, articles.size());
    }
}
