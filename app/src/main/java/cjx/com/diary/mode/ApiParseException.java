package cjx.com.diary.mode;

/**
 * Created by bear on 2017/4/17.
 */

public class ApiParseException extends RuntimeException {
    public String message;
    public String status;
    public String data;

    public ApiParseException(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiParseException(String status, String message, String data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
