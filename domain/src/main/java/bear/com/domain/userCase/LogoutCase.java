package bear.com.domain.userCase;

import bear.com.domain.type.UserCase;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public final class LogoutCase extends BaseUserCase implements UserCase<String,Boolean>{


    @Override
    public Boolean execute(String input) {
        return mUserRepository.isRemoveUser(input);
    }
}
