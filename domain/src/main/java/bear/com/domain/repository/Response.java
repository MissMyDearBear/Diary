package bear.com.domain.repository;

/**
 * description: 动作相应（用于获取各种用户操作后的反馈）
 * author: bear .
 * Created date:  2018/6/19.
 */
public class Response {
    public String status;//状态值（0：失败，1：成功）
    public String message;

    public Response(String s1, String s2) {
        this.status = s1;
        this.message = s2;
    }

    public boolean isOk(){
        return  status.equals("1");
    }
}
