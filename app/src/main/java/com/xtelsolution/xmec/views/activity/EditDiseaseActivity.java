package com.xtelsolution.xmec.views.activity;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.REQ_Add_Medicine;
import com.xtelsolution.xmec.model.REQ_Edit_Disease;
import com.xtelsolution.xmec.model.RESP_Disease_Detail;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.model.RESP_User_Medicine;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.presenter.EditDiseasePresenter;
import com.xtelsolution.xmec.views.adapter.DiseaseAutoCompleteAdapter;
import com.xtelsolution.xmec.views.adapter.MedicineAdapterOptionButton;
import com.xtelsolution.xmec.views.adapter.MedicineAutoCompleteAdapter;
import com.xtelsolution.xmec.views.inf.IEditDiseaseView;
import com.xtelsolution.xmec.views.smallviews.DelayAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 4/4/2017.
 */

public class EditDiseaseActivity extends BasicActivity implements IEditDiseaseView, ItemClickListener.ButtonAdapterClickListener, ItemClickListener.ItemIconClickListener {
    private Toolbar mToolbar;
    private RESP_Disease_Detail diseaseDetail;
    private DelayAutoCompleteTextView etFindDisease;
    private DelayAutoCompleteTextView etFindMedicine;
    private ProgressBar progressBarDisease;
    private Button btnAddDisease;
    private DiseaseAutoCompleteAdapter diseaseAdapter;
    private MedicineAutoCompleteAdapter medicineAdapter;
    private EditText etNote;
    private Dialog mDialog;
    private RESP_Disease_Detail uDisease;
    private Context mContext;
    private RESP_Medicine medicine;
    private int idMecine = -1;
    private int idDisease = -1;
    private int idUdisease;
    private Button btnAddMedicine;
    private ProgressBar progressBarMedicine;
    private EditDiseasePresenter presenter;
    private RecyclerView rvMedicine;
    private int idMedical = -1;
    private Intent intent;
    private MedicineAdapterOptionButton medicineAdapterOptionButton;
    private AlertDialog.Builder dialogRemoveDisease;
    private List<RESP_User_Medicine> userMedicine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);
        setUi(findViewById(R.id.container_all));
        mContext = getBaseContext();
        initUI();
        initRecycleView();
        initControl();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        intent = new Intent();
        initDiseaseAutocompletext();
        initMedicineAutocompletext();

        presenter = new EditDiseasePresenter(this);
        uDisease = (RESP_Disease_Detail) getIntent().getSerializableExtra(Constant.DISEASE_DETAIL);
        idMedical = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);
        if (uDisease != null) {
            idUdisease = uDisease.getId();
            etFindDisease.setText(uDisease.getTen_benh());
            etNote.setText(uDisease.getNote());
            if (uDisease.getData() != null)
                medicineAdapterOptionButton.addAll(uDisease.getData());
        }
    }

    private void initMedicineAutocompletext() {
        medicineAdapter = new MedicineAutoCompleteAdapter();
        etFindMedicine.setThreshold(3);
        etFindMedicine.setLoadingIndicator(progressBarMedicine);
        etFindMedicine.setAdapter(medicineAdapter);

    }

    private void initDiseaseAutocompletext() {
        diseaseAdapter = new DiseaseAutoCompleteAdapter();
        etFindDisease.setThreshold(3);
        etFindDisease.setLoadingIndicator(progressBarDisease);
        etFindDisease.setAdapter(diseaseAdapter);

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        etFindDisease = (DelayAutoCompleteTextView) findViewById(R.id.etFindIllness);
        rvMedicine = (RecyclerView) findViewById(R.id.rvMedicineWithEditButton);
        progressBarDisease = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        btnAddDisease = (Button) findViewById(R.id.btnSaveIllness);
        etNote = (EditText) findViewById(R.id.et_note);
        mDialog = new Dialog(EditDiseaseActivity.this);
        mDialog.setContentView(R.layout.dialog_add_medical);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnAddMedicine = (Button) mDialog.findViewById(R.id.btn_add_medicine);
        progressBarMedicine = (ProgressBar) mDialog.findViewById(R.id.pb_loading_indicator1);
        etFindMedicine = (DelayAutoCompleteTextView) mDialog.findViewById(R.id.et_find_medicine);

        dialogRemoveDisease = new AlertDialog.Builder(EditDiseaseActivity.this);
        dialogRemoveDisease.setTitle("Bạn có muốn xóa bệnh này?");
        dialogRemoveDisease.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.checkRemoveDisease(idUdisease);
                dialogInterface.dismiss();
            }
        });
        dialogRemoveDisease.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    private void initControl() {
        btnAddDisease.setText("Cập nhật bệnh");
        btnAddDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkEditDisease(new REQ_Edit_Disease(idUdisease, etFindDisease.getText().toString(), etNote.getText().toString(), idDisease));
            }
        });
        medicineAdapterOptionButton.setButtonAdapterClickListener(this);
        medicineAdapterOptionButton.setIconClickListener(this);
        medicineAdapterOptionButton.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
//                if (item == null) {
//                    showToast("Item Null: " + position);
//                } else {
//                    showToast("Item: " + position);
//                }
            }
        });
        etFindDisease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etFindDisease.setThreshold(1000);
                Disease disease = (Disease) adapterView.getItemAtPosition(i);
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
                presenter.checkAddMedicine(new REQ_Add_Medicine(idUdisease, etFindMedicine.getText().toString(), idMecine));
                idMecine = -1;
                etFindMedicine.setText(null);
                mDialog.dismiss();

            }
        });
    }


    private void initRecycleView() {
        userMedicine = new ArrayList<>();
        medicineAdapterOptionButton = new MedicineAdapterOptionButton(userMedicine, true, this);
        rvMedicine.setAdapter(medicineAdapterOptionButton);
        rvMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onButtonAdapterClickListener() {
        mDialog.show();
    }

    @Override
    public void onItemIconClickListener(Object item, final int positon) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditDiseaseActivity.this);
        alertDialog.setTitle("Bạn có muốn xóa Thuốc này?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.checkRemoveMedicine(userMedicine.get(positon).getId(), positon);
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

    @Override
    public void onEditDiseaseSuccess() {
        showToast("Cập nhật thành công");
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onRemoveMedicineSuccess(int index) {
        medicineAdapterOptionButton.removeItem(index);
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onAddMedicineSuccess(RESP_User_Medicine medicine) {
        medicineAdapterOptionButton.addItem(medicine);
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onRemoveDiseaseSuccess() {
        showToast("Xóa bệnh thành công");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(Constant.REMOVE_DISEASE_CODE);
                finish();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (uDisease != null) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_remove_medical, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_medical:
                dialogRemoveDisease.show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
