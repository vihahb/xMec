package com.xtelsolution.xmec.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.entity.UserInfo;
import com.xtelsolution.xmec.presenter.Activity.NotificationActivityPresenter;
import com.xtelsolution.xmec.views.activity.inf.INotificationActivity;
import com.xtelsolution.xmec.views.adapter.FriendListAdapter;
import com.xtelsolution.xmec.views.widget.ProgressView;

import java.util.ArrayList;

/**
 * Created by vivhp on 7/27/2017
 */

public class NotificationActivity extends BasicActivity implements INotificationActivity {

    ProgressView progressView;
    TextView tv_title_toolbar;
    private RecyclerView rcl_notification;
    private NotificationActivityPresenter presenter;
    private ArrayList<UserInfo> arrayNotification;
    private FriendListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        presenter = new NotificationActivityPresenter(this);

        initToolbar(R.id.toolbar, "");

        initView();
        initAdapter();
        initGesture();
    }

    private void initView() {
        tv_title_toolbar = (TextView) findViewById(R.id.toolbar_title);
        tv_title_toolbar.setText("Thông báo");
        progressView = new ProgressView(this);
        rcl_notification = (RecyclerView) findViewById(R.id.recyclerview_progress_view);
    }

    private void initAdapter() {
        arrayNotification = new ArrayList<>();
        adapter = new FriendListAdapter(this, arrayNotification, this);
        progressView.setUpRecyclerView(new LinearLayoutManager(getActivity()), adapter);
    }


    private void initGesture() {
        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataList();
            }
        });

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataList();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
                getDataList();
            }
        });
    }

    public void getDataList() {
        setRefresh(true);
        progressView.showData();
        presenter.getNotificationRequest();
    }

    @Override
    public void getNotificationRequestSuccess(ArrayList<UserInfo> data) {
        Log.e("data arr", JsonHelper.toJson(data));
//        for (int i = 0; i < data.size(); i++){
//            UserInfo info = new UserInfo();
//            info = getUserInfo(data.get(i));
//            arrayNotification.add(info);
//        }
        UserInfo info = new UserInfo();
        info.setFullname("Lời mời kết bạn");
        info.setHeader(true);
        arrayNotification.clear();
        arrayNotification.addAll(data);
        arrayNotification.add(0, info);
        adapter.notifyDataSetChanged();
        Log.e("data arr notify", JsonHelper.toJson(arrayNotification));

        setRefresh(false);
        if (arrayNotification.size() == 0) {
            progressView.updateMessage(-1, getString(R.string.error_list_data_empty));
            progressView.showMessage();
        }
    }

    private UserInfo getUserInfo(UserInfo userInfo) {
        UserInfo info = new UserInfo();
        info.setFullname(userInfo.getFullname());
        info.setAddress(userInfo.getAddress());
        info.setAvatar(userInfo.getAvatar());
        info.setBirthday(userInfo.getBirthday());
        info.setGender(userInfo.getGender());
        info.setWeight(userInfo.getWeight());
        info.setHeight(userInfo.getHeight());
        info.setAccepted(false);
        info.setHeader(false);
        return info;
    }

    @Override
    public void getNotificationRequestError(String mes) {
        showToast(mes);
    }

    @Override
    public void onNoNetWork() {
        setRefresh(false);
        if (arrayNotification.size() > 0) {
            showToast("Không có kết nối mạng");
        } else {
            progressView.updateMessage(-1, getString(R.string.error_no_network));
        }
    }

    @Override
    public void onAccept(int uid_friend) {
        presenter.onActiveFriend(uid_friend);
    }

    @Override
    public void onDecline(int uid_friend) {
        presenter.onDeclineFriend(uid_friend);
    }

    @Override
    public void onAccepted(int uid_friend) {
        adapter.setAcceptedItem(uid_friend);
    }

    @Override
    public void onDeclined(int uid_friend) {
        adapter.setRemoveItem(uid_friend);
    }

    @Override
    public void onAuthentExpired() {
        startActivityAndFinish(LoginActivity.class);
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        progressView.setRefreshing(isRefresh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
