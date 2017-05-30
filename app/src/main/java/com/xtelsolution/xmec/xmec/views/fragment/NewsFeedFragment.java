package com.xtelsolution.xmec.xmec.views.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;

//import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class NewsFeedFragment extends Fragment /*implements ScreenShotable*/ {
    private View view;
    private View containerView;
    private Bitmap bitmap;
    public static NewsFeedFragment newInstance() {

        Bundle args = new Bundle();

        NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private View mainView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        }
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mViewPager == null) {
            mViewPager = (ViewPager) view.findViewById(R.id.vpNewsFeed);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsNewsFeed);
            tabLayout.setupWithViewPager(mViewPager);
        }
        mSectionsPagerAdapter.notifyDataSetChanged();
    }
/*
    @Override
    public void takeScreenShot() {
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
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }*/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            switch (position) {
                case 0:
                    return NewsFragment.newInstance("http://songkhoe.vn/widget.rss", "Nổi bật");
                case 1:
                    return NewsFragment.newInstance("http://songkhoe.vn/tam-su.rss", "Tâm sự");
                case 2:
                    return NewsFragment.newInstance("http://songkhoe.vn/gioi-tinh.rss", "Giới tính");
                case 3:
                    return NewsFragment.newInstance("http://songkhoe.vn/dinh-duong.rss", "Dinh dưỡng");
                case 4:
                    return NewsFragment.newInstance("http://songkhoe.vn/thoi-su.rss", "Thời sự");
                case 5:
                    return NewsFragment.newInstance("http://songkhoe.vn/lam-dep.rss", "Làm đẹp");
                case 6:
                    return NewsFragment.newInstance("http://songkhoe.vn/lam-me.rss", "Làm mẹ");
                case 7:
                    return NewsFragment.newInstance("http://songkhoe.vn/vui-khoe.rss", "Vui khỏe");
                case 8:
                    return NewsFragment.newInstance("http://songkhoe.vn/can-biet.rss", "Cần biết");
                case 9:
                    return NewsFragment.newInstance("http://songkhoe.vn/video.rss", "Video");
            }

            return NewsFragment.newInstance("", "");
        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Nổi bật";
                case 1:
                    return "Tâm sự";
                case 2:
                    return "Giới tính";
                case 3:
                    return "Dinh dưỡng";
                case 4:
                    return "Thời sự";
                case 5:
                    return "Làm đẹp";
                case 6:
                    return "Làm mẹ";
                case 7:
                    return "Vui khỏe";
                case 8:
                    return "Cần biết";
                case 9:
                    return "Video";
            }
            return null;
        }
    }
}
