package com.xtelsolution.xmec.xmec.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.EndlessParentScrollListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.presenter.FindMedicinePresenter;
import com.xtelsolution.xmec.xmec.views.activity.MedicineDetailActivity;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapter;
import com.xtelsolution.xmec.xmec.views.inf.ISearchMedicineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 * edit by chungdt 30/03/2017.
 */


public class MedicineFragment extends BasicFragment implements ISearchMedicineView {

    private static final String TAG = MedicineFragment.class.getName();

    private RecyclerView rvResultFindMediacine;
    private FindMedicinePresenter presenter;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> list;
    private Context mContext;
    private Handler handler;
    private View view;
    private EditText etFindMedicine;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        list = new ArrayList<>();
        medicineAdapter = new MedicineAdapter(mContext, list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            return view = inflater.inflate(R.layout.fragment_find_medicine, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (list.size()==0) {
            initView(view.findViewById(R.id.nesstedScrollView));
            setUi(view);
            initControl();
            handler = new Handler();
            presenter = new FindMedicinePresenter(this);
        }

    }

    private void initView(View view) {
        rvResultFindMediacine = (RecyclerView) view.findViewById(R.id.rvResultFindMediacine);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvResultFindMediacine.setLayoutManager(manager);
        rvResultFindMediacine.setNestedScrollingEnabled(false);
        rvResultFindMediacine.setAdapter(medicineAdapter);
        etFindMedicine = (EditText) view.findViewById(R.id.etSearch);

    }

    private void initControl() {
        medicineAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                startActivity(new Intent(mContext, MedicineDetailActivity.class).putExtra(Constant.INTENT_ID_MEDICINE, ((Medicine) item).getId()));
            }
        });
        etFindMedicine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etFindMedicine.getRight() - etFindMedicine.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        medicineAdapter.clear();
                        medicineAdapter.setLoadMore(true);
                        presenter.checkSearchMedicine(etFindMedicine.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
        etFindMedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                medicineAdapter.clear();
                medicineAdapter.setLoadMore(true);
                presenter.checkSearchMedicine(charSequence.toString());
                xLog.d(TAG, "initControl: " + charSequence.toString());
                if (charSequence.length() < 1) medicineAdapter.setLoadMore(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

//    private List<Medicine> createTempData(int size) {
//
//        List<Medicine> list = new ArrayList<>();
//
//        for (int i = size; i < size + 10; i++) {
//
//            list.add(new Medicine(i, "Tên thuốc số " + i, "a"));
//        }
//        return list;
//    }

    @Override
    public void onFindMedicienFinish(List<Medicine> data) {
        medicineAdapter.setLoadMore(false);
        if (data.size() == 0) {
            showToast("Không tìm được kết quả");
        }
        medicineAdapter.addAll(data);
    }

    @Override
    public void onError() {
        medicineAdapter.setLoadMore(false);
    }
}
