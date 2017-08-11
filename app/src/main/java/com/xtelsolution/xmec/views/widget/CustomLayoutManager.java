package com.xtelsolution.xmec.views.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by vivhp on 8/11/2017.
 */

public class CustomLayoutManager extends LinearLayoutManager {
    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
