package com.xtelsolution.xmec.views.widget;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by phimau on 4/9/2017.
 */

public class CustomClusterManager<T extends ClusterItem> extends ClusterManager {
    private CameraIdle cameraIdle;

    public CustomClusterManager(Context context, GoogleMap map) {
        super(context, map);
    }

    public CustomClusterManager(Context context, GoogleMap map, MarkerManager markerManager) {
        super(context, map, markerManager);
    }

    @Override
    public void onCameraIdle() {
        super.onCameraIdle();
        cameraIdle.onCameraIdle();
    }

    public void setCameraIdle(CameraIdle cameraIdle) {
        this.cameraIdle = cameraIdle;
    }

    public interface CameraIdle {
        void onCameraIdle();
    }
}
