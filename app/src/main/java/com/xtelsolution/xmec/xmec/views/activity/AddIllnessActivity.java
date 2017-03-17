package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.xmec.views.adapter.DiseaseAutoCompleteAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapterWithEditButton;
import com.xtelsolution.xmec.xmec.views.smallviews.DelayAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HUNGNT on 2/14/2017.
 */

public class AddIllnessActivity extends BasicActivity {
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private DelayAutoCompleteTextView etFindDisease;
    private ProgressBar progressBar;
    private Context mContext;

    private int idMedical;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);
        mContext=getBaseContext();
        initUI();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initRecycleView();
        idMedical =getIntent().getIntExtra(Constant.MEDICAL_ID,-1);

        etFindDisease.setThreshold(3);
        etFindDisease.setLoadingIndicator(progressBar);
        etFindDisease.setAdapter(new DiseaseAutoCompleteAdapter());
        
    }

    private void initRecycleView() {
        ArrayList<Medicine> listMedicine = new ArrayList<>();
        listMedicine.add(new Medicine(0, "Tên thuốc", "tpye"));
        listMedicine.add(new Medicine(0, "Tên thuốc", "tpye"));
        listMedicine.add(new Medicine(0, "Tên thuốc", "tpye"));
        MedicineAdapterWithEditButton mAdapter = new MedicineAdapterWithEditButton(this, listMedicine);
        mAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                if (item == null) {
                    showToast("Item Null: " + position);
                } else {
                    showToast("Item: " + position);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        etFindDisease= (DelayAutoCompleteTextView) findViewById(R.id.etFindIllness);
        recyclerView = (RecyclerView) findViewById(R.id.rvMedicineWithEditButton);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);


    }
    private List<Disease> createTempData(int size) {
        List<Disease> sticks = new ArrayList<>();

        for (int i = size; i < size + 10; i++) {

            sticks.add(new Disease(i, "Tên Bệnh " + i));
        }
        return sticks;
    }
}
