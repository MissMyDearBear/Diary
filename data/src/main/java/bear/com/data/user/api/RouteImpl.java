package bear.com.data.user.api;

import android.text.TextUtils;

import java.util.Map;

import bear.com.data.user.db.model.UserModel;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/14.
 */
public class RouteImpl implements Route {
    @Override
    public UserModel login(Map map) {
        String account = map.get("account").toString();
        String psd = map.get("psd").toString();
        if (TextUtils.equals(account, "user") && TextUtils.equals(
                psd, "qqqqqq11")) {
            UserModel userModel = new UserModel("user_001", "Bear",
                    "user", "qqqqqq11", "12345678901");
            return userModel;
        }

        return null;
    }

    @Override
    public boolean logOut(String uid) {
        if (TextUtils.equals(uid, "user_001")) {
            return true;
        }
        return false;
    }
}
