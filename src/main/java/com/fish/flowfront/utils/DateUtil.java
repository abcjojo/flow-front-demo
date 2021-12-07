package com.fish.flowfront.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * @Auther: wp
 * @Date: 2019/4/26 15:13
 * @Description: 时间工具类
 */
public class DateUtil {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATETIME_HOUR = "yyyyMMddHH";
    public static final String FORMAT_DATETIME_MINUTE = "yyyyMMddHHmm";
    private static final long MINUTE = 60 * 1000;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 31 * DAY;
    private static final long YEAR = 12 * MONTH;

    /**
     * 计算时间
     *
     * @param date
     * @return
     */
    public static String formatCreatTime(Date date) {
        if (date == null) {
            return "";
        }
        long offset = System.currentTimeMillis() - date.getTime();
        if (offset > YEAR) {
            return (offset / YEAR) + "年前";
        } else if (offset > MONTH) {
            return (offset / MONTH) + "个月前";
        } else if (offset > WEEK) {
            return (offset / WEEK) + "周前";
        } else if (offset > DAY) {
            return (offset / DAY) + "天前";
        } else if (offset > HOUR) {
            return (offset / HOUR) + "小时前";
        } else if (offset > MINUTE) {
            return (offset / MINUTE) + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    public static String formatDateTime(Date date, String style) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param dateString
     * @param style
     * @return
     */
    public static Date string2Date(String dateString, String style) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        Date date = new Date();
        SimpleDateFormat strToDate = new SimpleDateFormat(style);
        try {
            date = strToDate.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断传入的时间是否在当前时间之后，返回boolean值
     * true: 过期
     * false: 还没过期
     *
     * @param date
     * @return
     */
    public static boolean isExpire(Date date) {
        if (date.before(new Date())) return true;
        return false;
    }

    // 获取 hour 后的时间
    public static Date getHourAfter(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return calendar.getTime();
    }

    // 获取 hour 前的时间
    public static Date getHourBefore(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return calendar.getTime();
    }

    /**
     * 获取 hour 前的时间,并将时间字符串格式化
     * FORMAT_DATETIME_HOUR = "yyyyMMddHH";
     *
     * @param date
     * @param hour
     * @return
     */
    public static String getHourBeforeHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME_HOUR);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取 minute 前的时间,并将时间字符串格式化
     * FORMAT_DATETIME_MINUTE = "yyyyMMddHHmm";
     *
     * @param date
     * @param minute
     * @return
     */
    public static String getHourBeforeMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minute);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME_MINUTE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(calendar.getTime());
    }

    public static Date getDateBefore(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        return calendar.getTime();
    }

    public static Date getDateAfter(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date getMinuteAfter(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getMinuteBefore(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

    /**
     * <p>两日期之间的天数差：date1-date2。</p>
     *
     * @param date1 日期
     * @param date2 日期
     * @return 有符号天数差
     */
    public static int getDaysDiff(Date date1, Date date2) {

        int result = 0;

        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        final int flag = calendar1.compareTo(calendar2);

        if (flag < 0) {
            calendar1.setTime(date2);
            calendar2.setTime(date1);
        }

        result = calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR);

        //不在同一年
        while (calendar1.get(Calendar.YEAR) > calendar2.get(Calendar.YEAR)) {

            result += calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendar2.add(Calendar.YEAR, 1);
        }

        result = flag < 0 ? -result : result;
        return result;
    }


    public static int getDaysDiff(Calendar calendar, Date date) {

        return getDaysDiff(calendar.getTime(), date);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));

    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 时间戳
     * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 num天，可以通过调用以下方法做到这一点：
     * add(Calendar.DAY_OF_MONTH, -num)。
     *
     * @param date 指定时间
     * @param num  为时间添加或减去的时间天数
     * @return
     */
    public static String getBeforeOrAfterDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();//把日历转换为Date
        String dStr = formatDateTime(d);
        String stamp = date2TimeStamp(dStr, FORMAT_DATETIME);
        return stamp;
    }

    /**
     * 时间字符串
     * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 num天，可以通过调用以下方法做到这一点：
     * add(Calendar.DAY_OF_MONTH, -num)。
     *
     * @param date 指定时间
     * @param num  为时间添加或减去的时间天数
     * @return
     */
    public static String getBeforeOrAfterDateStr(Date date, int num) {
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
        calendar.add(Calendar.DATE, -num);
        Date d = calendar.getTime();//把日历转换为Date
        String dStr = formatDateTime(d);
        return dStr;
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static Long getNowTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    //获取上月是哪一月
    public static int getLastMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2);
    }

    /**
     * @Description: 获取上月的开始时间
     * @Return: java.util.Date
     */
    public static Date getStartTimeOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getLastMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * @Description: 获取上月的结束时间
     * @Return: java.util.Date
     */
    public static Date getEndTimeOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getLastMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getLastMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * @Description: 获取今日开始时间
     * @Return: java.util.Date
     */
    public static Date getNowStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return getDayStartTime(todayStart.getTime());
    }

    /**
     * @Description: 获取今日结束时间
     * @Return: java.util.Date
     */
    public static Date getNowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return getDayEndTime(todayEnd.getTime());
    }

    /**
     * @Description: 获取指定日期开始时间
     * @Param: [date]
     * @Return: java.util.Date
     */
    public static Date getAssignStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @Description: 获取指定日期结束时间
     * @Param: [date]
     * @Return: java.util.Date
     */
    public static Date getAssignEndTime(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.add(Calendar.MONTH, 1);
        now.add(Calendar.MILLISECOND, -1);
        return now.getTime();
    }

    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    /**
     * 得到指定日期的一天的的最后时刻23:59:59
     *
     * @param date
     * @return
     */
    public static Date getFinallyDate(Date date) {
        String temp = format.format(date);
        temp += " 23:59:59";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到指定日期的一天的开始时刻00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        String temp = format.format(date);
        temp += " 00:00:00";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // System.out.print(getHourBeforeMinute(new Date(), 1));
        // System.out.println(getBeginDayOfLastMonth());
        // System.out.println(getEndDayOfLastMonth());

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        // Date date = new Date();
        // System.out.println(getAssignStartTime(date));
        //
        // System.out.println(getAssignEndTime(date));
        int i = 0;
        String start = "2020-07-06";
        String end = "2021-07-06";
        System.out.println(getBetweenDate(i, start, end));
    }

    public static List<String> getBetweenDate(int i, String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long distance;
        if (i > 30) {
            distance = ChronoUnit.MONTHS.between(startDate, endDate);
            Stream.iterate(startDate, d -> d.plusMonths(1)).limit(distance + 1).forEach(f -> list.add(f.toString().substring(0, 7)));
        } else {
            distance = ChronoUnit.DAYS.between(startDate, endDate);
            Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        }
        return list;
    }


    // 获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }
    // 获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

}