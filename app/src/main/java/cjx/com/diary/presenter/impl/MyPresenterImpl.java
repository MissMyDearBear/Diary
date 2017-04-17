package cjx.com.diary.presenter.impl;

import cjx.com.diary.base.BasePresenter;
import cjx.com.diary.base.BaseView;

/**
 * Created by bear on 2017/4/17.
 */

public class MyPresenterImpl implements BasePresenter {
    public BaseView mView;
    public Object mObject;
    @Override
    public void bindView(BaseView view, Object o) {
        this.mObject=0;
        this.mView=view;

    }
}
