package cjx.com.diary.presenter.impl;


import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cjx.com.diary.presenter.MainPresenter;
import cjx.com.diary.view.fragment.FindFragment;
import cjx.com.diary.view.fragment.HomePageFragment;
import cjx.com.diary.view.fragment.PersonalFragment;

/**
 * Created by bear on 2017/4/26.
 */

public class MainPresenterImp extends MyPresenterImpl implements MainPresenter {

    @Override
    public List<Fragment> getFragmentList() {
        List<Fragment> list=new ArrayList<>();
        list.add(HomePageFragment.Companion.newInstance());
        list.add(FindFragment.Companion.newInstance());
        list.add(PersonalFragment.Companion.newInstance());
        return list;
    }
}
