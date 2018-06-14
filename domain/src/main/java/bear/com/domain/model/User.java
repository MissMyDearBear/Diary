package bear.com.domain.model;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String name;
    private String uid;
    private String account;
    private String psd;
    private String mobile;
    public User(String name,String uid,String account,String psd,String mobile){
        this.name=name;
        this.uid=uid;
        this.account=account;
        this.psd=psd;
        this.mobile=mobile;
    }

    @Override
    public String toString() {
        return "User:{" +
                "name:"+this.name+"\n"+
                "uid:"+this.uid+"\n"+
                "account:"+this.account+"\n"+
                "psd:"+this.psd+"\n"+
                "mobile:"+this.mobile+"\n"+

                "}";
    }
}
