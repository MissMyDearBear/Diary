package bear.com.domain.cases.userCase;

import java.util.Map;

import bear.com.domain.repository.Response;
import bear.com.domain.type.UserCase;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/19.
 */
public class RegisterCase extends BaseUserCase implements UserCase<Map,Response>{

    @Override
    public Response execute(Map input) {
        return mUserRepository.registerUser(input);
    }

    public void removeAllUser(){
        mUserRepository.removeAllUser();
    }
}
