package cjx.com.diary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: bear
 * @Description: 时间相关的工具类
 * @date: 2017/5/10
 */
public class DateUtils {
    public static String getCurrentTime() {
        return convertDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    private static String convertDate(Date date, String format) {
        if (date != null) {
            DateFormat format1 = new SimpleDateFormat(format);
            String s = format1.format(date);
            return s;
        }
        return "";
    }

    public static String getCurrentDate() {
        return convertDate(new Date(), "yyyy-MM-dd");
    }


    public static int getWeek(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(stringToDate(date));
        int weekDay = 1;
        switch (ca.get(Calendar.DAY_OF_WEEK)) {
            case 7:
                weekDay = 6;
                break;
            case 1:
                weekDay = 7;
                break;
            case 2:
                weekDay = 1;
                break;
            case 3:
                weekDay = 2;
                break;
            case 4:
                weekDay = 3;
                break;
            case 5:
                weekDay = 4;
                break;
            case 6:
                weekDay = 5;
                break;
        }
        return weekDay;
    }

    public static Date stringToDate(String string) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
