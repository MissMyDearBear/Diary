package bear.com.data.repository.api;

import java.util.Map;

import bear.com.data.repository.db.model.UserModel;

/**
 * description: API接口定义
 * author: bear .
 * Created date:  2018/6/14.
 */
public interface Route {
    UserModel login(Map map);

    boolean logOut(String uid);
}
