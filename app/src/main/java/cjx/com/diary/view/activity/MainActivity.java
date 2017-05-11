package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import cjx.com.diary.view.fragment.FindFragment;
import cjx.com.diary.view.fragment.HomePageFragment;
import cjx.com.diary.view.fragment.PersonalFragment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    FragmentManager fm;

    HomePageFragment homePageFragment;

    FindFragment findFragment;

    PersonalFragment personalFragment;

    int mCurrentFragmentIndex=0;

    public static void action(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private MainPresenterImp mPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(0);
                        return true;
                    case R.id.navigation_dashboard:
                        switchFragment(1);
                        return true;
                    case R.id.navigation_notifications:
                        switchFragment(2);
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
        switchFragment(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        switchFragment(0);
    }

    /**
     * 切换fragment
     */
    public void switchFragment(int fragmentIndex) {
        mCurrentFragmentIndex = fragmentIndex;
        if (fm == null)
            fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (homePageFragment == null) {
            homePageFragment = (HomePageFragment) HomePageFragment.newInstance();
            transaction.add(R.id.fl_container, homePageFragment);
        }
        if (findFragment == null) {
            findFragment = (FindFragment) FindFragment.newInstance();
            transaction.add(R.id.fl_container, findFragment);
        }
        if (personalFragment == null) {
            personalFragment = (PersonalFragment) PersonalFragment.newInstance();
            transaction.add(R.id.fl_container, personalFragment);
        }
        transaction.hide(homePageFragment).hide(findFragment).hide(personalFragment);
        switch (fragmentIndex) {
            case 0:
                transaction.show(homePageFragment);
                break;
            case 1:
                transaction.show(findFragment);
                break;
            case 2:
                transaction.show(personalFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
        mNavigationView.getMenu().getItem(mCurrentFragmentIndex).setChecked(true);
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
}
