package cjx.com.diary.util;

import cjx.com.diary.base.MyApplication;
import cjx.com.diary.mode.diary.DaoSession;
import cjx.com.diary.mode.user.UserBean;

/**
 * 用户相关
 * Created by bear on 2017/5/5.
 */

public class UserUtils {
    public static UserBean getAccountInfo(){
        DaoSession dao = MyApplication.INSTANCE.getDaoSession();
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
