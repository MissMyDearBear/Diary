package bear.com.data.user.converter;

import bear.com.data.user.db.model.UserModel;
import bear.com.domain.model.User;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public interface UserModelConverter {
    User login(UserModel userModel);

    boolean logout(String uid);
}
