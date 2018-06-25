package bear.com.data.repository.converter;

import bear.com.data.repository.db.model.UserModel;
import bear.com.domain.model.User;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public final class UserModelconverterImpl implements UserModelConverter{
    @Override
    public User login(UserModel userModel) {
        User user=null;
        if(userModel!=null){
         user=new User(userModel.name,userModel.uid,userModel.account,userModel.psd,
                userModel.mobile);}
        return user;
    }

    @Override
    public boolean logout(String uid) {

        return false;
    }
}
