package com.xtelsolution.xmec.xmec.views.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.EndlessParentScrollListener;
import com.xtelsolution.xmec.model.RESP_LIST_MEDICAL;
import com.xtelsolution.xmec.model.RESP_MEDICAL;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.HomePresenter;
import com.xtelsolution.xmec.xmec.views.activity.ProfileActivity;
import com.xtelsolution.xmec.xmec.views.inf.IHomeView;
import com.xtelsolution.xmec.xmec.views.adapter.MedicalDirectoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class HomeFragment extends BasicFragment implements IHomeView {
    private MedicalDirectoryAdapter adapter;
    private RecyclerView rvDisease;
    private ImageView imgAvatar;
    private TextView btnProfile;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvHeight;
    private TextView tvWeight;
    private HomePresenter homePresenter;
    private Context mContext;
    private ArrayList<RESP_MEDICAL> sticks;
    private ImageView imgGender;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        sticks = new ArrayList<>();
        homePresenter = new HomePresenter(this);
        sticks.addAll(createTempData(0));
        adapter = new MedicalDirectoryAdapter(sticks,getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        initControl();
        homePresenter.getUser();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);
        rvDisease.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDisease.setLayoutManager(manager);
        rvDisease.setNestedScrollingEnabled(false);
        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.spcategorize);
        spinner.setItems("Bệnh", "Thuốc");
        NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(new EndlessParentScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            }

            @Override
            public void onHide() {

                mContext.sendBroadcast(new Intent(Constant.ACTION_HIDE_BOTTOM_BAR));
            }

            @Override
            public void onShow() {

                mContext.sendBroadcast(new Intent(Constant.ACTION_SHOW_BOTTOM_BAR));
            }
        });


        scrollView.scrollTo(0, 0);

    }

    public void initUI(View view) {
        btnProfile = (TextView) view.findViewById(R.id.btnProfile);
        tvBirthday = (TextView) view.findViewById(R.id.tv_birthday);
        tvHeight = (TextView) view.findViewById(R.id.tv_profile_height);
        tvWeight = (TextView) view.findViewById(R.id.tv_profile_weight);
        tvName = (TextView) view.findViewById(R.id.tv_profile_name);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        imgGender = (ImageView) view.findViewById(R.id.img_gender);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Đang tải");
    }
    private void initControl(){
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });
    }

    private List<RESP_MEDICAL> createTempData(int size) {
        List<RESP_MEDICAL> sticks = new ArrayList<>();

        for (int i = size; i < size + 10; i++) {

            sticks.add(new RESP_MEDICAL("Tên Y Bạ ",1487236138575l,0l,0));
        }
        return sticks;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUerSusscess(RESP_User user) {
        tvName.setText(user.getFullname());
        tvBirthday.setText(user.getBirthDayasString());
        tvHeight.setText(String.valueOf(user.getHeight()));
        tvWeight.setText(String.valueOf(user.getWeight()));
        setImage(imgAvatar,user.getAvatar());
        if (user.getGender()==2)
            imgGender.setImageResource(R.drawable.ic_action_name);
        SharedPreferencesUtils.getInstance().saveUser(user);
        progressDialog.dismiss();
    }

    @Override
    public void onGetMediacalListSusscess(RESP_LIST_MEDICAL user) {

    }

    @Override
    public void showProcessbar() {
        progressDialog.show();
    }

    @Override
    public void hidePRocessbar() {
        progressDialog.dismiss();
    }
}
