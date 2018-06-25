package bear.com.domain.cases.userCase;

import bear.com.domain.repository.UserRepository;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public abstract class BaseUserCase {
    protected UserRepository mUserRepository;

    public void setUserRepository(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }
}
