package com.xtelsolution.xmec.xmec.views.fragment;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;

/**
 * Created by phimau on 2/15/2017.
 */

public abstract class BasicFragment extends Fragment {

    protected void setImage(ImageView img,String url) {
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(img);
    }
}
