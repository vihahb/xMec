package com.xtelsolution.xmec.views.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.xLog;
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
        int screenSize = getDeviceResolution();
        xLog.e("SCREEN0",screenSize+"   s");
//        if (screenSize == 2) {
//            size = 48;
//        } else if (screenSize == 1) {
//            size = 48;
//        } else if (screenSize==3){
//            size = 128;
//        }
        switch (screenSize){
            case 2:
                size=48;
                break;
            case 1:
                size=48;
                break;
            case 3:
                size=128;
                break;
            case -1:
                size=64;
        }
        Bitmap bhalfsize = Bitmap.createScaledBitmap(b, size, size, false);
        return bhalfsize;
    }

    //    public int getScreenSize() {
//        int screenSize = context.getResources().getConfiguration().screenLayout &
//                Configuration.SCREENLAYOUT_SIZE_MASK;
//        xLog.e("SCREEN SIZE",screenSize+"   :");
//        return screenSize;
//    }
    private int getDeviceResolution()
    {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                return 1;
            case DisplayMetrics.DENSITY_HIGH:
                return 2;
            case DisplayMetrics.DENSITY_LOW:
                return 1;
            case DisplayMetrics.DENSITY_XHIGH:
                return 2;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 3;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 4;
            default:
                return -1;
        }
    }
}
