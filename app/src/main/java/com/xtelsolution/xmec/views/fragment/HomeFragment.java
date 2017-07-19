package com.xtelsolution.xmec.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.dialog.DialogAddFriend;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.HomePresenter;
import com.xtelsolution.xmec.views.activity.AddMedicalDetailActivity;
import com.xtelsolution.xmec.views.activity.MedicalDetailActivity;
import com.xtelsolution.xmec.views.activity.ProfileActivity;
import com.xtelsolution.xmec.views.adapter.MedicalDirectoryAdapter;
import com.xtelsolution.xmec.views.adapter.PagerUserAdapter;
import com.xtelsolution.xmec.views.inf.IHomeView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;


/**
 * Created by HUNGNT on 1/18/2017.
 */

public class HomeFragment extends BasicFragment implements /*ScreenShotable,*/ IHomeView, ItemClickListener, ItemClickListener.ButtonAdapterClickListener {

    private static final String TAG = "HomeFragment";

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RESP_User userModel;
    private MedicalDirectoryAdapter adapter;
    private RecyclerView rvDisease;
    private DiscreteScrollView pagerUser;
    private PagerUserAdapter userAdapter;
    private ProgressBar loadingProgress;
    private HomePresenter presenter;
    private Context mContext;
    private ArrayList<RESP_Medical> mlistMedica;
    private ArrayList<RESP_User> mlistUsers;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        presenter = new HomePresenter(this);
        mlistMedica = new ArrayList<>();
        mlistUsers = new ArrayList<>();
        adapter = new MedicalDirectoryAdapter(mlistMedica, getContext());
        adapter.setItemClickListener(this);
        adapter.setButtonAdapterClickListener(this);
        userAdapter = new PagerUserAdapter(mContext, mlistUsers);

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
        initUI(view);

    }

    public void initUI(View view) {
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);
        pagerUser = (DiscreteScrollView) view.findViewById(R.id.pagerUser);
        loadingProgress = (ProgressBar) view.findViewById(R.id.loadingProgress);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDisease.setLayoutManager(manager);
        rvDisease.setNestedScrollingEnabled(false);
        rvDisease.setAdapter(adapter);
        final NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        scrollView.scrollTo(0, 0);
        pagerUser.setNestedScrollingEnabled(false);
        pagerUser.setAdapter(userAdapter);


    }

    private void initControl() {
        userAdapter.setItemUpDateClickListener(new PagerUserAdapter.ItemUpDateClickListener() {
            @Override
            public void onClick(int position, RESP_User user) {
                if (!isLogin())
                    showLoginDialog();
                else
                    startActivityForResult(new Intent(mContext, ProfileActivity.class), Constant.UPDATE_PROFILE);
            }
        });

        userAdapter.setItemAddClickListener(new PagerUserAdapter.ItemAddClickListener() {
            @Override
            public void onClick() {
                if (!isLogin())
                    showLoginDialog();
                else {
                    DialogAddFriend.DiaLodAddFriend(mContext).show();
                }
            }
        });

        pagerUser.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {

            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                if (adapterPosition < mlistUsers.size()) {
                    rvDisease.setVisibility(View.VISIBLE);
                } else {
                    rvDisease.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(float scrollPosition, @NonNull RecyclerView.ViewHolder currentHolder, @NonNull RecyclerView.ViewHolder newCurrent) {

            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadindView() {
        loadingProgress.setVisibility(View.VISIBLE);
        rvDisease.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetUerSusscess(RESP_User user) {
        loadingProgress.setVisibility(View.GONE);
        rvDisease.setVisibility(View.VISIBLE);
        if (user == null) {
            return;
        }
        mlistUsers.add(user);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMediacalListSusscess(boolean useCache, RESP_List_Medical list_medical) {
        if (list_medical == null) {
            return;
        }
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

        }
    }


    @Override
    public void onButtonAdapterClickListener() {
        if (SharedPreferencesUtils.getInstance().isLogined()) {
            Intent i = new Intent(getActivity(), AddMedicalDetailActivity.class);
            startActivityForResult(i, Constant.ADDMEDICAL_CODE);
        } else {
            showLoginDialog();
        }
    }
}
