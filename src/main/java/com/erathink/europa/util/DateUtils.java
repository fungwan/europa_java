package com.erathink.europa.util;

/**
 * Created by fengyun on 2017/12/17.
 */
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final ZoneId ZONE = ZoneId.systemDefault();

    public static BigInteger now() {
        return BigInteger.valueOf(Date.from(Instant.now()).getTime());
    }

    /**
     * 获取年初的时间戳，
     * 例：如果求当前年份年初的时间戳，则before=0，
     * 如果获取去年年初的时间戳，则before=1
     * @param before 从当前年份到目标年份的差
     * @return
     */
    public static BigInteger getYearInitial(int before) {
        int year = Calendar.getInstance().get(Calendar.YEAR)-before;
        Calendar beginYearTime = Calendar.getInstance();
        beginYearTime.set(year,0, 0,0,0,0);
        return BigInteger.valueOf(Date.from(beginYearTime.getTime().toInstant()).getTime());
    }
    /**
     * 获取若干年前的对应时刻的时间戳
     * @param Time 指定时间
     * @param before 时间回退的年份
     * @return
     */
    public static BigInteger getTimeStamp(BigInteger Time,int before) {
        Calendar oldTime = Calendar.getInstance();
        oldTime.setTimeInMillis(Time.longValue());
        oldTime.add(Calendar.YEAR, -before);
        return BigInteger.valueOf(Date.from(oldTime.getTime().toInstant()).getTime());
    }
    /**
     * 获取指定时间前若干月的对应时刻的时间戳
     * @param Time 指定的当前时间
     * @param before 回退的月份
     * @return
     */
    public static BigInteger getLastMonthTimeStamp(BigInteger Time,int before) {
        Calendar oldTime = Calendar.getInstance();
        oldTime.setTimeInMillis(Time.longValue());
        oldTime.add(Calendar.MONTH, -before);
        return BigInteger.valueOf(Date.from(oldTime.getTime().toInstant()).getTime());
    }

    public static LocalDateTime dateToLocalDateTime(Date d) {
        return LocalDateTime.ofInstant(d.toInstant(), ZONE);
    }

    public static LocalDate dateToLocalDate(Date d) {
        return dateToLocalDateTime(d).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date d) {
        return dateToLocalDateTime(d).toLocalTime();
    }

    public static Instant dateToInstant(Date d) {
        return d.toInstant();
    }

    public static Date localDateTimeToDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZONE).toInstant());
    }

    public static Date localDateToDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay().atZone(ZONE).toInstant());
    }

    public static Date localTimeToDate(LocalTime lt) {
        return Date.from(LocalDateTime.of(LocalDate.now(), lt).atZone(ZONE).toInstant());
    }

    public static String format(Date d) {
        return format(d, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static String format(Date d, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return format(d, formatter);
    }

    public static String format(Date d, DateTimeFormatter formatter) {
        return dateToLocalDateTime(d).format(formatter);
    }

    public static Date parse(String date, String pattern) {
        if (null == pattern || 0 == pattern.length()) {
            pattern = "uuuuMMdd[ [HH][:mm][:ss][.SSS]]";
        }
        DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern(pattern)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();
        return parse(date, DATE_FORMAT);
    }

    public static Date parse(String date) {
        return parse(date, "");
    }

    public static Date parse(String date, DateTimeFormatter formatter) {
        return localDateTimeToDate(LocalDateTime.parse(date, formatter));
    }

    public static Date plus(Date d, int value, ChronoUnit unit) {
        switch (unit) {
            case MONTHS:
                return localDateToDate(dateToLocalDate(d).plusMonths(value));
            case YEARS:
                return localDateToDate(dateToLocalDate(d).plusYears(value));
            case WEEKS:
                return localDateToDate(dateToLocalDate(d).plusWeeks(value));
            default:
                break;
        }
        return Date.from(d.toInstant().plus(value, unit));
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat
     *             yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 获取年份的后两位 用于生成流水码
     * @author ylchen@erathink.com
     * @return
     */
    public static String getYearLast() {
        return new SimpleDateFormat("yy").format(DateUtils.now());
    }
}

