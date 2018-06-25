package bear.com.data.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import bear.com.data.repository.db.model.UserModel;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM UserModel")
    List<UserModel> getAll();
    @Query("SELECT * FROM UserModel WHERE uid = :uid")
    UserModel getUserInfoByUid(String uid);

    @Query("SELECT * FROM UserModel WHERE account = :account LIMIT 1" )
    UserModel getUserInfoByAccount(String account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(UserModel... users);

    @Update
    void updateUsers(UserModel... users);

    @Delete
    void deleteUsers(UserModel... users);
}
