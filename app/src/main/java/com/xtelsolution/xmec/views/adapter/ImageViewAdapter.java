package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.model.Resource;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by phimau on 3/4/2017.
 */

public class ImageViewAdapter extends PagerAdapter {
    private List<Resource> urlList;
    private Context mContext;

    public ImageViewAdapter(List<Resource> urls, Context mContext) {
        this.mContext = mContext;
        urlList = urls;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());

        Resource resource = urlList.get(position);
        if (resource.getBitmap() != null) {
            photoView.setImageBitmap(resource.getBitmap());
        } else {
            Picasso.with(mContext)
                    .load(urlList.get(position).getServer_path())
                    .into(photoView);
        }
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void add(Resource resource) {
        urlList.add(resource);
        notifyDataSetChanged();
    }
}
