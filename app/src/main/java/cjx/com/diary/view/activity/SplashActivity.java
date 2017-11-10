package cjx.com.diary.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.presenter.impl.SplashPresenterImpl;
import cjx.com.diary.view.SplashView;

/**
 * Created by bear on 2017/4/17.
 */

public class SplashActivity extends BaseActivity implements SplashView {
    @BindView(R.id.btn_skip)
    Button mSkipBtn;

    private SplashPresenterImpl mSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mSplashPresenter = new SplashPresenterImpl();
        mSplashPresenter.bindView(SplashActivity.this, null);
        mSplashPresenter.setData();
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        jumpToMainActivity();
    }

    @Override
    public void jumpToMainActivity() {
        MainActivity.action(mActivity);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.clearTimer();
    }
}
