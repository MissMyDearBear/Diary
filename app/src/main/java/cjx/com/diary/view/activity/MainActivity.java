package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjx.com.diary.R;
import cjx.com.diary.api.ApiService;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.common.MyObserver;
import cjx.com.diary.presenter.MainPresenter;
import cjx.com.diary.presenter.impl.MainPresenterImp;
import cjx.com.diary.util.Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    public static void action(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private MainPresenterImp mPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
//                        LoginActivity.action(mActivity);
                        mViewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter=new MainPresenterImp();
        mPresenter.bindView(this,null);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),mPresenter.getFragmentList()));
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(listener);
    }

    private long mLastBackPress = 0;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - mLastBackPress < 2000) {
            super.onBackPressed();
        } else {
            mLastBackPress = time;
            Utils.showToast(mActivity, getString(R.string.tip_exit));
        }
    }
    private class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment>fragments;
        public MyAdapter(FragmentManager fm, List<Fragment>mList) {
            super(fm);
            fragments=mList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
    private ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
          mNavigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
