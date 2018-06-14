package bear.com.data.user;

import java.util.Map;

import bear.com.data.user.api.Route;
import bear.com.data.user.converter.UserModelConverter;
import bear.com.data.user.db.database.DiaryDataBase;
import bear.com.data.user.db.model.UserModel;
import bear.com.domain.model.User;
import bear.com.domain.repository.UserRepository;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public final class UserRepositoryImpl implements UserRepository {

    private DiaryDataBase mDb;

    private Route mRoute;

    private UserModelConverter mUmc;

    public UserRepositoryImpl(DiaryDataBase db, Route route, UserModelConverter umc) {
        this.mDb = db;
        this.mRoute = route;
        this.mUmc = umc;
    }

    @Override
    public User getUserInfo(Map map) {
        User user=null;
        UserModel useModel=mRoute.login(map);
        if(useModel!=null){
        mDb.userDao().insertUsers(useModel);
            user=mUmc.login(useModel);
        }
        return user;
    }

    @Override
    public boolean isRemoveUser(String uid) {
        UserModel user = mDb.userDao().getUserInfoByUid(uid);
        if (user != null) {
            mDb.userDao().deleteUsers(user);
        }
        return true;
    }
}
