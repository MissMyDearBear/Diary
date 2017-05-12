package cjx.com.diary.presenter;

import cjx.com.diary.base.BasePresenter;
import cjx.com.diary.mode.diary.Diary;

/**
 * description: 日记详情presenter
 * author: bear .
 * Created date:  2017/5/11.
 */
public interface DiaryDetailPresenter extends BasePresenter {
    /**
     * 删除
     * @param diary
     * @return
     */
    boolean delete(Diary diary);


    /**
     * 保存
     * @param diary
     * @return
     */
    boolean save(Diary diary);

    /**
     * 更新
     * @param diary
     * @return
     */
    boolean update(Diary diary);


    Diary query(String id);

}
