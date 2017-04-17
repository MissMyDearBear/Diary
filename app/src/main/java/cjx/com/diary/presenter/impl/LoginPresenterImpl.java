package cjx.com.diary.presenter.impl;

import android.content.Context;

import cjx.com.diary.presenter.LoginPresenter;
import cjx.com.diary.util.Utils;

/**
 * Created by bear on 2017/4/17.
 */

public class LoginPresenterImpl extends MyPresenterImpl implements LoginPresenter {

    @Override
    public void login(String account, String psd) {

    }

    @Override
    public void jumpToRegister(Context context) {
        Utils.showToast(context, "注册");
    }
}
