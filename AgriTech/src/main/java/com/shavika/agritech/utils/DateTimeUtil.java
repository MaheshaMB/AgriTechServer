package com.shavika.agritech.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil {

	public static final String DATEFORMAT_DD_HYP_MM_HYP_YYYY = "dd-MM-yyyy";

	public static final String DATEFORMAT_DD_DOT_MM_DOT_YYYY = "dd.MM.yyyy";

	public static final String DATEFORMAT_DD_SL_MM_SL_YYYY = "dd/MM/yyyy";

	public static final String DATEFORMAT_DD_SL_MM_SL_YYYY_SP_HH_CO_MM_CO_SS = "dd/MM/yyyy HH:mm:ss";

	public static final String TIMEFORMAT_HH_CO_MM_CO_SS = "HH:mm:ss";

	public static final String DATEFORMAT_MMM_SPC_DD_CMA_SPC_YYYY = "MMM dd, yyyy";

	public static String defaultDateTime() {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATEFORMAT_DD_SL_MM_SL_YYYY_SP_HH_CO_MM_CO_SS);
		return fmt.print(dt).toString();
	}

	public static String dateWithFormat(String format) {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		return fmt.print(dt).toString();
	}

	public static java.util.Date dateWithFormat(String strdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date = formatter.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static DateTime getDateTime(long mills) {
		return new DateTime(mills);
	}

	public static long getMillis() {
		return new DateTime().getMillis();
	}

	public static java.sql.Date sqlDate() {
		return new java.sql.Date(getMillis());
	}

	public static java.sql.Date sqlDate(long mill) {
		return new java.sql.Date(mill);
	}

}
