package com.YHJstyle.b005.j_style_Pro.b005.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;

/**
 * 日历参数
 * */
public class CalendarData {
	private static final String TAG = "CalendarData";
	public static Calendar calStartDate = Calendar.getInstance();
	private Calendar calSelected = Calendar.getInstance();
	private Calendar calToady = Calendar.getInstance();
	private int iFirstDayOfWeek = 2;
	private int MonthViewCurrentMonth = 0;
	private int MonthViewCurrentYear = 0;
	private OnMonthChangeListener mOnMonthChangeListener;
	// 返回的年月
	private String resuYearAndMonth;
	private int resultDay;
	private int resultMonth;
	private int resultYear;

	public CalendarData() {
		calStartDate = getCalendarStartDate();
	}

	// 获取日期的当天日期
	private Calendar getCalendarStartDate() {
		calToady.setTimeInMillis(System.currentTimeMillis());
		calToady.setFirstDayOfWeek(iFirstDayOfWeek);
		if (calSelected.getTimeInMillis() == 0L) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}
		UpdateStartDataForMonth();

		return calStartDate;
	}

	// 获取当前月的最后一天
	public String getCurrentMonthEndDate() {
		return String.valueOf(resultYear) + "."
				+ String.valueOf(resultMonth) + "."
				+ String.valueOf(getMonthTotalDay());
	}

	// 获取当前月的开始一天
	public String getCurrentMonthStartDate() {
		return String.valueOf(resultYear) + "."
				+ String.valueOf(resultMonth) + "."
				+ String.valueOf(Calendar.YEAR);
	}

	public String getCurrentYear() {
		return String.valueOf(resultYear);
	}

	/**
	 * 得到当天的星期
	 * */
	public String getDateWeek(int i) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calStartDate.setTime(simpleDateFormat.parse(calStartDate
					.get(Calendar.YEAR)
					+ "-"
					+ (1 + calStartDate.get(Calendar.MONTH))
					+ "-"
					+ i));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (calStartDate.get(Calendar.DAY_OF_WEEK)) {
		case 1://R.string.date_list_sun
			return getDateString(R.string.date_list_sun);
		case 2://R.string.date_list_mon
			return getDateString(R.string.date_list_mon);
		case 3:
			return getDateString(R.string.date_list_tue);
		case 4:
			return getDateString(R.string.date_list_wed);
		case 5:
			return getDateString(R.string.date_list_thu);
		case 6:

			return getDateString(R.string.date_list_fri);
		case 7:
			return getDateString(R.string.date_list_sat);
		default:
			return "";
		}
	}

	public int getMonthTotalDay() {
		return calStartDate.getActualMaximum(Calendar.DATE);
	}

	public String getCurrentMonth() {
		return String.valueOf(this.resultMonth);
	}

	/**
	 * 跟新日期 随着月份的变化
	 */
	private void UpdateStartDataForMonth() {
		MonthViewCurrentMonth = calStartDate.get(Calendar.MONDAY);
		MonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		// calStartDate.set(field, value);
		calStartDate.set(Calendar.DATE, 1);
		calStartDate.set(Calendar.HOUR_OF_DAY, 0);
		setResult();
	}

	private void setResult() {
		resultYear = calStartDate.get(Calendar.YEAR);
		resultMonth = 1 + calStartDate.get(Calendar.MONDAY);
		resultDay = calStartDate.get(Calendar.DATE);
	}

	/**
	 * 通过R文件中的整形参数得到结果
	 * */
	private String getDateString(int i) {
		return MyApplication.getInstance().getResources().getString(i);
	}

	public String intiDataTab() {
		setResuYearAndMonth();
		return this.resuYearAndMonth;
	}

	// 按一下  向下 按钮

	public String onClickNextButton() {
		MonthViewCurrentMonth = (Calendar.YEAR + MonthViewCurrentMonth);
		if (MonthViewCurrentMonth == 12) {
			MonthViewCurrentMonth = 0;
			MonthViewCurrentYear = (Calendar.YEAR + MonthViewCurrentYear);
		}
		setOnClickWithDate();
		if (mOnMonthChangeListener != null) {
			mOnMonthChangeListener.onMonthChange();
		}
		setResuYearAndMonth();
		return resuYearAndMonth;
	}

	//安一下 向上 按钮
	 public String onClickPreButton()
	  {
	    MonthViewCurrentMonth = (-1 + MonthViewCurrentMonth);
	    if (MonthViewCurrentMonth == -1)
	    {
	      MonthViewCurrentMonth = 11;
	      MonthViewCurrentYear = (-1 + MonthViewCurrentYear);
	    }
	    setOnClickWithDate();
	    if (this.mOnMonthChangeListener != null) {
	      this.mOnMonthChangeListener.onMonthChange();
	    }
	    setResuYearAndMonth();
	    return this.resuYearAndMonth;
	  }
	
	
	
	private void setOnClickWithDate() {

		calStartDate.set(Calendar.DATE, Calendar.YEAR);
		calStartDate.set(Calendar.MONTH, MonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, MonthViewCurrentYear);
		UpdateStartDataForMonth();

	}

	public void setResuYearAndMonth() {
		switch (this.resultMonth) {
		case 1:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_jan)
					+ "  " + this.resultYear);
			break;
		case 2:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_feb)
					+ "  " + this.resultYear);
			break;
		case 3:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_mar)
					+ "  " + this.resultYear);
			break;
		case 4:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_apr)
					+ "  " + this.resultYear);
			break;
		case 5:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_may)
					+ "  " + this.resultYear);
			break;
		case 6:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_jun)
					+ "  " + this.resultYear);
			break;
		case 7:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_jul)
					+ "  " + this.resultYear);
			break;
		case 8:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_aug)
					+ "  " + this.resultYear);
			break;
		case 9:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_sep)
					+ "  " + this.resultYear);
			break;
		case 10:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_oct)
					+ "  " + this.resultYear);
			break;
		case 11:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_nov)
					+ "  " + this.resultYear);
			break;
		case 12:
			this.resuYearAndMonth = (getDateString(R.string.date_tab_month_dec)
					+ "  " + this.resultYear);
			break;
		}
	}

	public static abstract interface OnMonthChangeListener {
		public abstract void onMonthChange();
	}

	public void setOnMonthChangeListener(
			OnMonthChangeListener onMonthChangeListener) {
		this.mOnMonthChangeListener = onMonthChangeListener;
	}
}
