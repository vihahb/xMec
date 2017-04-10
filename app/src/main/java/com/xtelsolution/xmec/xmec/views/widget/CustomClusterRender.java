package com.xtelsolution.xmec.xmec.views.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.model.entity.HospitalClusterItem;

/**
 * Created by phimau on 4/9/2017.
 */

public class CustomClusterRender<T extends ClusterItem> extends DefaultClusterRenderer<HospitalClusterItem> {
    private Context context;

    public CustomClusterRender(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }

//    @Override
//    protected void onBeforeClusterItemRendered(ClusterItem item, MarkerOptions markerOptions) {
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
//    }

    @Override
    protected void onBeforeClusterItemRendered(HospitalClusterItem item, MarkerOptions markerOptions) {
        if (item.getType() == 0)
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
        else {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_pharmacy)));
        }

    }

    private Bitmap scaleBimap(int id) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), id);
        int size = 0;
        int screenSize = getScreenSize();
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            size = 64;
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            size = 32;
        } else {
            size = 48;
        }
        Bitmap bhalfsize = Bitmap.createScaledBitmap(b, size, size, false);
        return bhalfsize;
    }

    public int getScreenSize() {
        int screenSize = context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize;
    }
}
