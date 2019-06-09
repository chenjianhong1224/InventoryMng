package com.cjh.InventoryMng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {

	public static String getCurOrderDate() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
		Date now = new Date();
		String nowDate = "";
		if (sdf2.format(now).compareTo("150000") >= 0) {
			nowDate = sdf1.format(new Date(now.getTime() + 24 * 60 * 60 * 1000));
		} else {
			nowDate = sdf1.format(now);
		}
		return nowDate;
	}

	public static Date truncDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date returnDate = date;
		try {
			returnDate = df.parse(df.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDate;
	}

	public static Date getTheIntervalDay(Date date, long interval) {
		if (date == null) {
			return null;
		}
		Date returnDate = new Date(date.getTime() + interval * 24 * 60 * 60 * 1000L);
		return returnDate;
	}

	public static Date getTheFormatDay(String dateStr, String pattern) throws ParseException {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		Date returnDate = df.parse(dateStr);
		return returnDate;
	}

	public static String getTheDayStr(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		String str = df.format(date);
		return str;
	}

	/**
	 * 
	 * @param date
	 * @param days
	 *            1 = tomorrow
	 */
	public static LocalDate getNextWorkDay(LocalDate date, int days) {
		assert days >= 0;
		int weeks = days / 5;
		int remainDays = days % 5;
		LocalDate plusWeeks = date.plusWeeks(weeks);
		int dayOfWeek = plusWeeks.getDayOfWeek().getValue();
		if (dayOfWeek - 6 >= 0) { // on weekend, set to next Monday
			plusWeeks = plusWeeks.plusDays(8 - dayOfWeek);
		}
		if (dayOfWeek < 6 - remainDays) {
			return plusWeeks.plusDays(remainDays);
		} else {// go to next week
			return plusWeeks.plusDays(remainDays + 2);
		}
	}

	public static Date localDate2Date(LocalDate dt) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = dt.atStartOfDay().atZone(zone).toInstant();
		java.util.Date date = Date.from(instant);
		return date;
	}

	public static String dateToWeek(String date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(date);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	static public String[] theNearWeek(String date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(date);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		int benginIndex = 0 - w - 6;
		Date beginDate = getTheIntervalDay(datet, benginIndex);
		String[] returnStr = new String[7];
		for (int i = 0; i < 7; i++) {
			returnStr[i] = f.format(getTheIntervalDay(beginDate, i));
		}
		return returnStr;
	}
}
