package com.smartlab.componentbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author：xxl
 * @Created in：2019/6/6
 */
public class TimeHelper {
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp1(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (s == null || s == "")
                return "";

            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        }catch (NumberFormatException e){
            e.printStackTrace();
            res = s;
        }
        return res.replace("00:00:00","");
    }

    /**
     * 获取当前时间
     * */
    public static String getTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
