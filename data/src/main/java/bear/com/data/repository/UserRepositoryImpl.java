package bear.com.data.repository;

import android.text.TextUtils;

import java.util.Map;
import java.util.UUID;

import bear.com.data.repository.api.Route;
import bear.com.data.repository.converter.UserModelConverter;
import bear.com.data.repository.db.database.DiaryDataBase;
import bear.com.data.repository.db.model.UserModel;
import bear.com.domain.model.User;
import bear.com.domain.repository.Response;
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
        User user = null;
        String account=map.get("account").toString();
        String psd=map.get("psd").toString();
        UserModel useModel = mDb.userDao().getUserInfoByAccount(account);
        if (useModel != null&& TextUtils.equals(psd,useModel.psd)) {
            user = mUmc.login(useModel);
        }
        return user;
    }

    @Override
    public Response isRemoveUser(String uid) {
        UserModel user = mDb.userDao().getUserInfoByUid(uid);
        if (user != null) {
            mDb.userDao().deleteUsers(user);
        }
        Response response = new Response("1", "删除成功");
        return response;
    }

    @Override
    public Response registerUser(Map map) {
        String message;
        String status = "0";
        String account = map.get("account").toString();
        String psd = map.get("psd").toString();
        UserModel user = mDb.userDao().getUserInfoByAccount(account);
        if (user != null) {
            message = "账号已注册";
        } else {
            UserModel userModel = new UserModel(UUID.randomUUID().toString()
                    , "", account, psd, "");
            mDb.userDao().insertUsers(userModel);
            message = "账号注册成功";
            status = "1";
        }
        Response response = new Response(status, message);
        return response;
    }

    @Override
    public void removeAllUser() {
        mDb.clearAllTables();
    }
}
