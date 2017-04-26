package com.sunesoft.lemon.fr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具。
 */
public class DateHelper {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取当前日期凌晨时间
	 * 
	 * @param date
	 *            当前日期
	 * @return 当前日期凌晨时间
	 */
	public static Date getBeginDateTime(Date date) {
		Date currentDateTime = null;

		try {
			currentDateTime = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return currentDateTime;
	}

	/**
	 * 获取次日凌晨时间
	 * 
	 * @param date
	 *            当前日期
	 * @return 次日凌晨时间
	 */
	public static Date getEndDateTime(Date date) {
		Date currentDateTime = null;

		try {
			long time = sdf.parse(sdf.format(date)).getTime() + 3600 * 24 * 1000;
			currentDateTime = new Date(time); // //获取当前日期凌晨时间
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return currentDateTime;
	}

	/**
	 * 格式化日期。
	 * 
	 * @param date
	 *            日期。
	 * @param pattern
	 *            格式化字符串。
	 * @return 格式化后的日期字符串。
	 */
	public static String formatDate(Date date, String pattern) {
        if(date==null) return null;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

    /**
     * xzl write
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if(date==null) return null;
        String pattern="yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
	public static String formatDate2(Date date) {
		if(date==null) return null;
		String pattern="yyyy";
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	/**
	 * 将String类型转换为日期格式。
	 * 
	 * @param dateStr
	 *            日期字符串。
	 * @param pattern
	 *            格式化类型。
	 * @return 日期。
	 */
	public static Date parse(String dateStr, String pattern) {
//        if(dateStr==null) return null;
		SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateFormat.applyPattern(pattern);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

    /**
     * 比较年月日相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2) {
        if(null == d1 || null == d2)
            return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        return  cal1.getTime().equals(cal2.getTime());
    }


    /**
     * 将String类型转换为日期格式。
     *
     * @param dateStr
     *            日期字符串。
      * @return 日期。
     */
    public static Date parse(String dateStr) {
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
			Date datess=dateFormat.parse(dateStr);
            return datess;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date setDay(Integer day){
        Calendar c =Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

	public static Date parse2(String dateStr) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateFormat.applyPattern("yyyy-MM-dd");
		try {
			Date datess=dateFormat.parse(dateStr);
			return datess;
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parse3(String dateStr) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
		dateFormat.applyPattern("yyyy");
		try {
			Date datess=dateFormat.parse(dateStr);
			return datess;
		} catch (ParseException e) {
			return null;
		}
	}
    /**
	 * 获取日期。
	 * 
	 * @return 日期。
	 */
	public static Date getDate() {
		return new Date();
	}

    /**
     * 计算两个日期之间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1,Date date2)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
	 * 获取年。
	 * 
	 * @param date
	 *            日期。
	 * @return 年。
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月。
	 * 
	 * @param date
	 *            日期。
	 * @return 月。
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.MONTH)+1;
	}

	/**
	 * 获取天。
	 * 
	 * @param date
	 *            日期。
	 * @return 天。
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DATE);
	}
    /**
     * 获取周几。
     *
     * @param date
     *            日期。
     * @return 天。
     */
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断是否是闰年
     * @param year
     * @return
     */
    public static Boolean isLeapYear(Integer year){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,Calendar.PM,31);
        if(calendar.get(Calendar.DAY_OF_YEAR)==366){
            return true;
        }else{
            return false;
        }
    }

	/**
	 * 获取小时。
	 * 
	 * @param date
	 *            日期。
	 * @return 小时。
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.HOUR);
	}

	/**
	 * 获取分钟。
	 * 
	 * @param date
	 *            日期。
	 * @return 分钟。
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒。
	 * 
	 * @param date
	 *            日期。
	 * @return 秒。
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 加年。
	 * 
	 * @param date
	 *            日期。
	 * @param year
	 *            年。
	 * @return 日期。
	 */
	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);

		return calendar.getTime();
	}

	/**
	 * 加月。
	 * 
	 * @param date
	 *            日期。
	 * @param month
	 *            月。
	 * @return 日期。
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);

		return calendar.getTime();
	}

	/**
	 * 添加日。
	 * 
	 * @param date
	 *            日期。
	 * @param day
	 *            日。
	 * @return 日期。
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);

		return calendar.getTime();
	}

	/**
	 * 添加小时。
	 * 
	 * @param date
	 *            日期。
	 * @param hour
	 *            小时。
	 * @return 日期。
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);

		return calendar.getTime();
	}

	/**
	 * 添加分钟。
	 * 
	 * @param date
	 *            日期。
	 * @param minute
	 *            分钟。
	 * @return 日期。
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);

		return calendar.getTime();
	}

	/**
	 * 添加秒。
	 * 
	 * @param date
	 *            日期。
	 * @param second
	 *            秒。
	 * @return 日期。
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);

		return calendar.getTime();
	}
}
