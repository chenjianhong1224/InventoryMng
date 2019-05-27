package com.cjh.InventoryMng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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

	// public static void main(String[] args) {
	// LocalDate dd = LocalDate.now();
	// for(int i = 0 ; i < 8 ; i++){
	// LocalDate plusDays = dd.plusDays(i);
	// System.out.println(plusDays);
	// System.out.println(plusDays.getDayOfWeek().getValue());
	// }
	// }
}
