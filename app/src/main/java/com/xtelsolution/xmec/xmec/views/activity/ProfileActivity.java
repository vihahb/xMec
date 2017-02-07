package com.xtelsolution.xmec.xmec.views.activity;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.widget.PickerBuilder;

import agency.tango.android.avatarview.views.AvatarView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnSelectImage;
    private AvatarView avatarView;
    private Context mContext;
    private LinearLayout boxInput;
    private FrameLayout layout_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = this;
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        avatarView = (AvatarView) findViewById(R.id.avatar_profile);
        btnSelectImage = (ImageView) findViewById(R.id.btnSelectImage);
        boxInput = (LinearLayout) findViewById(R.id.boxInput);
        layout_avatar = (FrameLayout) findViewById(R.id.layout_avatar);
        btnSelectImage.setOnClickListener(this);
        animation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectImage:
                new PickerBuilder((Activity) mContext, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                Toast.makeText(mContext, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                avatarView.setImageURI(imageUri);
                            }
                        })
                        .start();
                break;
        }
    }

    public void animation() {
        boxInput.setVisibility(View.GONE);

        Animation animTranslate = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.translate_profile);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                boxInput.setVisibility(View.VISIBLE);
                Animation animFade = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fade);
                boxInput.startAnimation(animFade);
            }
        });
        layout_avatar.startAnimation(animTranslate);


    }

}
