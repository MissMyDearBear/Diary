package cjx.com.diary.presenter;

import android.content.Context;

import bear.com.domain.repository.Response;
import cjx.com.diary.base.BasePresenter;

/**
 * Created by bear on 2017/4/17.
 */

public interface LoginPresenter extends BasePresenter{
    /**
     * 登录
     * @param account 账号
     * @param psd 密码
     */
    void login(String account,String psd);

    /**
     * 跳转至注册界面
     * @param context 上下文
     */
    void jumpToRegister(Context context);


    Response logout(String uid);
}
