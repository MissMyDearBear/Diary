package cjx.com.diary.presenter.impl;

import bear.com.data.repository.NewsRepositoryImpl;
import bear.com.data.repository.converter.NewsModelConverterImpl;
import bear.com.domain.cases.newsCase.NewsCase;
import cjx.com.diary.presenter.FindPresenter;
import cjx.com.diary.view.fragment.FindFragment;

/**
 * Created by bear on 2017/4/27.
 */

public class FindPresenterImp extends MyPresenterImpl implements FindPresenter {
    @Override
    public void getQiuBai() {
        FindFragment findFragment = (FindFragment) mView;
        NewsCase newsCase = new NewsCase(new NewsRepositoryImpl(new NewsModelConverterImpl()));
        findFragment.onRefresh(newsCase.getQiuBai());
    }
}
