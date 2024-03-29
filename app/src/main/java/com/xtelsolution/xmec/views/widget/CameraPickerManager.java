package com.xtelsolution.xmec.views.widget;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.xtelsolution.xmec.common.xLog;

/**
 * Created by Admin on 1/24/2017.
 */

public class CameraPickerManager extends PickerManager {

    public CameraPickerManager(Activity activity) {
        super(activity);
    }

    protected void sendToExternalApp() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        mProcessingPhotoUri = getImageFile();
        xLog.e("URI ///", mProcessingPhotoUri.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mProcessingPhotoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }
}