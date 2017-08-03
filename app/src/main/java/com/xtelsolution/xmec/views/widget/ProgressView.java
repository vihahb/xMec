package com.xtelsolution.xmec.views.widget;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.callbacks.AllRefeshProgressViewListener;

/**
 * Created by Vivhp on 28/07/2017.
 */

public class ProgressView {
    private RecyclerView recyclerView;
    private LinearLayout layout_data;
    private ImageView imageView;
    private TextView textView_data;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ProgressView(Activity activity) {
        layout_data = (LinearLayout) activity.findViewById(R.id.layout_progress_view_data);
        imageView = (ImageView) activity.findViewById(R.id.img_progress_view_data);
        textView_data = (TextView) activity.findViewById(R.id.txt_progress_view_data);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipe_progress_view);
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_progress_view);

        setUpSwipeLayout();
    }

    public ProgressView(View view) {
        layout_data = (LinearLayout) view.findViewById(R.id.layout_progress_view_data);
        imageView = (ImageView) view.findViewById(R.id.img_progress_view_data);
        textView_data = (TextView) view.findViewById(R.id.txt_progress_view_data);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_progress_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_progress_view);

        setUpSwipeLayout();
    }

    private void setUpSwipeLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
    }

    public void setUpRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void updateMessage(int imageView, String textView) {
        if (imageView == -1)
            this.imageView.setVisibility(View.GONE);
        else {
            this.imageView.setVisibility(View.VISIBLE);
            this.imageView.setImageResource(imageView);
        }

        if (textView == null)
            this.textView_data.setVisibility(View.GONE);
        else {
            this.textView_data.setVisibility(View.VISIBLE);
            this.textView_data.setText(textView);
        }
        showMessage();
    }

    public void showData() {
        if (recyclerView.getVisibility() == View.GONE)
            recyclerView.setVisibility(View.VISIBLE);
        if (layout_data.getVisibility() == View.VISIBLE)
            layout_data.setVisibility(View.GONE);
    }

    public void showMessage() {
        if (recyclerView.getVisibility() == View.VISIBLE)
            recyclerView.setVisibility(View.GONE);
        if (layout_data.getVisibility() == View.GONE)
            layout_data.setVisibility(View.VISIBLE);
    }

    public void setEnableRefresh(boolean isEnable) {
        swipeRefreshLayout.setEnabled(false);
        layout_data.setEnabled(isEnable);
    }


    public void setOnRefeshListener(final AllRefeshProgressViewListener refeshListener) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refeshListener.onRefesh();
            }
        });
        layout_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refeshListener.onRefesh();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refeshListener.onRefesh();
            }
        });

    }

    public void onRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setRefreshing(boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

    public void onLayoutClicked(View.OnClickListener onClickListener) {
        layout_data.setOnClickListener(onClickListener);
    }

    public void onSwipeLayoutPost(Runnable runnable) {
        swipeRefreshLayout.post(runnable);
    }

    public void disableSwipe() {
        swipeRefreshLayout.setEnabled(false);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}