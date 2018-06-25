package bear.com.data.repository.converter;

import bear.com.data.repository.db.model.UserModel;
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
