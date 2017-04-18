package com.xtelsolution.xmec.xmec.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.EndlessParentScrollListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.HomePresenter;
import com.xtelsolution.xmec.xmec.views.activity.AddMedicalDetailActivity;
import com.xtelsolution.xmec.xmec.views.activity.MedicalDetailActivity;
import com.xtelsolution.xmec.xmec.views.activity.ProfileActivity;
import com.xtelsolution.xmec.xmec.views.adapter.MedicalDirectoryAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IHomeView;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class HomeFragment extends BasicFragment implements IHomeView, ItemClickListener, ItemClickListener.ButtonAdapterClickListener {
    private static final String TAG = "HomeFragment";

    private RESP_User userModel;
    private MedicalDirectoryAdapter adapter;
    private RecyclerView rvDisease;
    private ImageView imgAvatar;
    private TextView btnProfile;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvHeight;
    private TextView tvWeight;
    private HomePresenter presenter;
    private Context mContext;
    private ArrayList<RESP_Medical> mlistMedica;
    private ImageView imgGender;
    private CoordinatorLayout progcess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        presenter = new HomePresenter(this);
        mlistMedica = new ArrayList<>();
        adapter = new MedicalDirectoryAdapter(mlistMedica, getContext());
        adapter.setItemClickListener(this);
        adapter.setButtonAdapterClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        initControl();
        presenter.checkGetUser(userModel);
        presenter.checkGetMedical(adapter.getList());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDisease.setLayoutManager(manager);
        rvDisease.setNestedScrollingEnabled(false);
//        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.spcategorize);
//        spinner.setItems("Bệnh", "Thuốc");
        rvDisease.setAdapter(adapter);
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
        progcess = (CoordinatorLayout) view.findViewById(R.id.progress_bar);

    }

    private void initControl() {
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(mContext, ProfileActivity.class));
                startActivityForResult(new Intent(mContext, ProfileActivity.class), Constant.UPDATE_PROFILE);
            }
        });
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
        setImage(imgAvatar, user.getAvatar());
        if (user.getGender() == 2) {
            imgGender.setVisibility(View.VISIBLE);
            imgGender.setImageResource(R.drawable.ic_action_name);
        } else if (user.getGender() == 1) {
            imgGender.setVisibility(View.VISIBLE);
            imgGender.setImageResource(R.drawable.ic_man);
        } else {
            imgGender.setVisibility(View.GONE);
        }
        SharedPreferencesUtils.getInstance().saveUser(user);
        progcess.setVisibility(View.GONE);

    }

    @Override
    public void onGetMediacalListSusscess(boolean useCache, RESP_List_Medical list_medical) {
        Log.e(TAG, "onGetMediacalListSusscess: " + list_medical.getList().size());
        if (!useCache)
            adapter.addAll(list_medical.getList());
    }

    @Override
    public void onItemClickListener(Object item, int position) {

        Intent intent = new Intent(getActivity(), MedicalDetailActivity.class);
        intent.putExtra(Constant.MEDICAL_ID, ((RESP_Medical) item).getId());
        intent.putExtra(Constant.MEDICAL_INDEX, (position));
        startActivityForResult(intent, 94);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADDMEDICAL_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                RESP_Medical medical = (RESP_Medical) data.getSerializableExtra(Constant.MEDICAL_ADD_SUSSCESS);
                adapter.addItem(medical);
            }
        } else if (requestCode == 94) {
            if (resultCode == Activity.RESULT_OK) {
                int index = data.getIntExtra(Constant.MEDICAL_INDEX, -1);
                adapter.removeItem(index);
            } else if (resultCode == Constant.REMOVE_MEDICAL_CODE) {
                adapter.updateItem(data.getStringExtra(Constant.MEDICAL_NAME), data.getLongExtra(Constant.MEDICAL_BEGIN_TIME, -1), data.getIntExtra(Constant.MEDICAL_INDEX, -1));
            }
        } else if (requestCode == Constant.UPDATE_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                RESP_User user = SharedPreferencesUtils.getInstance().getUser();
                tvName.setText(user.getFullname());
                tvBirthday.setText(user.getBirthDayasString());
                tvHeight.setText(String.valueOf(user.getHeight()));
                tvWeight.setText(String.valueOf(user.getWeight()));
                setImage(imgAvatar, user.getAvatar());
                if (user.getGender() == 2) {
                    imgGender.setImageResource(R.drawable.ic_action_name);
                    imgGender.setVisibility(View.VISIBLE);
                } else if (user.getGender() == 1) {
                    imgGender.setImageResource(R.drawable.ic_man);
                    imgGender.setVisibility(View.VISIBLE);
                } else {
                    imgGender.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setNull() {
        tvName.setText(null);
        tvBirthday.setText(null);
        tvHeight.setText(null);
        tvWeight.setText(null);
        imgGender.setVisibility(View.GONE);
        progcess.setVisibility(View.GONE);

    }

    @Override
    public void onButtonAdapterClickListener() {
//        if (SharedPreferencesUtils.getInstance().isLogined()) {
            Intent i = new Intent(getActivity(), AddMedicalDetailActivity.class);
            startActivityForResult(i, Constant.ADDMEDICAL_CODE);
//        } else {
//            showLoginDialog();
//        }
    }

}
