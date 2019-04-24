package cjx.com.diary.util;

import cjx.com.diary.base.DiaryApplication;
import cjx.com.diary.mode.user.UserBean;
import cjx.com.diary.mode.weight.DaoSession;

/**
 * @author: bear
 *
 * @Description: 用户相关工具类
 *
 * @date: 2017/5/10
 */

public class UserUtils {
    public static UserBean getAccountInfo(){
        DaoSession dao = DiaryApplication.INSTANCE.getDaoSession();
        if(dao==null||dao.getUserBeanDao()==null||dao.getUserBeanDao().loadAll().size()==0)
            return null;
       return dao.getUserBeanDao().load((long) 1);
    }

    /**
     * 是否登录
     * @return
     */
    public static boolean isUserLogin(){
        return getAccountInfo()!=null;
    }
}
