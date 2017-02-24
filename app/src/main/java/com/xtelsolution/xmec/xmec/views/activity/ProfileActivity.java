package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.UpdateInfoPresenter;
import com.xtelsolution.xmec.xmec.views.inf.IProfileView;
import com.xtelsolution.xmec.xmec.views.smallviews.DatePickerFragment;
import com.xtelsolution.xmec.xmec.views.widget.PickerBuilder;

import java.io.IOException;

import agency.tango.android.avatarview.views.AvatarView;

public class ProfileActivity extends BasicActivity implements View.OnClickListener,IProfileView,UploadFileListener{
    private ImageView btnSelectImage;
    private AvatarView avatarView;
    private Context mContext;
    private LinearLayout boxInput;
    private FrameLayout layout_avatar;
    private Toolbar mToolbar;
    private EditText etName;
    private EditText etBirthday;
    private EditText etHeight;
    private EditText etWeight;
    private Button btnUpdateInfo;
    private DatePickerFragment datePicker;
    private UpdateInfoPresenter presenter;
    private String urlAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = this;
        initUI();
        presenter= new UpdateInfoPresenter(this);
        presenter.getProfile();

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
        btnSelectImage.setClickable(true);
        boxInput = (LinearLayout) findViewById(R.id.boxInput);
        layout_avatar = (FrameLayout) findViewById(R.id.layout_avatar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        etBirthday = (EditText) findViewById(R.id.et_birth_day);
        datePicker = new DatePickerFragment(etBirthday);
        etName = (EditText) findViewById(R.id.et_name);
        etHeight = (EditText) findViewById(R.id.et_height);
        etWeight = (EditText) findViewById(R.id.et_weight);
        etBirthday.setInputType(InputType.TYPE_NULL);
        btnUpdateInfo = (Button) findViewById(R.id.btn_update_info);
        btnSelectImage.setOnClickListener(this);
        animation();
        initControl();
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

            case R.id.btn_update_info:
                if (!NetWorkInfo.isOnline(mContext)){
                    showToastNoInternet();
                }
                updateProfile();

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

    private void showDatePicker() {
        datePicker.show(getSupportFragmentManager(), "datepicker");
    }

    private void initControl() {
        btnUpdateInfo.setOnClickListener(this);
        etBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDatePicker();
            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetWorkInfo.isOnline(mContext))
                    return;
                uploadAvatar();
            }
        });
    }

    @Override
    public void onLoadProfileSuccess(String name, long birthday, double height, double weight, String url) {
        etName.setText(name);
        etBirthday.setText(Constant.getDate(birthday));
        etHeight.setText(String.valueOf(height));
        etWeight.setText(String.valueOf(weight));
        setImage(avatarView,url);
        urlAvatar=url;
    }


    @Override
    public void onUpdateProfileSuccess() {
        showToast("Cập nhật thành công");
    }


    private void showToastNoInternet(){
        showToast("Không kết nối internet");
    }
    private void updateProfile(){
        String name = etName.getText().toString();
        double height = Double.valueOf(etHeight.getText().toString());
        double weight = Double.valueOf(etWeight.getText().toString());
        long birthday = datePicker.getTimeinMilisecond();
        if (birthday==0){
            birthday = SharedPreferencesUtils.getInstance().getLongValue(Constant.USER_BIRTHDAY);
        }
        birthday=birthday/1000;
        presenter.updateProfile(name,(birthday),height,weight,urlAvatar);
    }
    private void uploadAvatar(){
        new PickerBuilder(ProfileActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                    @Override
                    public void onImageReceived(Uri imageUri) {
                        showToast("Got image - " + imageUri);
                        try {
                            Bitmap avatar = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            new Task.ConvertImage(mContext, false, ProfileActivity.this).execute(avatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }

    @Override
    public void onSuccess(String url) {
        urlAvatar =url;
        Log.e("URLAVART", "onSuccess: "+url );
    }

    @Override
    public void onError(String e) {

    }
}
