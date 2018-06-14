package bear.com.domain.repository;


import java.util.Map;

import bear.com.domain.model.User;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public interface UserRepository {
  User getUserInfo(Map map);
  boolean isRemoveUser(String uid);
}
