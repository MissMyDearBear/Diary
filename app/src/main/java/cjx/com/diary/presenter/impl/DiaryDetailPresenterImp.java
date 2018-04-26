package cjx.com.diary.presenter.impl;

import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.presenter.DiaryDetailPresenter;
import cjx.com.diary.thirdtools.rx.rxbus.RxBus;
import cjx.com.diary.thirdtools.rx.rxbus.RxBusAction;
import cjx.com.diary.util.DiaryUtils;

/**
 * description: 日记详情presenter实现类
 * author: bear .
 * Created date:  2017/5/11.
 */
public class DiaryDetailPresenterImp extends MyPresenterImpl implements DiaryDetailPresenter {

    @Override
    public boolean delete(Diary diary) {
       boolean isSuccess= DiaryUtils.delete(diary);
       if(isSuccess){
           RxBus.get().post(RxBusAction.DIARY_DELETE,diary);
       }
        return isSuccess;
    }


    @Override
    public boolean save(Diary diary) {
        boolean isSuccess= DiaryUtils.addDiary(diary);
        if(isSuccess){
            RxBus.get().post(RxBusAction.DIARY_ADD,diary);
        }
        return isSuccess;
    }


    @Override
    public boolean update(Diary diary) {
        boolean isSuccess= DiaryUtils.update(diary);
        if(isSuccess){
            RxBus.get().post(RxBusAction.DIARY_UPDATE,diary);
        }
        return isSuccess;
    }

    @Override
    public Diary query(String id) {
        return DiaryUtils.queryDiaryByUid(id);
    }
}
