package com.xtelsolution.xmec.xmec.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.view.menu.ShowableListMenu;
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
import com.xtelsolution.xmec.xmec.views.adapter.PagerUserAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IHomeView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;


/**
 * Created by HUNGNT on 1/18/2017.
 */

public class HomeFragment extends BasicFragment implements /*ScreenShotable,*/ IHomeView, ItemClickListener, ItemClickListener.ButtonAdapterClickListener {
    private static final String TAG = "HomeFragment";
    private View view;
    private View containerView;
    private Bitmap bitmap;

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
    //    private ImageView imgAvatar;
//    private TextView btnProfile;
//    private TextView tvName;
//    private TextView tvBirthday;
//    private TextView tvHeight;
//    private TextView tvWeight;
//    private TextView tvIBM;
    private HomePresenter presenter;
    private Context mContext;
    private ArrayList<RESP_Medical> mlistMedica;
    private ArrayList<RESP_User> mlistUsers;
//    private ImageView imgGender;
//    private CoordinatorLayout progcess;


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
//        btnProfile = (TextView) view.findViewById(R.id.btnProfile);
//        tvBirthday = (TextView) view.findViewById(R.id.tv_birthday);
//        tvHeight = (TextView) view.findViewById(R.id.tv_profile_height);
//        tvWeight = (TextView) view.findViewById(R.id.tv_profile_weight);
//        tvIBM = (TextView) view.findViewById(R.id.tv_profile_ibm);
//        tvName = (TextView) view.findViewById(R.id.tv_profile_name);
//        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
//        imgGender = (ImageView) view.findViewById(R.id.img_gender);
//        progcess = (CoordinatorLayout) view.findViewById(R.id.progress_bar);
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);
        pagerUser = (DiscreteScrollView) view.findViewById(R.id.pagerUser);
        this.view = view;
        containerView = view.findViewById(R.id.container);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDisease.setLayoutManager(manager);
        rvDisease.setNestedScrollingEnabled(false);
        rvDisease.setAdapter(adapter);


        final NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);


        scrollView.scrollTo(0, 0);

        pagerUser.setNestedScrollingEnabled(false);
        pagerUser.setAdapter(userAdapter);

        userAdapter.setItemUpDateClickListener(new PagerUserAdapter.ItemUpDateClickListener() {
            @Override
            public void onClick(int position, RESP_User user) {
                startActivity(new Intent(mContext, ProfileActivity.class));
                if (!isLogin())
                    showLoginDialog();
                else
                    startActivityForResult(new Intent(mContext, ProfileActivity.class), Constant.UPDATE_PROFILE);
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

    private void initControl() {
//        btnProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(mContext, ProfileActivity.class));
//                if (!isLogin())
//                    showLoginDialog();
//                else
//                    startActivityForResult(new Intent(mContext, ProfileActivity.class), Constant.UPDATE_PROFILE);
////                getActivity().finish();
//            }
//        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUerSusscess(RESP_User user) {
        if (user == null) {
            setNull();
            return;
        }
        mlistUsers.add(user);
        userAdapter.notifyDataSetChanged();
//        tvName.setText(user.getFullname());
//        tvBirthday.setText(user.getBirthDayasString());
//        tvHeight.setText(String.valueOf(user.getHeight()));
//        tvWeight.setText(String.valueOf(user.getWeight()));
//        try {
//            DecimalFormat format = new DecimalFormat("##.##");
//            tvIBM.setText(format.format(user.getWeight() * 100 * 100 / (user.getHeight() * user.getHeight())));
//        } catch (Exception e) {
//            tvIBM.setText("0.0");
//        }
//
//        setImage(imgAvatar, user.getAvatar());
//        if (user.getGender() == 2) {
//            imgGender.setVisibility(View.VISIBLE);
//            imgGender.setImageResource(R.drawable.ic_action_name);
//        } else if (user.getGender() == 1) {
//            imgGender.setVisibility(View.VISIBLE);
//            imgGender.setImageResource(R.drawable.ic_man);
//        } else {
//            imgGender.setVisibility(View.GONE);
//        }
//        SharedPreferencesUtils.getInstance().saveUser(user);
//        progcess.setVisibility(View.GONE);

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
//            if (resultCode == Activity.RESULT_OK) {
//                RESP_User user = SharedPreferencesUtils.getInstance().getUser();
//                tvName.setText(user.getFullname());
//                tvBirthday.setText(user.getBirthDayasString());
//                tvHeight.setText(String.valueOf(user.getHeight()));
//                tvWeight.setText(String.valueOf(user.getWeight()));
//                setImage(imgAvatar, user.getAvatar());
//                if (user.getGender() == 2) {
//                    imgGender.setImageResource(R.drawable.ic_action_name);
//                    imgGender.setVisibility(View.VISIBLE);
//                } else if (user.getGender() == 1) {
//                    imgGender.setImageResource(R.drawable.ic_man);
//                    imgGender.setVisibility(View.VISIBLE);
//                } else {
//                    imgGender.setVisibility(View.GONE);
//                }
//            }
        }
    }

    private void setNull() {
//        tvName.setText(null);
//        tvBirthday.setText(null);
//        tvHeight.setText(null);
//        tvWeight.setText(null);
//        imgGender.setVisibility(View.GONE);
//        progcess.setVisibility(View.GONE);

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
//
//    @Override
//    public void takeScreenShot() {
//        try {
//            Thread thread = new Thread() {
//                @Override
//                public void run() {
//                    if (containerView == null) {
//                        containerView = view.findViewById(R.id.container);
//                    }
//                    Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                            containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(bitmap);
//                    containerView.draw(canvas);
//                    HomeFragment.this.bitmap = bitmap;
//                }
//            };
//
//            thread.start();
//        } catch (Exception e) {
//            Log.e(TAG, "takeScreenShot: ", new Throwable(e));
//        }
//    }

//    @Override
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
}
