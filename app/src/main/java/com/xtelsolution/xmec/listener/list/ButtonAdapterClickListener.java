package com.xtelsolution.xmec.listener.list;

import android.view.View;
import android.widget.Button;

/**
 * Created by phimau on 3/19/2017.
 */

public interface ButtonAdapterClickListener {
    void onButtonAdapterClickListener(Button button);
    void onRemoveButtonClick(Object obj,int position);
}
