package com.xtelsolution.xmec.listener.list;

import android.widget.Button;

/**
 * Created by Admin on 2/6/2017.
 */

public interface ItemClickListener {
    void onItemClickListener(Object item, int position);

    interface ItemIconClickListener {
        void onItemIconClickListener(Object item, int positon);
    }

    interface ButtonAdapterClickListener {
        void onButtonAdapterClickListener();
    }
}
