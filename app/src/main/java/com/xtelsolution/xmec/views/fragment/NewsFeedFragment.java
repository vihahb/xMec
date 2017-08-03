package com.xtelsolution.xmec.views.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.views.inf.ScreenShotable;

//import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class NewsFeedFragment extends Fragment implements ScreenShotable {
    private View view;
    private View containerView;
    private Bitmap bitmap;
    private View mainView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static NewsFeedFragment newInstance() {

        Bundle args = new Bundle();

        NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
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
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return NewsFragment.newInstance("http://suckhoe.vn/dinh-duong/1", "Dinh dưỡng");
                case 1:
                    return NewsFragment.newInstance("http://suckhoe.vn/loi-song/1", "Lối sống");
                case 2:
                    return NewsFragment.newInstance("http://suckhoe.vn/lam-dep/1", "Làm đẹp");
                case 3:
                    return NewsFragment.newInstance("http://suckhoe.vn/cac-benh/1", "Các bệnh");
                case 4:
                    return NewsFragment.newInstance("http://suckhoe.vn/thuoc/1", "Thuốc");
                case 5:
                    return NewsFragment.newInstance("http://suckhoe.vn/gioi-tinh/1", "Giới tính");
                case 6:
                    return NewsFragment.newInstance("http://suckhoe.vn/me-be/1", "Mẹ & Bé");
                case 7:
                    return NewsFragment.newInstance("http://suckhoe.vn/trac-nghiem/1", "Trắc nghiệm");
                case 8:
                    return NewsFragment.newInstance("http://suckhoe.vn/hoi-dap/1", "Hỏi đáp");
                case 9:
                    return NewsFragment.newInstance("http://suckhoe.vn/meo-vat/1", "Mẹo vặt");
                case 10:
                    return NewsFragment.newInstance("http://suckhoe.vn/giai-tri/1", "Giải trí");
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 11;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Dinh dưỡng";
                case 1:
                    return "Lối sống";
                case 2:
                    return "Làm đẹp";
                case 3:
                    return "Các bệnh";
                case 4:
                    return "Thuốc";
                case 5:
                    return "Giới tính";
                case 6:
                    return "Mẹ & Bé";
                case 7:
                    return "Trắc nghiệm";
                case 8:
                    return "Hỏi đáp";
                case 9:
                    return "Mẹo vặt";
                case 10:
                    return "Giải trí";
            }
            return null;
        }
    }
}
