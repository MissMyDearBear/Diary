package bear.com.data.repository.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
@Entity(indices = {@Index(value = {"uid", "account"},
        unique = true)})
public class UserModel {
    @NonNull
    @PrimaryKey
    public String uid;
    public String name;

    public String account;
    public String psd;
    public String mobile;

    public UserModel(String uid, String name, String account, String psd,
                     String mobile) {
        this.uid = uid;
        this.name = name;
        this.account = account;
        this.psd = psd;
        this.mobile = mobile;
    }
}
