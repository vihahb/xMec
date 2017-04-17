package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.ChoosePictureListener;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.ProfilePresenter;
import com.xtelsolution.xmec.listener.IProfileView;
import com.xtelsolution.xmec.xmec.views.smallviews.BottomSheetChoosePicture;
import com.xtelsolution.xmec.xmec.views.smallviews.DatePickerFragment;
import com.xtelsolution.xmec.xmec.views.widget.PickerBuilder;

import java.io.IOException;

import agency.tango.android.avatarview.views.AvatarView;
import de.psdev.formvalidations.Field;
import de.psdev.formvalidations.Form;
import de.psdev.formvalidations.validations.NotEmpty;

public class ProfileActivity extends BasicActivity implements View.OnClickListener, IProfileView {
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
    private ProfilePresenter presenter;
    private String urlAvatar;
    private Form mForm;
    private MaterialSpinner spSex;
    private int gender;
    private BottomSheetChoosePicture bottomSheetChoosePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUi(findViewById(R.id.activity_profile));
        mContext = this;
        initUI();
        presenter = new ProfilePresenter(this);
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
        spSex = (MaterialSpinner) findViewById(R.id.spcategorize);
        spSex.setItems("Giới tính", "Nam", "Nữ");

        bottomSheetChoosePicture = new BottomSheetChoosePicture();
//        animation();
        initControl();
        initValidate();
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
        spSex.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0)
                    gender = 3;
                else if (position == 1)
                    gender = 2;
                else if (position == 2)
                    gender = 1;
            }
        });
//        layout_avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!NetWorkInfo.isOnline(mContext))
//                    return;
//                uploadAvatar();
//            }
//        });
//        layout_avatar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                uploadAvatar();
//                return false;
//            }
//        });
        bottomSheetChoosePicture.setChoosePictureListener(new ChoosePictureListener() {
            @Override
            public void onTakeNewPicture() {
                new PickerBuilder(ProfileActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                showToast("Got image - " + imageUri);
                                try {
                                    Bitmap avatar = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                    avatarView.setImageBitmap(avatar);
                                    bottomSheetChoosePicture.dismiss();
                                    presenter.postImage(avatar, false, getBaseContext());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
            }

            @Override
            public void onChoosePictureFromGallery() {
                new PickerBuilder(ProfileActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
//                                showToast("Got image - " + imageUri);
                                try {
                                    Bitmap avatar = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                    avatarView.setImageBitmap(avatar);
                                    presenter.postImage(avatar, false, getBaseContext());
                                    bottomSheetChoosePicture.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
            }
        });
    }

    private void initValidate() {
        mForm = Form.create();
        mForm.addField(Field.using(etName).validate(NotEmpty.build()));
        mForm.addField(Field.using(etHeight).validate(NotEmpty.build()));
        mForm.addField(Field.using(etWeight).validate(NotEmpty.build()));

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
                if (!mForm.isValid()) {
                    Toast.makeText(mContext, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
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


    @Override
    public void onLoadProfileSuccess(String name, long birthday, double height, double weight, String url, int sex) {
        xLog.e("onLoadProfileSuccess ", "  name " + name + "  birthday " + birthday + " height " + height + "   Weight " + weight);
        if (!name.equals(""))
            etName.setText(name);
        if (birthday != 0)
            datePicker.setTimeinMilisecond(birthday);
        else {
            etBirthday.setText(null);
        }
        if (height != 0)
            etHeight.setText(String.valueOf(height));
        if (weight != 0)
            etWeight.setText(String.valueOf(weight));
        setImage(avatarView, url);
//        gender=sex;
        urlAvatar = url;
        if (sex == 1)
            spSex.setSelectedIndex(2);
        else if (sex == 2)
            spSex.setSelectedIndex(1);
        else
            spSex.setSelectedIndex(0);
    }


    @Override
    public void onUpdateProfileSuccess() {
        showToast("Cập nhật thành công");
        final Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        }, 300);

    }

    private void updateProfile() {
        String name = etName.getText().toString();
        double height = Double.valueOf(etHeight.getText().toString());
        double weight = Double.valueOf(etWeight.getText().toString());
        long birthday = datePicker.getTimeinMilisecond();
        if (birthday == 0) {
            birthday = SharedPreferencesUtils.getInstance().getLongValue(Constant.USER_BIRTHDAY);
        }
        birthday = birthday / 1000;
        presenter.checkUpdateProfile(name, (birthday), height, weight, urlAvatar, gender);
    }

    private void uploadAvatar() {
        bottomSheetChoosePicture.show(getSupportFragmentManager(), "AVATAR");
    }

    @Override
    public void onUploadImageSussces(String url) {
        urlAvatar = url;
    }
}
