package bear.com.domain.cases.userCase;

import java.util.Map;

import bear.com.domain.model.User;
import bear.com.domain.type.UserCase;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public final class LoginCase extends BaseUserCase implements UserCase<Map,User>{


    @Override
    public User execute(Map map) {
        return mUserRepository.getUserInfo(map);
    }
}
