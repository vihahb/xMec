package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapterWithEditButton;

import java.util.ArrayList;


/**
 * Created by HUNGNT on 2/14/2017.
 */

public class AddIllnessActivity extends BasicActivity {
    private RecyclerView recyclerView;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_illness);

        init();
        initRecycleView();
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

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.rvMedicineWithEditButton);
    }
}
