package com.xtelsolution.xmec.views.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.REQ_Medicine;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.model.entity.DrugEntity;
import com.xtelsolution.xmec.presenter.Fragment.DrugDetailsFragmentPresenter;
import com.xtelsolution.xmec.views.activity.AddIllnessActivity;
import com.xtelsolution.xmec.views.activity.LoginActivity;
import com.xtelsolution.xmec.views.adapter.DrugListAdapter;
import com.xtelsolution.xmec.views.adapter.MedicineAdapterWithEditButton;
import com.xtelsolution.xmec.views.adapter.MedicineAutoCompleteAdapter;
import com.xtelsolution.xmec.views.inf.IDrugDetailsFragment;
import com.xtelsolution.xmec.views.smallviews.DelayAutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public class DrugDetailsFragment extends Fragment implements IDrugDetailsFragment,
        ItemClickListener, ItemClickListener.ButtonAdapterClickListener, ItemClickListener.ItemIconClickListener {

    private static final String TAG = "DrugDetailsFragment";

    private RecyclerView rcl_Drug;
    private DrugDetailsFragmentPresenter presenter;
    private int id;
    private ArrayList<DrugEntity> arrayList;
    private Button btnAction;
    private DrugListAdapter adapter;
    private TextView message;
    private ProgressBar progress_bar;
    private Dialog mDialog;
    private Button btnAddMedicine;
    private ProgressBar progressBarMedicine;
    private DelayAutoCompleteTextView etFindMedicine;
    private MedicineAutoCompleteAdapter medicineAdapter;
    private MedicineAdapterWithEditButton medicineAdapterWithEditButton;
    private int idMecine;
    private RESP_Medicine medicine;

    public static DrugDetailsFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(Constant.MEDICAL_ID, id);
        DrugDetailsFragment fragment = new DrugDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicine_in_medical_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getInt(Constant.MEDICAL_ID);
        initView(view);
        initDialog(view);
    }

    private void initDialog(View view) {
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.dialog_add_medical);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btnAddMedicine = (Button) mDialog.findViewById(R.id.btn_add_medicine);
        progressBarMedicine = (ProgressBar) mDialog.findViewById(R.id.pb_loading_indicator1);
        etFindMedicine = (DelayAutoCompleteTextView) mDialog.findViewById(R.id.et_find_medicine);
        medicineAdapter = new MedicineAutoCompleteAdapter();

        etFindMedicine.setThreshold(3);
        etFindMedicine.setLoadingIndicator(progressBarMedicine);
        etFindMedicine.setAdapter(medicineAdapter);

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
                String MedicineName = etFindMedicine.getText().toString();

                presenter.addDrugToMedicineReport(id, idMecine, "");
//                medicineAdapterWithEditButton.add(new REQ_Medicine(etFindMedicine.getText().toString(), idMecine));

            }
        });
    }

    private void initView(View view) {
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnAction = (Button) view.findViewById(R.id.btnAction);
        message = (TextView) view.findViewById(R.id.textError);
        rcl_Drug = (RecyclerView) view.findViewById(R.id.rList);
        arrayList = new ArrayList<>();

        btnAction.setVisibility(View.GONE);
        presenter = new DrugDetailsFragmentPresenter(this);
        adapter = new DrugListAdapter(arrayList);
        adapter.setButtonAdapterClickListener(this);
        adapter.setIconClickListener(this);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcl_Drug.setHasFixedSize(false);
        rcl_Drug.setLayoutManager(layoutManager);
        rcl_Drug.setAdapter(adapter);
        presenter.getListDrug(id);
    }

    @Override
    public void getDrugListSuccess(ArrayList<DrugEntity> arrayList) {
        Log.e(TAG, "getDrugListSuccess: arr size" + arrayList.size());
        adapter.refreshAdapter(arrayList);
        rcl_Drug.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void getDrugListError(String mes) {
        showToast(mes);
    }

    @Override
    public void requireLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showDialogAdd() {
        mDialog.show();
    }

    @Override
    public void addDrugSucces() {
        presenter.getListDrug(id);
        idMecine = -1;
        etFindMedicine.setText(null);
        mDialog.dismiss();
        showToast("Thêm thuốc thành công");
    }

    @Override
    public void addDrugError() {
        idMecine = -1;
        etFindMedicine.setText(null);
        mDialog.dismiss();
        showToast("Thêm thuốc thất bại");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLog(String TAG, String msg) {

    }

    @Override
    public void showProgressDialog(String title) {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void onItemClickListener(Object item, int position) {

    }

    @Override
    public void onItemIconClickListener(Object item, int positon) {

    }

    @Override
    public void onButtonAdapterClickListener() {
        mDialog.show();
    }
}
