package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.entity.Article;
import com.xtelsolution.xmec.xmec.views.activity.NewsDetailActivity;

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
        if (holder instanceof NewsFeedViewHolder) {
            final Article article = data.get(position);
            NewsFeedViewHolder viewHolder = (NewsFeedViewHolder) holder;
            viewHolder.tvAuthorName.setText(article.getAuthor().getName());
            viewHolder.tvNewsFeedTitle.setText(article.getNewsFeed().getTitle());
            viewHolder.tvTypename.setText(article.getType_name());
            viewHolder.tvNewsPubTime.setText(article.getNewsFeed().getPubDate());
            viewHolder.tvViews.setText(String.format(mContext.getResources().getString(R.string.total_view), article.getTotal_view()));
            viewHolder.tvLikes.setText(String.format(mContext.getResources().getString(R.string.total_like), article.getTotal_like()));
            viewHolder.tvComments.setText(String.format(mContext.getResources().getString(R.string.total_comment), article.getTotal_comment()));
            Picasso.with(mContext).load(article.getNewsFeed().getDescription()).into((viewHolder).imgNewsPhoto);
            if (data.get(position).getType() == Article.TYPE_VIDEO)
                (viewHolder).imgPlayIcon.setVisibility(View.VISIBLE);
            else (viewHolder).imgPlayIcon.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.TAG_NEWS_URL, article.getNewsFeed().getLink());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        TextView tvNewsFeedTitle;
        TextView tvAuthorName;
        TextView tvTypename;
        TextView tvNewsPubTime;
        TextView tvViews, tvLikes, tvComments;
        ImageView imgNewsPhoto;
        ImageView imgPlayIcon;
        ImageView imgAuthorAvatar;

        private NewsFeedViewHolder(View itemView) {
            super(itemView);
            tvNewsFeedTitle = (TextView) itemView.findViewById(R.id.tvNewsFeedTitle);
            tvAuthorName = (TextView) itemView.findViewById(R.id.tvAuthorName);
            tvTypename = (TextView) itemView.findViewById(R.id.tvTypeName);
            tvNewsPubTime = (TextView) itemView.findViewById(R.id.tvNewsPubTime);
            tvViews = (TextView) itemView.findViewById(R.id.tvViews);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            tvComments = (TextView) itemView.findViewById(R.id.tvComments);
            imgNewsPhoto = (ImageView) itemView.findViewById(R.id.imgNewsPhoto);
            imgPlayIcon = (ImageView) itemView.findViewById(R.id.imgPlayIcon);
            imgAuthorAvatar = (ImageView) itemView.findViewById(R.id.imgAuthorAvatar);
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
