package cjx.com.diary.presenter.impl;

import android.os.CountDownTimer;

import cjx.com.diary.presenter.SplashPresenter;
import cjx.com.diary.view.activity.SplashActivity;

/**
 * Created by bear on 2017/4/17.
 */

public class SplashPresenterImpl extends MyPresenterImpl implements SplashPresenter {

private final String mSloganStr="欢\n迎\n来\n到\n小\n熊\n日\n记\n!";


    @Override
    public void setData() {
        SplashActivity mSplashActivity= (SplashActivity) mView;
        mSplashActivity.showSlogan(mSloganStr);
        mTimer.start();
    }

    @Override
    public void clearTimer() {
        if(null!=mTimer){
            mTimer.cancel();
        }
    }

    private CountDownTimer mTimer=new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            ((SplashActivity)mView).jumpToMainActivity();

        }
    };
}
