package cjx.com.diary.presenter.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import bear.com.data.user.UserRepositoryImpl;
import bear.com.data.user.api.RouteImpl;
import bear.com.data.user.converter.UserModelconverterImpl;
import bear.com.data.user.db.database.DiaryDataBase;
import bear.com.domain.model.User;
import bear.com.domain.userCase.LoginCase;
import bear.com.domain.userCase.LogoutCase;
import cjx.com.diary.base.BaseView;
import cjx.com.diary.presenter.LoginPresenter;
import cjx.com.diary.util.Utils;

/**
 * Created by bear on 2017/4/17.
 */

public class LoginPresenterImpl extends MyPresenterImpl implements LoginPresenter {

    private LoginCase mLoginCase;
    private LogoutCase mLogoutCase;

    @Override
    public void bindView(BaseView view, Object o) {
        super.bindView(view, o);
        mLoginCase = new LoginCase();
        mLogoutCase = new LogoutCase();
        mLoginCase.setmUserRepository(new UserRepositoryImpl(DiaryDataBase.getInstance((Context) view), new RouteImpl(), new UserModelconverterImpl()));
        mLogoutCase.setmUserRepository(new UserRepositoryImpl(DiaryDataBase.getInstance((Context) view), new RouteImpl(), new UserModelconverterImpl()));
    }

    @Override
    public void login(String account, String psd) {
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("psd", psd);
        User user = mLoginCase.execute(map);
        if(user!=null){
        Utils.showToast((Context) mView,user.toString());
        }else{
            Utils.showToast((Context) mView,"账号密码错误");
        }

    }

    @Override
    public void jumpToRegister(Context context) {
        Utils.showToast(context, "注册");
    }

    @Override
    public boolean logout(String uid) {
        return  mLogoutCase.execute(uid);
    }
}
