package cjx.com.diary.presenter.impl;

import android.os.CountDownTimer;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.user.UserBean;
import cjx.com.diary.mode.user.UserBeanDao;
import cjx.com.diary.presenter.SplashPresenter;
import cjx.com.diary.view.activity.SplashActivity;

/**
 * Created by bear on 2017/4/17.
 */

public class SplashPresenterImpl extends MyPresenterImpl implements SplashPresenter {

private final String mSloganStr="欢\n迎\n来\n到\n小\n熊\n日\n记\n!";


    @Override
    public void setData() {
        //创建用户信息
        UserBean userBean = new UserBean();
        userBean.setId((long)1);
        userBean.setAccount("18262282215");
        userBean.setPassWord("111111qq");
        userBean.setEmail("bear@berdatata.com");
        userBean.setMobile("18262282215");
        UserBeanDao dao = MyApplication.INSTANCE.getDaoSession().getUserBeanDao();
        QueryBuilder queryBuilder = dao.queryBuilder();
        List<UserBean> list = queryBuilder.where(UserBeanDao.Properties.Account.eq("18262282215")).list();
        if (list == null || (list != null && list.size() == 0)) {
            dao.insert(userBean);
        }
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
