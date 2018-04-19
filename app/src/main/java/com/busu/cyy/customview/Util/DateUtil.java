package com.busu.cyy.customview.Util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Cyy513 on 2018/4/17.
 */

public class DateUtil {

    //获取当前月份
    public static int getCurrentMonth()
    {
        Calendar calendar=Calendar.getInstance();
        //获得当前时间的月份，月份从0开始所以结果要加1
        int month=calendar.get(Calendar.MONTH)+1;
        return month;
    }

    /**
     * 通过指定的年份和月份获取当月有多少天.
     *
     * @param year  年.
     * @param month 月.
     * @return 天数.
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return -1;
        }
    }

    //根据年月获取本月第一天
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    //获取当前是哪年
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    //获取今天几号

    public static int getSysDay()
    {
        Calendar date = Calendar.getInstance();
        String day = String.valueOf(date.get(Calendar.DATE));
        return Integer.parseInt(day);
    }

}
