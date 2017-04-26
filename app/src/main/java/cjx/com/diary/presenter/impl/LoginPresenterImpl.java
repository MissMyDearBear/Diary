package cjx.com.diary.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.user.UserBean;
import cjx.com.diary.mode.user.UserBeanDao;
import cjx.com.diary.presenter.LoginPresenter;
import cjx.com.diary.util.Utils;

/**
 * Created by bear on 2017/4/17.
 */

public class LoginPresenterImpl extends MyPresenterImpl implements LoginPresenter {

    @Override
    public void login(String account, String psd) {
        UserBeanDao userBeanDao= MyApplication.INSTANCE.getDaoSession().getUserBeanDao();
        QueryBuilder<UserBean> query = userBeanDao.queryBuilder();

        List<UserBean> userBeanList=query.where(UserBeanDao.Properties.Account.eq(account)).list();
        if(userBeanList!=null&&userBeanList.size()>0){
            for(int i=0;i<userBeanList.size();i++){
                UserBean item=userBeanList.get(i);
                if(TextUtils.equals(item.account,account)){
                    if(TextUtils.equals(item.passWord,psd)){
                        Utils.showToast((Context) mView,"登录成功");
                        ((BaseActivity)mView).finish();

                    }else{
                        Utils.showToast((Context) mView,"账号或密码错误");

                    }
                    break;
                }
            }
        }else{
            Utils.showToast((Context) mView,"该账号未注册");
        }

    }

    @Override
    public void jumpToRegister(Context context) {
        Utils.showToast(context, "注册");
    }
}
