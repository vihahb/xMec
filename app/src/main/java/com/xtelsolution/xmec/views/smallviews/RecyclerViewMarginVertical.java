package com.xtelsolution.xmec.views.smallviews;

import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class RecyclerViewMarginVertical extends RecyclerView.ItemDecoration {
    private int margin;


    public RecyclerViewMarginVertical(@IntRange(from = 0) int margin) {
        this.margin = margin;

    }

    /**
     * Set different margins for the items inside the recyclerView: no top margin for the first row
     * and no left margin for the first column.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (position == 0)
            outRect.top = 0;
        else
            outRect.top = margin;

    }

}