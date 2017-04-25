package cjx.com.diary.mode.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by bear on 2017/4/17.
 */
@Entity(indexes = {
        @Index(value = "account",unique = true)
})
public class UserBean {
    @Id
    public long id;
    public String account;
    public String passWord;
    public String mobile;
    public String email;
@Generated(hash = 1328729131)
public UserBean(long id, String account, String passWord, String mobile,
        String email) {
    this.id = id;
    this.account = account;
    this.passWord = passWord;
    this.mobile = mobile;
    this.email = email;
}
@Generated(hash = 1203313951)
public UserBean() {
}
public long getId() {
    return this.id;
}
public void setId(long id) {
    this.id = id;
}
public String getAccount() {
    return this.account;
}
public void setAccount(String account) {
    this.account = account;
}
public String getPassWord() {
    return this.passWord;
}
public void setPassWord(String passWord) {
    this.passWord = passWord;
}
public String getMobile() {
    return this.mobile;
}
public void setMobile(String mobile) {
    this.mobile = mobile;
}
public String getEmail() {
    return this.email;
}
public void setEmail(String email) {
    this.email = email;
}

}
