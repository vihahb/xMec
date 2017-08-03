package com.xtelsolution.xmec.views.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.presenter.MedicalDetailPresenter;
import com.xtelsolution.xmec.views.activity.AddIllnessActivity;
import com.xtelsolution.xmec.views.activity.MedicalDetailActivity;
import com.xtelsolution.xmec.views.activity.UserDiseaseDetailActivity;
import com.xtelsolution.xmec.views.adapter.IllnessAdapterWithEditButton;
import com.xtelsolution.xmec.views.inf.IMedicalDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhChung on 09/06/2017.
 */

public class MedicineInMedicalDetailFragment extends Fragment implements IMedicalDetailView,
        ItemClickListener, ItemClickListener.ButtonAdapterClickListener, ItemClickListener.ItemIconClickListener {
    private String TAG = "MedicineInMedicalDetailFragment";
    private RecyclerView rcDesease;
    private Context mContext;
    private IllnessAdapterWithEditButton illnessAdapter;
    private int id;
    private MedicalDetailPresenter presenter;
    private ArrayList<RESP_Disease> diseases;
    private TextView message;
    private ProgressBar progress_bar;
    private Button btnAction;

    public static MedicineInMedicalDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(Constant.MEDICAL_ID, id);
        MedicineInMedicalDetailFragment fragment = new MedicineInMedicalDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        id = getArguments().getInt(Constant.MEDICAL_ID);
        presenter = new MedicalDetailPresenter(this);
        diseases = new ArrayList<>();
        illnessAdapter = new IllnessAdapterWithEditButton(mContext, diseases);
        illnessAdapter.setButtonAdapterClickListener(this);
        illnessAdapter.setIconClickListener(this);
        illnessAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                int idDisease = ((RESP_Disease) item).getId();
                xLog.e(TAG, "onCreate: LINK  " + idDisease);
                Intent i = new Intent(mContext, UserDiseaseDetailActivity.class);
                i.putExtra(Constant.DISEASE_ID, idDisease);
                i.putExtra(Constant.MEDICAL_ID, id);
                startActivityForResult(i, Constant.DETAIL_USER_DISEASE_CODR);

            }
        });
        presenter.checkGetDetailMedical(id);
        presenter.checkGetListIllness(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_in_medical_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcDesease = (RecyclerView) view.findViewById(R.id.rList);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnAction = (Button) view.findViewById(R.id.btnAction);
        message = (TextView) view.findViewById(R.id.textError);
        rcDesease.setAdapter(illnessAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(mContext));
        ((MedicalDetailActivity) getActivity()).sendData(this);
    }

    @Override
    public void showToast(String msg) {

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
        /*dialog.show();*/
    }

    @Override
    public void onButtonAdapterClickListener() {
        Intent i = new Intent(mContext, AddIllnessActivity.class);
        i.putExtra(Constant.MEDICAL_ID, id);
        startActivity(i);
    }


    @Override
    public void onItemIconClickListener(Object item, int positon) {
//        Intent i = new Intent(MedicalDetailActivity.this, AddIllnessActivity.class);
//        i.putExtra(Constant.MEDICAL_ID, id);
//        startActivity(i);
    }


    @Override
    public void onLoadListIllnessFinish(List<RESP_Disease> data) {
        illnessAdapter.addAll(data);
        Log.e(TAG, "onLoadListIllnessFinish: list size " + data.size());
        rcDesease.setVisibility(View.VISIBLE);
        btnAction.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateMedicalFinish() {

    }

    @Override
    public void onRemoveMedicalSuccess() {

    }

    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {

    }


}
