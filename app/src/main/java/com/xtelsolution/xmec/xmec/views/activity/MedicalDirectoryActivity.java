package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xtelsolution.xmec.entity.IIlness;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.presenter.AddMedicalPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IIlnessAdapter2;
import com.xtelsolution.xmec.xmec.views.inf.IAddMedicalView;
import com.xtelsolution.xmec.xmec.views.smallviews.DatePickerFragment;
import com.xtelsolution.xmec.xmec.views.widget.PickerBuilder;

import java.util.ArrayList;
import java.util.List;

public class MedicalDirectoryActivity extends BasicActivity implements IAddMedicalView {
    private Toolbar mToolbar;
    private IIlnessAdapter2 mAdapter;
    private HealtRecoderAdapter healtRecoderAdapter;
    private RecyclerView rcDesease;
    private RecyclerView rvHealthReconder;
    private Context mContext;
    private EditText etName;
    private EditText etBeginTime;
    private EditText etEndTime;
    private EditText etNote;
    private Button btnSavaDirectory;
    private DatePickerFragment pickerBeginTime;
    private DatePickerFragment pickerEndTime;
    private List<String> listUrl;
    private AddMedicalPresenter presenter;
    private ImageView btnAddHelthReconder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_directory);
        mContext = MedicalDirectoryActivity.this;
        init();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        etEndTime.setInputType(InputType.TYPE_NULL);
        etBeginTime.setInputType(InputType.TYPE_NULL);

        rcDesease.setAdapter(mAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        initControl();
        rvHealthReconder.setAdapter(healtRecoderAdapter);
        rvHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initControl() {
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                Intent i = new Intent(mContext, DetailDiseaseActivity.class);
                mContext.startActivity(i);
            }
        });
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
                String name = etName.getText().toString();
                String note = etNote.getText().toString();
                long beginTime  = pickerBeginTime.getTimeinMilisecond();
                long endTime = pickerEndTime.getTimeinMilisecond();
                presenter.addMedicalDirectorry(name,beginTime,endTime,1,note,listUrl);

            }
        });
        btnAddHelthReconder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "maui dsa asd ", Toast.LENGTH_SHORT).show();

                new PickerBuilder(MedicalDirectoryActivity.this,PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                Toast.makeText(mContext, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                            }
                        }).start();
            }
        });
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        rcDesease = (RecyclerView) findViewById(R.id.rv_Desease);
        rvHealthReconder = (RecyclerView) findViewById(R.id.rv_health_records);
        etName = (EditText) findViewById(R.id.et_directory_name);
        etBeginTime = (EditText) findViewById(R.id.et_start_time);
        etEndTime = (EditText) findViewById(R.id.et_end_time);
        btnSavaDirectory = (Button) findViewById(R.id.btn_save_director);
        etNote = (EditText) findViewById(R.id.et_note);
        btnAddHelthReconder = (ImageView) findViewById(R.id.btn_add_healty_recoder);
        btnAddHelthReconder.setClickable(true);
        ArrayList<IIlness> iIlnesses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            iIlnesses.add(new IIlness("Tên bệnh "+i));
        }
        mAdapter = new IIlnessAdapter2(getBaseContext(), iIlnesses);
        healtRecoderAdapter = new HealtRecoderAdapter(mContext);
        rcDesease.setNestedScrollingEnabled(false);
        listUrl = new ArrayList<>();
        data();
        pickerBeginTime = new DatePickerFragment(etBeginTime);
        pickerEndTime = new DatePickerFragment(etEndTime);
        presenter= new AddMedicalPresenter(this);
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
    private void data(){
        listUrl.add("phimh");
        listUrl.add("hungnt");
        listUrl.add("hungtt");

    }

    @Override
    public void onUploadImageSussces() {

    }

    @Override
    public void showProcessBar() {

    }

    @Override
    public void hideProcessBar() {

    }

    @Override
    public void onAddMedicalSuccess() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
