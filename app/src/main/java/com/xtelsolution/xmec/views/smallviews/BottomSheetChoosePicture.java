package com.xtelsolution.xmec.views.smallviews;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.ChoosePictureListener;

/**
 * Created by phimau on 3/28/2017.
 */

public class BottomSheetChoosePicture extends BottomSheetDialogFragment {
    private ImageView btnTakePicture;
    private ImageView btnChoosePicture;
    private Dialog dialog;
    private ChoosePictureListener choosePictureListener;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void setChoosePictureListener(ChoosePictureListener choosePictureListener) {
        this.choosePictureListener = choosePictureListener;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_pick_picture, null);
        dialog.setContentView(contentView);
        this.dialog = dialog;
        btnTakePicture = (ImageView) dialog.findViewById(R.id.btn_take_picture);
        btnChoosePicture = (ImageView) dialog.findViewById(R.id.btn_choose_picure);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePictureListener.onTakeNewPicture();
            }
        });
        btnChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePictureListener.onChoosePictureFromGallery();
            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }
//    public ImageView btnTakePicture(){
//        return btnTakePicture;
//    }
//    public ImageView getBtnChoosePicture(){
//        return btnChoosePicture;
//    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }
}
