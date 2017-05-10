package cjx.com.diary.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.diary.DaoSession;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.mode.diary.DiaryDao;

/**
 * @author: bear
 *
 * @Description: 日记相关工具类
 *
 * @date: 2017/5/10
**/

public class DiaryUtils {
    /**
     * 添加日记
     *
     * @param diary
     */
    public static boolean addDiary(Diary diary) {
        boolean isAddSuccess = false;
        DaoSession dao = MyApplication.INSTANCE.getDaoSession();
        DiaryDao diaryDao = dao.getDiaryDao();
        List<Diary> tem = diaryDao.queryBuilder().where(DiaryDao.Properties.Uid.eq(diary.uid)).list();
        if (tem == null || tem.size() == 0) {
            diaryDao.insert(diary);
            isAddSuccess = true;
        }
        return isAddSuccess;
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
            Collections.reverse(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除日记
     * @param diary
     * @return
     */
    public static boolean delete(Diary diary) {
        boolean isDeleted = false;
        try {
            DaoSession dao = MyApplication.INSTANCE.getDaoSession();
            DiaryDao diaryDao = dao.getDiaryDao();
            diaryDao.delete(diary);
            isDeleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            isDeleted = false;
        }
        return isDeleted;
    }


}
