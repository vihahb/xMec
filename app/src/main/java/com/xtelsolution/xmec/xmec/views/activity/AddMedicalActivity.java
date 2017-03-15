package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_List_IIlness;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.presenter.AddMedicalPresenter;
import com.xtelsolution.xmec.presenter.DetailMedicalPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.ImageViewAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IAddMedicalView;
import com.xtelsolution.xmec.xmec.views.inf.IDetailMedicalView;
import com.xtelsolution.xmec.xmec.views.smallviews.DatePickerFragment;
import com.xtelsolution.xmec.xmec.views.smallviews.DialogImageViewer;
import com.xtelsolution.xmec.xmec.views.widget.PickerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.psdev.formvalidations.Field;
import de.psdev.formvalidations.Form;
import de.psdev.formvalidations.validations.NotEmpty;

public class AddMedicalActivity extends BasicActivity implements IAddMedicalView, ItemClickListener, IDetailMedicalView {
    private Toolbar mToolbar;
    private HealtRecoderAdapter healtRecoderAdapter;
    private RecyclerView rvHealthReconder;
    private Context mContext;
    private EditText etName;
    private EditText etBeginTime;
    private EditText etEndTime;
    private EditText etNote;
    private Button btnSavaDirectory;
    private DatePickerFragment pickerBeginTime;
    private DatePickerFragment pickerEndTime;
    private List<Resource> resourceList;
    private AddMedicalPresenter presenter;
    private ImageView btnAddHelthReconder;
    private ImageViewAdapter imageViewAdapter;
    private int idMedical;
    private Form mForm;
    private DetailMedicalPresenter detailMedicalPresenter;
    private DialogImageViewer dialogImageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_directory);
        mContext = AddMedicalActivity.this;
        init();
        idMedical = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);
        etEndTime.setInputType(InputType.TYPE_NULL);
        etBeginTime.setInputType(InputType.TYPE_NULL);
        initControl();
        initValidate();
        rvHealthReconder.setAdapter(healtRecoderAdapter);
        rvHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        imageViewAdapter = new ImageViewAdapter(resourceList, mContext);
        dialogImageViewer.setAdapter(imageViewAdapter);
        if (idMedical != -1) {
            detailMedicalPresenter.getDetailMedical(idMedical);
            btnSavaDirectory.setText(getResources().getText(R.string.update_medical));
        } else {
            dialogImageViewer.btnRemove.setVisibility(View.GONE);
        }
    }

    private void initControl() {
        etEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDatePicker(pickerEndTime);
            }
        });
        etBeginTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDatePicker(pickerBeginTime);
            }
        });
        btnSavaDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetWorkInfo.isOnline(getBaseContext())) {
                    Toast.makeText(mContext, "Không kết nối Internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idMedical == -1) {
                    if (!mForm.isValid()) {
                        showToast("Không được để trống");
                        return;
                    }
                    addMedicalDirectory();
                } else
                    updateMedicalDirectory();
            }
        });
        btnAddHelthReconder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetWorkInfo.isOnline(getBaseContext())) {
                    Toast.makeText(mContext, "Không kết nối Internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddHeathRecoder();
            }
        });
        healtRecoderAdapter.setOnItemClickListener(this);
        dialogImageViewer.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Xóa ảnh "+dialogImageViewer.getCurrentPosiion());

            }
        });
    }


    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        rvHealthReconder = (RecyclerView) findViewById(R.id.rv_health_records);
        etName = (EditText) findViewById(R.id.et_directory_name);
        etBeginTime = (EditText) findViewById(R.id.et_start_time);
        etEndTime = (EditText) findViewById(R.id.et_end_time);
        btnSavaDirectory = (Button) findViewById(R.id.btn_save_director);
        etNote = (EditText) findViewById(R.id.et_note);
        btnAddHelthReconder = (ImageView) findViewById(R.id.btn_add_healty_recoder);

        resourceList = new ArrayList<>();
        healtRecoderAdapter = new HealtRecoderAdapter(mContext, resourceList);

        pickerBeginTime = new DatePickerFragment(etBeginTime);
        pickerEndTime = new DatePickerFragment(etEndTime);
        presenter = new AddMedicalPresenter(this);
        detailMedicalPresenter = new DetailMedicalPresenter(this);
        dialogImageViewer = new DialogImageViewer(mContext);
        mForm = Form.create();
    }

    private void AddHeathRecoder() {
        new PickerBuilder(AddMedicalActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                    @Override
                    public void onImageReceived(Uri imageUri) {
                        Toast.makeText(mContext, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                        try {
                            Bitmap avatar = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            presenter.postImage(avatar, true, getBaseContext());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }

    private void updateMedicalDirectory() {
        String name = etName.getText().toString();
        String note = etNote.getText().toString();
        long beginTime = pickerBeginTime.getTimeinMilisecond();
        long endTime = pickerEndTime.getTimeinMilisecond();
        detailMedicalPresenter.updateMedicalDirectory(idMedical, name, beginTime, endTime, 1, note, resourceList);

    }


    private void addMedicalDirectory() {
        String name = etName.getText().toString();
        String note = etNote.getText().toString();
        long beginTime = pickerBeginTime.getTimeinMilisecond();
        long endTime = pickerEndTime.getTimeinMilisecond();
        presenter.addMedicalDirectorry(name, beginTime, endTime, 1, note, resourceList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showDatePicker(DatePickerFragment datePicker) {
        datePicker.show(getSupportFragmentManager(), "datepicker");
    }

    @Override
    public void onUploadImageSussces(String url) {
        xLog.e("SIZE ADAPTER " + healtRecoderAdapter.getItemCount());
        healtRecoderAdapter.add(new Resource(url));
        imageViewAdapter.notifyDataSetChanged();
        Log.d("URL", "onUploadImageSussces: " + url);
    }


    @Override
    public void onAddMedicalSuccess() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public Activity getActivity() {
        return AddMedicalActivity.this;
    }

    @Override
    public void onItemClickListener(Object item, int position) {
        Toast.makeText(mContext, imageViewAdapter.getCount() + "  ", Toast.LENGTH_SHORT).show();
        dialogImageViewer.show();

    }

    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {
        etName.setText(obj.getName());
        etNote.setText(obj.getNote());
        etBeginTime.setText(Constant.getDate(obj.getBegin_time()));
        pickerBeginTime.setTimeinMilisecond(obj.getBegin_time());
        etEndTime.setText(Constant.getDate(obj.getEnd_time()));
        pickerEndTime.setTimeinMilisecond(obj.getEnd_time());
        healtRecoderAdapter.addAll(obj.getResources());
        imageViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadListIllnessFinish(RESP_List_IIlness data) {

    }

    @Override
    public void onUpdateMedicalFinish() {
        showToast("Cập nhật thành công");
    }

    private void initValidate() {
        mForm = Form.create();
        mForm.addField(Field.using(etName).validate(NotEmpty.build()));
        mForm.addField(Field.using(etBeginTime).validate(NotEmpty.build()));
        mForm.addField(Field.using(etNote).validate(NotEmpty.build()));
        mForm.addField(Field.using(etEndTime).validate(NotEmpty.build()));
    }

}
