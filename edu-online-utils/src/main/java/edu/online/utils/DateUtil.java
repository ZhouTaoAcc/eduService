package edu.online.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * @Classname DateUtil
 * @Description TODO 时间转换工具 mongo使用的是ISOdate UTC时间 比CST中国标准时间慢8个小时
 * @Date 2020/2/12 12:21
 * @Created by zhoutao
 */
public class DateUtil {
    /**
     * TODO Date类型  中国标准时间CST转UTC
     * @param date
     * @return
     */
    public static Date dateToISODate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO 字符串类型 中国标准时间CST转UTC
     * @param dateStr
     * @return
     */
    public static Date dateStrToISODate(String dateStr){
        //T代表后面跟着时间，Z代表UTC统一时间
        Date date = formatD(dateStr);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO 时间格式(yyyy-MM-dd HH:mm:ss) 字符串转Date类型
     * @param dateStr
     * @return
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static Date formatD(String dateStr){
        return formatD(dateStr,DATE_TIME_PATTERN);
    }

    public static Date formatD(String dateStr ,String format)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date ret = null ;
        try {
            ret = simpleDateFormat.parse(dateStr) ;
        } catch (ParseException e) {
            //
        }
        return ret;
    }
}
