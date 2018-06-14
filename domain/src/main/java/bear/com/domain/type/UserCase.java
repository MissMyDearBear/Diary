package bear.com.domain.type;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/12.
 */
public interface UserCase <I,P>{
    P execute(I input);
}
