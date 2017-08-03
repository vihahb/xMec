package com.xtelsolution.xmec.views.fragment;

import android.view.View;

/**
 * Created by vivhp on 7/28/2017.
 */

public abstract class Fragments extends BasicFragment {

    protected View view;
    protected boolean isWatched = false;


    protected View onCreateView(View view) {

        if (this.view == null) {
            this.view = view;
        }
        return this.view;
    }

    protected void onViewCreated() {
        if (!isWatched) {
            onCreateViewFirst(view);
            isWatched = true;
        } else
            onCreateViewAgain(view);
    }

    protected abstract void onCreateViewAgain(View view);

    protected abstract void onCreateViewFirst(View view);
}
