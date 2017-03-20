package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ButtonAdapterClickListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.REQ_Medicine;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.presenter.AddDiseasePresenter;
import com.xtelsolution.xmec.xmec.views.adapter.DiseaseAutoCompleteAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapterWithEditButton;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAutoCompleteAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IAddIllnessView;
import com.xtelsolution.xmec.xmec.views.smallviews.DelayAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HUNGNT on 2/14/2017.
 */

public class AddIllnessActivity extends BasicActivity implements IAddIllnessView, ButtonAdapterClickListener {
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private DelayAutoCompleteTextView etFindDisease;
    private DelayAutoCompleteTextView etFindMedicine;
    private ProgressBar progressBarDisease;
    private Context mContext;
    private Button btnAddDisease;
    private int idDisease = -1;
    private int uidDisease = -1;
    private int idMecine=-1;
    private Disease disease;
    private RESP_Medicine medicine;
    private AddDiseasePresenter presenter;
    private DiseaseAutoCompleteAdapter diseaseAdapter;
    private MedicineAutoCompleteAdapter medicineAdapter;
    private EditText etNote;
    private Dialog mDialog;
    private int idMedical;
    private Button btnAddMedicine;
    private ProgressBar progressBarMedicine;
    private MedicineAdapterWithEditButton medicineAdapterWithEditButton;
    private List<REQ_Medicine> listMedicine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);
        mContext = getBaseContext();
        initUI();
        initControl();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initRecycleView();

        idMedical = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);
        presenter = new AddDiseasePresenter(this);

        diseaseAdapter = new DiseaseAutoCompleteAdapter();
        etFindDisease.setThreshold(3);
        etFindDisease.setLoadingIndicator(progressBarDisease);
        etFindDisease.setAdapter(diseaseAdapter);

    }

    private void initControl() {
        btnAddDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDisease();
            }
        });
        etFindDisease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etFindDisease.setThreshold(1000);
                disease = (Disease) adapterView.getItemAtPosition(i);
                idDisease = disease.getId();
                etFindDisease.setText(disease.getName());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etFindDisease.setThreshold(3);
                    }
                }, 100);

            }
        });
        etFindMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etFindMedicine.setThreshold(1000);
                medicine = (RESP_Medicine) adapterView.getItemAtPosition(i);
                idMecine = medicine.getId();
                etFindMedicine.setText(medicine.getName());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etFindMedicine.setThreshold(3);
                    }
                }, 100);

            }
        });
        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                presenter.addMedicine(idMedical,uidDisease,idDisease,etFindDisease.getText().toString(),etFindMedicine.getText().toString(),idMecine,etNote.getText().toString());
//                listMedicine.add(new REQ_Medicine(etFindMedicine.getText().toString(),idMecine));
                medicineAdapterWithEditButton.add(new REQ_Medicine(etFindMedicine.getText().toString(),idMecine));
                idMecine=-1;
                mDialog.dismiss();

            }
        });

    }

    private void addDisease() {
        presenter.addDeisease(idMedical, etFindDisease.getText().toString(), idDisease, etNote.getText().toString(),listMedicine);
    }


    private void initRecycleView() {
        listMedicine = new ArrayList<>();
        medicineAdapterWithEditButton = new MedicineAdapterWithEditButton(this, listMedicine);
        medicineAdapterWithEditButton.setButtonAdapterClickListener(this);
//        medicineAdapterWithEditButton.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClickListener(Object item, int position) {
//                if (item == null) {
//                    showToast("Item Null: " + position);
//                } else {
//                    showToast("Item: " + position);
//                }
//            }
//        });
        recyclerView.setAdapter(medicineAdapterWithEditButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        etFindDisease = (DelayAutoCompleteTextView) findViewById(R.id.etFindIllness);
        recyclerView = (RecyclerView) findViewById(R.id.rvMedicineWithEditButton);
        progressBarDisease = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        btnAddDisease = (Button) findViewById(R.id.btnSaveIllness);
        etNote = (EditText) findViewById(R.id.et_note);

        mDialog = new Dialog(AddIllnessActivity.this);
        mDialog.setContentView(R.layout.dialog_add_medical);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        btnAddMedicine = (Button) mDialog.findViewById(R.id.btn_add_medicine);
        progressBarMedicine = (ProgressBar)mDialog.findViewById(R.id.pb_loading_indicator1);
        etFindMedicine= (DelayAutoCompleteTextView) mDialog.findViewById(R.id.et_find_medicine);
        medicineAdapter = new MedicineAutoCompleteAdapter();
        etFindMedicine.setThreshold(3);
        etFindMedicine.setLoadingIndicator(progressBarMedicine);
        etFindMedicine.setAdapter(medicineAdapter);
    }

    @Override
    public void onAddDiseaseSuccess(int idDisease) {
        showToast("Thêm bệnh thành công");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(AddIllnessActivity.this,MedicalDetailActivity.class);
                i.putExtra(Constant.MEDICAL_ID,idMedical);
                startActivity(i);
            }
        },500);
    }

    @Override
    public void onAddMedicineSuccess(int id) {
        showToast("Thêm bệnh thành công");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(AddIllnessActivity.this,MedicalDetailActivity.class);
                i.putExtra(Constant.MEDICAL_ID,idMedical);
                startActivity(i);
            }
        },500);
    }

    @Override
    public void onButtonAdapterClickListener(Button button) {
        mDialog.show();
    }

    @Override
    public void onRemoveButtonClick(Object obj, final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddIllnessActivity.this);
        alertDialog.setTitle("Bạn có muốn xóa Thuốc này?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                medicineAdapterWithEditButton.removeItem(position);
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}
