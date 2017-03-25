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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.EndlessParentScrollListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.presenter.SearchNewsPresenter;
import com.xtelsolution.xmec.xmec.views.activity.DetailDiseaseActivity;
import com.xtelsolution.xmec.xmec.views.activity.DiseaseDiagnosiActivity;
import com.xtelsolution.xmec.xmec.views.adapter.IllnessAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.NewsAdapter;
import com.xtelsolution.xmec.xmec.views.inf.ISearchNewsView;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMarginHorizontal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 */


public class SearchFragment extends BasicFragment implements ISearchNewsView {
    private final String TAG = SearchFragment.class.getName();
    private RecyclerView rvResultFindNews, rvResultFindIllness;
    private Button btnDiseaseDiagnos;
    private NestedScrollView NscrollView;
    private Handler handler;
    private NewsAdapter adapter;
    private Context mContext;
    private IllnessAdapter illnessAdapter;
    private List<Disease> listMedicines;
    private SearchNewsPresenter newsPresenter;
    private ArrayList<NewsFeed> mNewsFeeds;
    private EditText etFindAll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        handler = new Handler();
        listMedicines = new ArrayList<>();
        mNewsFeeds = new ArrayList<>();
        adapter = new NewsAdapter(getContext(), mNewsFeeds);
        illnessAdapter = new IllnessAdapter(mContext, listMedicines);
        newsPresenter = new SearchNewsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initRecyclerView();

        btnDiseaseDiagnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), DiseaseDiagnosiActivity.class);

                getActivity().startActivity(i);
            }
        });
    }

    private void initRecyclerView() {
        //////////////////////////////////////////////////
        rvResultFindNews.setAdapter(adapter);
        rvResultFindNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerViewMarginHorizontal decoration = new RecyclerViewMarginHorizontal((int) (getContext().getResources().getDisplayMetrics().density * 8f + 0.5f));

        rvResultFindNews.addItemDecoration(decoration);
        ////
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rvResultFindIllness.setAdapter(illnessAdapter);

        rvResultFindIllness.setLayoutManager(manager);

        NscrollView.setOnScrollChangeListener(new EndlessParentScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        illnessAdapter.addAll(createTempData(illnessAdapter.getItemCount()));
                    }
                }, 1500);

            }

            @Override
            public void onHide() {
//                xLog.e(TAG, "hide");
                mContext.sendBroadcast(new Intent(Constant.ACTION_HIDE_BOTTOM_BAR));
            }

            @Override
            public void onShow() {
//                xLog.e(TAG, "show");
                mContext.sendBroadcast(new Intent(Constant.ACTION_SHOW_BOTTOM_BAR));
            }
        });
        illnessAdapter.addAll(createTempData(0));

        illnessAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                Intent i = new Intent(mContext, DetailDiseaseActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    private void initView(View view) {

        rvResultFindNews = (RecyclerView) getActivity().findViewById(R.id.rvResultFindNews);
        rvResultFindIllness = (RecyclerView) getActivity().findViewById(R.id.rvResultFindIllnesses);
        NscrollView = (NestedScrollView) getActivity().findViewById(R.id.NscrollView);
        btnDiseaseDiagnos = (Button) getActivity().findViewById(R.id.btn_Disease_Dianosi);
        etFindAll = (EditText) view.findViewById(R.id.etSearch1);
        rvResultFindIllness.setNestedScrollingEnabled(false);
        etFindAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etFindAll.getRight() - etFindAll.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(mContext, "hshasd", Toast.LENGTH_SHORT).show();
                        newsPresenter.searchNews(etFindAll.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private List<Disease> createTempData(int size) {
        List<Disease> sticks = new ArrayList<>();
        for (int i = size; i < size + 10; i++) {
            sticks.add(new Disease(i, "Tên Bệnh " + i));
        }
        return sticks;
    }

    @Override
    public void updateResult(ArrayList<NewsFeed> listNewsFeeds) {
        mNewsFeeds.clear();
        mNewsFeeds.addAll(listNewsFeeds);
        adapter.notifyDataSetChanged();
    }
}
