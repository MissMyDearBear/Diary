package cjx.com.diary.presenter.impl;

import android.content.Context;

import java.util.HashMap;

import bear.com.data.repository.UserRepositoryImpl;
import bear.com.data.repository.api.RouteImpl;
import bear.com.data.repository.converter.UserModelconverterImpl;
import bear.com.data.repository.db.database.DiaryDataBase;
import bear.com.domain.repository.Response;
import bear.com.domain.cases.userCase.RegisterCase;
import cjx.com.diary.base.BaseView;
import cjx.com.diary.presenter.RegisterPresenter;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/20.
 */
public class RegisterPresentImpl implements RegisterPresenter {
    private RegisterCase mRegisterCase;

    @Override
    public Response register(String account, String psd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("psd", psd);
        return mRegisterCase.execute(map);
    }

    @Override
    public void removeAllUser() {
        mRegisterCase.removeAllUser();
    }

    @Override
    public void bindView(BaseView view, Object o) {
        mRegisterCase = new RegisterCase();
        mRegisterCase.setUserRepository(new UserRepositoryImpl(DiaryDataBase.getInstance((Context) view), new RouteImpl(), new UserModelconverterImpl()));
    }
}
