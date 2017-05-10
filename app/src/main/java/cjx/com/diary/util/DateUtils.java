package cjx.com.diary.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: bear
 *
 * @Description: 时间相关的工具类
 *
 * @date: 2017/5/10
 */
public class DateUtils {
    public static String getCurrentTime(){
        return convertDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    }

    public static String convertDate(Date date, String format){
        if(date!=null){
            DateFormat format1=new SimpleDateFormat(format);
            String s=format1.format(date);
            return s;
        }
        return "";
    }
}
