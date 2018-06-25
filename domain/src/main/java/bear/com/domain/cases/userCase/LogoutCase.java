package bear.com.domain.cases.userCase;

import bear.com.domain.repository.Response;
import bear.com.domain.type.UserCase;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public final class LogoutCase extends BaseUserCase implements UserCase<String, Response> {


    @Override
    public Response execute(String input) {
        return mUserRepository.isRemoveUser(input);
    }
}
