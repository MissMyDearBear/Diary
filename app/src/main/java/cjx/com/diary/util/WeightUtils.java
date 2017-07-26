package cjx.com.diary.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.diary.DaoSession;
import cjx.com.diary.mode.weight.BodyWeightBean;
import cjx.com.diary.mode.weight.BodyWeightBeanDao;

/**
 * description: 体重管理
 * author: bear .
 * Created date:  2017/7/26.
 */
public class WeightUtils {
    /**
     * 获取所有数据列表
     *
     * @return
     */
    public static List<BodyWeightBean> getWeightList() {
        List<BodyWeightBean> list = new ArrayList<>();
        try {
            DaoSession daoSession = MyApplication.INSTANCE.getDaoSession();
            BodyWeightBeanDao dao = daoSession.getBodyWeightBeanDao();
            if (dao != null && dao.count() > 0) {
                list.addAll(dao.loadAll());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(BodyWeightBean bodyWeightBean) {
        try {
            DaoSession daoSession = MyApplication.INSTANCE.getDaoSession();
            BodyWeightBeanDao dao = daoSession.getBodyWeightBeanDao();
            if (dao != null) {
                List<BodyWeightBean> tem = dao.queryBuilder().where(BodyWeightBeanDao.Properties.CreatedDate.eq(bodyWeightBean.createdDate)).list();
                if (tem != null && tem.size() == 1) {
                    BodyWeightBean curBodyBean = tem.get(0);
                    if (!TextUtils.isEmpty(bodyWeightBean.morningWeight)) {
                        curBodyBean.morningWeight = bodyWeightBean.morningWeight;
                    }
                    if (!TextUtils.isEmpty(bodyWeightBean.nightWeight)) {
                        curBodyBean.nightWeight = bodyWeightBean.nightWeight;
                    }
                    upDate(curBodyBean);
                } else {
                    dao.insert(bodyWeightBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void upDate(BodyWeightBean bodyWeightBean) {
        try {
            DaoSession daoSession = MyApplication.INSTANCE.getDaoSession();
            BodyWeightBeanDao dao = daoSession.getBodyWeightBeanDao();
            if (dao != null) {
                dao.update(bodyWeightBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
