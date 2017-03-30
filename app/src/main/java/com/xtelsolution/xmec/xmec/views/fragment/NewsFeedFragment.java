package com.xtelsolution.xmec.xmec.views.fragment;

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

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class NewsFeedFragment extends Fragment {

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
    }

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
                    return new NewsFragment("http://songkhoe.vn/widget.rss", "Nổi bật");
                case 1:
                    return new NewsFragment("http://songkhoe.vn/tam-su.rss", "Tâm sự");
                case 2:
                    return new NewsFragment("http://songkhoe.vn/gioi-tinh.rss", "Giới tính");
                case 3:
                    return new NewsFragment("http://songkhoe.vn/dinh-duong.rss", "Dinh dưỡng");
                case 4:
                    return new NewsFragment("http://songkhoe.vn/thoi-su.rss", "Thời sự");
                case 5:
                    return new NewsFragment("http://songkhoe.vn/lam-dep.rss", "Làm đẹp");
                case 6:
                    return new NewsFragment("http://songkhoe.vn/lam-me.rss", "Làm mẹ");
                case 7:
                    return new NewsFragment("http://songkhoe.vn/vui-khoe.rss", "Vui khỏe");
                case 8:
                    return new NewsFragment("http://songkhoe.vn/can-biet.rss", "Cần biết");
            }

            return new NewsFragment("", "");
        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 9;
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
            }
            return null;
        }
    }
}
