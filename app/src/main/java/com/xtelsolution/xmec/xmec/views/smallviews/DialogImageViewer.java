package com.xtelsolution.xmec.xmec.views.smallviews;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Resource;

import java.util.List;

/**
 * Created by phimau on 3/8/2017.
 */

public class DialogImageViewer extends Dialog {
    private Context mContext;
    private ViewPager viewPager;
    public ImageView btnRemove;
    public DialogImageViewer(Context context) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setContentView(R.layout.image_viewer_dialog);
        btnRemove = (ImageView) findViewById(R.id.btn_remove);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }
    public void setAdapter(PagerAdapter adapter){
        viewPager.setAdapter(adapter);
    }
    public int  getCurrentPosiion(){
        return viewPager.getCurrentItem();
    }
}
