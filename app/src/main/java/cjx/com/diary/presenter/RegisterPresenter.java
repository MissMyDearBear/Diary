package cjx.com.diary.presenter;

import bear.com.domain.repository.Response;
import cjx.com.diary.base.BasePresenter;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/20.
 */
public interface RegisterPresenter extends BasePresenter {

    Response register(String account, String psd);

    void removeAllUser();

}
