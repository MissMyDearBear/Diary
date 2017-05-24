package cjx.com.diary.presenter.impl;

import cjx.com.diary.api.ApiService;
import cjx.com.diary.common.Mock;
import cjx.com.diary.common.MyObserver;
import cjx.com.diary.mode.QiuBaiBean;
import cjx.com.diary.presenter.FindPresenter;
import cjx.com.diary.util.GsonUtils;
import cjx.com.diary.view.fragment.FindFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bear on 2017/4/27.
 */

public class FindPresenterImp extends MyPresenterImpl implements FindPresenter {
    @Override
    public void getQiuBai() {
        FindFragment findFragment = (FindFragment) mView;
        ApiService.getApiService().getQiuBai()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(qiuBaiBean -> qiuBaiBean.data)
                .subscribeWith(new MyObserver<QiuBaiBean.DataBean>() {
                    @Override
                    public void onSuccess(QiuBaiBean.DataBean dataBean) {
                        if (dataBean != null && dataBean.items != null) {
                            findFragment.onRefresh(dataBean.items);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        QiuBaiBean bean = GsonUtils.jsonToClass(Mock.getQiuBai(), QiuBaiBean.class);
                        findFragment.onRefresh(bean.data.items);
                    }

                    @Override
                    public void onFinish() {
                        findFragment.setRefresh(false);
                    }
                });
    }
}
