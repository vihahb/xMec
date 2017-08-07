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
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.callbacks.DialogActionListener;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.dialog.DialogAddFriend;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.entity.UserMedical;
import com.xtelsolution.xmec.presenter.HomePresenter;
import com.xtelsolution.xmec.views.activity.AddMedicalDetailActivity;
import com.xtelsolution.xmec.views.activity.LoginActivity;
import com.xtelsolution.xmec.views.activity.MedicalDetailActivity;
import com.xtelsolution.xmec.views.activity.ProfileActivity;
import com.xtelsolution.xmec.views.adapter.MedicalDirectoryAdapter;
import com.xtelsolution.xmec.views.adapter.PagerUserAdapter;
import com.xtelsolution.xmec.views.inf.IHomeView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HUNGNT on 1/18/2017
 */

public class HomeFragment extends BasicFragment implements /*ScreenShotable,*/ IHomeView, ItemClickListener, ItemClickListener.ButtonAdapterClickListener {

    private static final String TAG = "HomeFragment";
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
    private List<UserMedical> listUserMedical;
    private TextView tv_state;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        presenter = new HomePresenter(this);
        mlistMedica = new ArrayList<>();
        mlistUsers = new ArrayList<>();
        listUserMedical = new ArrayList<>();
        adapter = new MedicalDirectoryAdapter(mlistMedica, getContext());
        adapter.setItemClickListener(this);
        adapter.setButtonAdapterClickListener(this);
        userAdapter = new PagerUserAdapter(mContext, mlistUsers);
        checkLogin();
    }

    private void checkLogin() {
        if (!SharedPreferencesUtils.getInstance().isLogined()) {
            userAdapter.setLogin(false);
        } else {
            userAdapter.setLogin(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        initControl();
        presenter.checkGetUser(userModel);
//        presenter.checkGetMedical(adapter.getList());
        presenter.getMedicalReportBookDefault();
        presenter.getUserFriend();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    public void initUI(View view) {
        tv_state = (TextView) view.findViewById(R.id.tv_state);
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
                else {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra(Constant.OBJECT, user);
                    startActivityForResult(intent, Constant.UPDATE_PROFILE);
                }
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

        userAdapter.setDeleteItemClickListener(new PagerUserAdapter.ItemDeleteClickListener() {
            @Override
            public void onClick(int position, final RESP_User user) {
                showActionDialog("Bạn có muốn xóa " + user.getFullname() + " khỏi danh sách bạn bè?", "Có", new DialogActionListener() {
                    @Override
                    public void onActionProcess() {
                        presenter.deleteFriend(user.getUid());
                    }
                });
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
        pagerUser.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                if (adapterPosition < mlistUsers.size()) {
                    if (adapterPosition != 0 && adapterPosition <= mlistUsers.size() - 1) {
                        int id_uid = mlistUsers.get(adapterPosition).getUid();
                        if (listUserMedical.get(adapterPosition).getMedical() != null) {
                            adapter.addCleanAll(listUserMedical.get(adapterPosition).getMedical(), false);
                        } else {
                            presenter.getMedicalFromUserId(id_uid);
                        }
                    } else {
                        presenter.getMedicalReportBookDefault();
                    }
                }
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
        setStateVisible(null);
        loadingProgress.setVisibility(View.GONE);
        rvDisease.setVisibility(View.VISIBLE);
        if (user == null) {
            return;
        }
        user.setMaster(true);
        mlistUsers.add(0, user);
        listUserMedical.add(0, new UserMedical(user, null));
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
    public void onGetFriendActiveSuccess(ArrayList<RESP_User> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            RESP_User user = arrayList.get(i);
            if (user == null) {
                return;
            }
            user.setMaster(false);
            listUserMedical.add(new UserMedical(user, null));
            mlistUsers.add(user);
            Log.e(TAG, "onGetFriendActiveSuccess: add user " + i);
        }
        userAdapter.notifyDataSetChanged();
        Log.e(TAG, "list uer: " + mlistUsers.size());
    }

    @Override
    public void getMedicalFromUIdSuccess(ArrayList<RESP_Medical> arrayList, int uid) {
        int position = getPositionByUid(uid);
        if (position != -1) {
            if (arrayList.size() == 0) {
                setStateVisible("Không có dữ liệu.");
            } else {
                setStateVisible(null);
                listUserMedical.get(getPositionByUid(uid)).setMedical(arrayList);
                adapter.addCleanAll(listUserMedical.get(position).getMedical(), false);
            }
        }

    }

    private void setStateVisible(String message) {
        if (message != null) {
            tv_state.setText(message);
            tv_state.setVisibility(View.VISIBLE);
        } else {
            tv_state.setVisibility(View.GONE);
        }
    }

    private int getPositionByUid(int uid) {
        int position = -1;

        for (int i = 0; i < listUserMedical.size(); i++) {
            if (uid == listUserMedical.get(i).getUser().getUid()) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void getMedicalFromUIdError(String mes) {

    }

    @Override
    public void requireLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onGetMedicalDefault(ArrayList<RESP_Medical> arrayList) {
        if (arrayList.size() == 0) {
            setStateVisible("Không có dữ liệu");
        }
        if (mlistMedica.size() > 0) {
            mlistMedica.clear();
        }
        mlistMedica.addAll(arrayList);
        listUserMedical.get(0).setMedical(mlistMedica);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItemSuccess(int uid) {
        showToast("Xóa bạn bè thành công");
        userAdapter.remove(getPositionByUid(uid));
        listUserMedical.remove(getPositionByUid(uid));
    }

    @Override
    public void onDeleteItemError(String message) {
        showToast("Xóa bạn bè thất bại");
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
