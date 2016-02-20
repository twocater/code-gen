package com.xunlei.game.velocity.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 缺省格式
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将时间转换为字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date, String format) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date.getTime());
		}
		return null;
	}

	/**
	 * 将时间按缺省格式转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return toString(date, DEFAULT_FORMAT);
	}

	/**
	 * 将日历转换为字符串
	 * 
	 * @param calendar
	 * @param format
	 * @return
	 */
	public static String toString(Calendar calendar, String format) {
		if (calendar != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(calendar.getTime());
		}
		return null;
	}

	/**
	 * 将日历按缺省格式转换为字符串
	 * 
	 * @param calendar
	 * @return
	 */
	public static String toString(Calendar calendar) {
		return toString(calendar, DEFAULT_FORMAT);
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String currentString(String format) {
		Calendar calendar = Calendar.getInstance();
		return toString(calendar, format);
	}

	/**
	 * 按缺省格式获取当前时间字符串
	 * 
	 * @return
	 */
	public static String currentString() {
		return currentString(DEFAULT_FORMAT);
	}

	public static String previewString() {
		return previewString(DEFAULT_FORMAT);
	}

	public static String previewString(String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return toString(calendar, format);
	}

	public static String nextString() {
		return nextString(DEFAULT_FORMAT);
	}

	public static String nextString(String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return toString(calendar, format);
	}

	/**
	 * 将字符串转换为时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date toDate(String date, String format) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(date);
			} catch (ParseException e) {
				throw new IllegalStateException("format error.", e.getCause());
			}
		}
		return null;
	}

	/**
	 * 将字符串按缺省格式转换为时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date toDate(String date) {
		return toDate(date, DEFAULT_FORMAT);
	}

	/**
	 * 将字符串转换为日历
	 * 
	 * @param calendar
	 * @param format
	 * @return
	 */
	public static Calendar toCalendar(String calendar, String format) {
		if (calendar != null) {
			Date date = toDate(calendar, format);
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			return instance;
		}
		return null;
	}

	/**
	 * 将字符串按缺省格式转换为日历
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar toCalendar(String calendar) {
		return toCalendar(calendar, DEFAULT_FORMAT);
	}

	/**
	 * 下一天
	 * 
	 * @param day
	 * @param format
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String nextDay(String day, String format) {
		if (day != null) {
			Date date = toDate(day, format);
			date.setDate(date.getDate() + 1);
			return toString(date, format);
		}
		return null;
	}

	/**
	 * 下一天
	 * 
	 * @param format
	 * @return
	 */
	public static String nextDay(String day) {
		return nextDay(day, DEFAULT_FORMAT);
	}

	/**
	 * date是否为昨天 FORMAT="yyyy-MM-dd HH:mm:ss";
	 * 
	 * @param format
	 * @return
	 */
	public static boolean isYesterday(String date) {
		String preview = previewString();
		return preview.substring(0, 10).equals(date.substring(0, 10));
	}

	public static boolean isToday(String date) {
		String current = DateUtil.currentString();
		return current.substring(0, 10).equals(date.substring(0, 10));
	}

	public static void main(String[] args) {
		System.out.println(isYesterday("2012-02-18 00:11:20"));
		System.out.println(isToday("2012-02-18 00:11:20"));
		System.out.println(isYesterday("2012-02-17 00:11:20"));
		System.out.println(isToday("2012-02-17 00:11:20"));
		System.out.println(isYesterday("2012-02-19 00:11:20"));
		System.out.println(isToday("2012-02-19 00:11:20"));
	}
}
