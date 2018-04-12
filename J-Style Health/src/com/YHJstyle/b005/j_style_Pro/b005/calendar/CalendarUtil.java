package com.YHJstyle.b005.j_style_Pro.b005.calendar;

import java.io.ObjectInputStream.GetField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.SaveDataBase;

import android.text.format.Time;
//import android.util.Log;

public class CalendarUtil {
	public static final String YH_DATE_FORMAT = "yyyy-MM-dd";
	public static final String YH_ID_FORMAT = "yyyyMMdd";
	private int months = 0;
	private int weeks = 0;
	private int year = 0;

	/**
	 * 得到两个数据 相差的内容 1----------------1
	 * */
	// public static ArrayList<String> getBetweenDataString1(String str1,
	// String str2) {
	// GregorianCalendar[] gregorianCalendar1 = null;
	// ArrayList localArrayList = new ArrayList();
	// GregorianCalendar[] gregorianCalendar2 = null;
	// try {
	// gregorianCalendar2 = getBetweenDate(str1, str2);
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// gregorianCalendar1 = gregorianCalendar2;
	// int i;
	// int j;
	//
	// i = gregorianCalendar1.length;
	// j = 0;
	// if (j < i) {
	// GregorianCalendar calendar = gregorianCalendar1[j];
	// localArrayList.add(calendar.get(Calendar.YEAR) + "-" + (1 +
	// calendar.get(Calendar.MONTH))
	// + "-" + calendar.get(Calendar.DATE));
	// j++;
	// }
	// return localArrayList;
	// }

	public static ArrayList<String> getBetweenDataString(String str1,
			String str2) {
		ArrayList list = new ArrayList();
		try {
			GregorianCalendar[] arr = getBetweenDate(str1, str2);
			for (int i = 0; i < arr.length; i++) {
				GregorianCalendar str = arr[i];
				list.add(str.get(GregorianCalendar.YEAR) + "-"
						+ (1 + str.get(GregorianCalendar.MONTH)) + "-"
						+ str.get(GregorianCalendar.DATE));

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 得到两个字符类型之间的日期
	 * */

	public static GregorianCalendar[] getBetweenDate(String str1, String str2)
			throws ParseException {
		Vector vector = new Vector();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gregorianCalendar1 = new GregorianCalendar();
		GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
		gregorianCalendar1.setTime(simpleDateFormat.parse(str1));
		gregorianCalendar2.setTime(simpleDateFormat.parse(str2));
		// if (gregorianCalendar1.after(gregorianCalendar2)) {
		// // 数据加入到vector中
		// vector.add(gregorianCalendar1.clone());
		// // 得到的天数+1；
		// gregorianCalendar1.add(Calendar.DATE, 1);
		// }
		do {
			// 数据加入到vector中
			vector.add(gregorianCalendar1.clone());
			// 得到的天数+1；
			gregorianCalendar1.add(Calendar.DATE, 1);

		} while (!(gregorianCalendar1.after(gregorianCalendar2)));
//		Log.i("APP", "vector.size" + vector.size());
		return (GregorianCalendar[]) vector
				.toArray(new GregorianCalendar[vector.size()]);
	}

	/**
	 * 得到前后的时间
	 * */
	public static Date getDateBeforeOrAfter(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, i * -1);
		return calendar.getTime();
	}

	/**
	 * 得到星期一 加
	 * */
	private int getMondayPlus() {
		int i = -1 + Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return 0;
		}
		return 1 - i;
	}

	// 得到现在的是时间
	public static String getNowTime(String str) {
		Date date = new Date();
		return new SimpleDateFormat(str).format(date);
	}

	/**
	 * 得到计步器的数据
	 * */
	public static ArrayList<PedoMeter> getPedometerDate(
			ArrayList<String> arrayList) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < arrayList.size(); i++) {
			String[] str1 = ((String) arrayList.get(i)).split("-");
//			PedoMeter pMeter = SaveDataBase.getPeodeterInfo(MyApplication.getInstance(), Integer.valueOf(str1[0]).intValue(),
//					Integer.valueOf(str1[1]).intValue(),
//					Integer.valueOf(str1[2]).intValue());
			list.add(SaveDataBase.getPeodeterInfo(MyApplication.getInstance(),
					Integer.valueOf(str1[0]).intValue(),
					Integer.valueOf(str1[1]).intValue(),
					Integer.valueOf(str1[2]).intValue()));
		}
		return list;
	}

	/**
	 * 得到后面制定的时间
	 * */
	public static String getSpecifiedDayAfter(String str) {
		Date date1 = null;
		Calendar calendar = Calendar.getInstance();
		try {
			Date date2 = new SimpleDateFormat("yy-MM-dd").parse(str);
			date1 = date2;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		calendar.setTime(date1);
		calendar.set(Calendar.DATE, 1 + calendar.get(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

	}

	/**
	 * 得到前一天时间
	 * */
	public static String getSpecifiedDayBefore(String str) {
		Date date1 = null;
		Calendar calendar = Calendar.getInstance();
		try {
			Date date2 = new SimpleDateFormat("yy-MM-dd").parse(str);
			date1 = date2;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		calendar.setTime(date1);
		calendar.set(Calendar.DATE, -1 + calendar.get(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

	}

	/**
	 * 得到下一个时间
	 * */
	public static ArrayList<Integer> getTheDayAfter(int param1, int param2,
			int param3) {
		ArrayList list = new ArrayList();
		String[] arrayStr = getSpecifiedDayAfter(
				String.valueOf(param1) + "-" + String.valueOf(param2) + "-"
						+ String.valueOf(param3)).split("-");
		list.add(Integer.valueOf(arrayStr[0]));
		list.add(Integer.valueOf(arrayStr[1]));
		list.add(Integer.valueOf(arrayStr[2]));

		return list;
	}

	public static ArrayList<Integer> getTheDayBefore(int param1, int param2,
			int param3) {
		ArrayList list = new ArrayList();
		String[] arrayStr = getSpecifiedDayBefore(
				String.valueOf(param1) + "-" + String.valueOf(param2) + "-"
						+ String.valueOf(param3)).split("-");
		list.add(Integer.valueOf(arrayStr[0]));
		list.add(Integer.valueOf(arrayStr[1]));
		list.add(Integer.valueOf(arrayStr[2]));

		return list;
	}

	/**
	 * 得到现在的时间 安卓特有类 Time
	 * */
	public static int[] getTime() {
		Time time = new Time();
		time.setToNow();
		int[] localInt = new int[6];
		localInt[0] = (-2000 + time.year);
		localInt[1] = (1 + time.month);
		localInt[2] = time.monthDay;
		localInt[3] = time.hour;
		localInt[4] = time.minute;
		localInt[5] = time.second;
		return localInt;
	}

	/**
	 * 得到目标的总的数据
	 * */
	public static int[] getTotalData(ArrayList<PedoMeter> paramList) {
		int[] arrayInt = new int[8];
		for (int i = 0; i < paramList.size(); i++) {
			arrayInt[0] += ((PedoMeter) paramList.get(i)).activityTtime;
			arrayInt[1] += ((PedoMeter) paramList.get(i)).steps;
			arrayInt[2] += ((PedoMeter) paramList.get(i)).calories;
			arrayInt[3] += ((PedoMeter) paramList.get(i)).fuel;
			arrayInt[4] += ((PedoMeter) paramList.get(i)).distance;
			arrayInt[5] += ((PedoMeter) paramList.get(i)).sleepTime;
			arrayInt[6] += ((PedoMeter) paramList.get(i)).dailyGoal;
		}
		return arrayInt;
	}

	/**
	 * 得到某年的数据
	 * */
	public static PedoMeter getTotalYearData(ArrayList<PedoMeter> paramArrayList) {
		PedoMeter localPedoMeter = new PedoMeter();
		int[] arrayOfInt = getTotalData(paramArrayList);
		if (arrayOfInt != null) {
			for (int i = 0; i < 6; i++) {
				localPedoMeter.activityTtime = arrayOfInt[0];
				localPedoMeter.steps = arrayOfInt[1];
				localPedoMeter.calories = arrayOfInt[2];
				localPedoMeter.fuel = arrayOfInt[3];
				localPedoMeter.distance = arrayOfInt[4];
				localPedoMeter.sleepTime = arrayOfInt[5];
			}

		}
		return localPedoMeter;
	}

	/**
	 * 得到两天间隔
	 * */
	public static String getTwoDay(String str1, String str2) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date1 = simpleDateFormat.parse(str1);
			Date date2 = simpleDateFormat.parse(str2);
			long l = (date1.getTime() - date2.getTime()) / 86400000L;
			return l + "";
		} catch (Exception e) {
			return "";
		}

	}

	/*
	 * 得到格式化的日期时间
	 */
	public static String getFormatDateTime(Date paramDate, String paramString) {

		if (paramDate != null) {
			SimpleDateFormat ss = new SimpleDateFormat(paramString);
			String str1 = ss.format(paramDate);
			return str1;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String str2 = sdf.format(paramDate);
			return "";
		}

	}

	/**
	 * 获取年份的第一天
	 * */
	private int getYearPlus() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		int j = calendar.get(Calendar.DAY_OF_YEAR);
		if (i == 1) {
			return -j;
		}
		return 1 - i;
	}

	/**
	 * 保存某天的数据
	 * */

	public static void setSavaDateAndId() {
		String[] arrayStr = getFormatDateTime(
				getDateBeforeOrAfter(MyApplication.saveDataTimes), "yyyy-MM-dd")
				.split("-");
//		Log.i("APP", Arrays.toString(arrayStr));
		MyApplication.getInstance().mPedoMeter.year = Integer.valueOf(
				arrayStr[0]).intValue();
		MyApplication.getInstance().mPedoMeter.month = Integer.valueOf(
				arrayStr[1]).intValue();
		MyApplication.getInstance().mPedoMeter.day = Integer.valueOf(
				arrayStr[2]).intValue();
	}

	/**
	 * 分来年月日 ----
	 * */

	public static int[] splitNowTime(String str) {
		int[] arrayInt = new int[3];
		String[] arrayString = str.split("-");
		for (int i = 0; i < arrayString.length; i++) {
			arrayInt[i] = Integer.valueOf(arrayString[i]).intValue();
		}
		return arrayInt;

	}

	/**
	 * 得到正确的星期
	 * */
	public String getCurrentWeekday() {
		this.weeks = 0;
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(Calendar.DATE, i + 6);
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	/**
	 * 得到一年的最后一天
	 */

	public String getCurrentYearEnd() {
		Date localDate = new Date();
		return new SimpleDateFormat("yyyy").format(localDate) + "-12-31";

	}

	/**
	 * 得到一年的第一天
	 * */
	public String getCurrentYearFirst() {
		int i = getYearPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(Calendar.DATE, i);
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	/**
	 * 得到默认的年月日 ?
	 * */
	public String getDefaultDay() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(Calendar.DATE, 1);
		localCalendar.add(Calendar.MONTH, 1);
		localCalendar.add(Calendar.DATE, -1);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	/**
	 * 一个月的第一天
	 * */

	public String getFirstDayOfMonth() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	/**
	 * 得到一个星期的星期一
	 * */

	public String getMondayOFWeek() {
		this.weeks = 0;
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(Calendar.DATE, i);
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	public String getNextMonday() {
		this.weeks = (1 + this.weeks);
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(Calendar.DATE, i + 7 * this.weeks);
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	public String getNextMonthEnd() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.add(Calendar.MONTH, this.months);
		localCalendar.set(Calendar.DAY_OF_MONTH, 1);
		localCalendar.roll(Calendar.DAY_OF_MONTH, -1);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	public String getNextMonthFirst() {
		this.months = (1 + this.months);
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.add(Calendar.MONTH, this.months);
		localCalendar.set(Calendar.DATE, 1);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	public String getNextSunday() {
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(5, 6 + (i + 7 * this.weeks));
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	public String getNextYearEnd() {
		Date localDate = new Date();
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(localDate))
				+ year + "-12-31";
	}

	public String getNextYearFirst() {
		this.year = (1 + this.year);
		Date localDate = new Date();
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(localDate))
				+ year + "-1-1";
	}

	public String getPreviousMonthEnd() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.add(Calendar.MONTH, this.months);
		localCalendar.set(5, 1);
		localCalendar.roll(5, -1);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	public String getPreviousMonthFirst() {
		this.months = (-1 + this.months);
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		localCalendar.add(Calendar.MONTH, this.months);
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	public String getPreviousWeekSunday() {
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(5, 6 + (i + 7 * this.weeks));
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	public String getPreviousWeekday() {
		this.weeks = (-1 + this.weeks);
		int i = getMondayPlus();
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		localGregorianCalendar.add(5, i + 7 * this.weeks);
		Date localDate = localGregorianCalendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
	}

	public String getPreviousYearEnd() {
		Date localDate = new Date();
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(localDate))
				+ this.year + "-12-31";
	}

	public String getPreviousYearFirst() {
		this.year = (-1 + this.year);
		Date localDate = new Date();
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(localDate))
				+ this.year + "-1-1";
	}
}
