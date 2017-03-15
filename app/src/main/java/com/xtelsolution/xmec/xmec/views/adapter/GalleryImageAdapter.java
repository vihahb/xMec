package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 3/3/2017.
 */

public class GalleryImageAdapter extends BaseAdapter {
    private List<Drawable> drawableList;
    private Context mContext;
    public GalleryImageAdapter(Context context){
        mContext =context;
        drawableList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return drawableList.size();
    }

    @Override
    public Object getItem(int i) {
        return drawableList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView= new ImageView(mContext);
        imageView.setImageDrawable(drawableList.get(i));
        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
    public void addAll(List<Drawable> drawableList){
        this.drawableList.addAll(drawableList);
        notifyDataSetChanged();
    }
    public void add(Drawable drawable){
        drawableList.add(drawable);
        notifyDataSetChanged();
    }
}
