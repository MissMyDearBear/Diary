package cjx.com.diary.util;

import java.util.ArrayList;
import java.util.List;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.diary.DaoSession;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.mode.diary.DiaryDao;

/**
 * Created by bear on 2017/5/9.
 */

public class DiaryUtils {
    /**
     * 添加日记
     *
     * @param diary
     */
    public static void addDiary(Diary diary) {
        DaoSession dao = MyApplication.INSTANCE.getDaoSession();
        DiaryDao diaryDao = dao.getDiaryDao();
        List<Diary> tem = diaryDao.queryBuilder().where(DiaryDao.Properties.Id.eq(diary.id)).list();
        if (tem == null || tem.size() == 0) {
            diaryDao.insert(diary);
        }
    }

    /**
     * 获取日记列表
     *
     * @return
     */
    public static List<Diary> getDiaryList() {
        List<Diary> list = new ArrayList<>();
        try {
            DaoSession dao = MyApplication.INSTANCE.getDaoSession();
            list.addAll(dao.getDiaryDao().loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
