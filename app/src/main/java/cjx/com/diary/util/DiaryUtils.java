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
     * 删除日记
     * @param diary
     * @return
     */
    public static boolean delete(Diary diary) {
        boolean isDeleted ;
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

    /**
     * 更新日记
     * @param diary
     * @return
     */
    public static boolean update(Diary diary){
        boolean isUpdate;
        try {
            DaoSession dao = MyApplication.INSTANCE.getDaoSession();
            DiaryDao diaryDao = dao.getDiaryDao();
            diaryDao.update(diary);
            isUpdate=true;
        } catch (Exception e) {
            e.printStackTrace();
            isUpdate=false;
        }
        return isUpdate;
    }

    /**
     * 根据uid查询对应的日记
     * @param uid
     * @return
     */
     public static Diary queryDiaryByUid(String uid){
         DaoSession dao = MyApplication.INSTANCE.getDaoSession();
         DiaryDao diaryDao = dao.getDiaryDao();
         List<Diary> diary = diaryDao.queryBuilder().where(DiaryDao.Properties.Uid
                 .eq(uid)).list();
         if(diary!=null&&diary.size()==1){
             return diary.get(0);
         }else{
             return null;
         }
     }
}
