package cjx.com.diary.presenter;


import android.support.v4.app.Fragment;

import java.util.List;

import cjx.com.diary.base.BasePresenter;

/**
 * Created by bear on 2017/4/26.
 */

public interface MainPresenter extends BasePresenter {
    List<Fragment> getFragmentList();

}
