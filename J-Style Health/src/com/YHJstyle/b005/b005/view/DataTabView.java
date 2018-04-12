package com.YHJstyle.b005.b005.view;

import java.util.ArrayList;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.jump.ProgressActivity;
import com.YHJstyle.b005.view.b005.layout.ShowDataView;

import android.content.Context;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DateSorter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 数据表现布局
 * */

public class DataTabView extends LinearLayout implements OnClickListener {
	
	public static final String TAG = "DataTabView";
	private static final int TYPE_YEAR_MONTH = 0;
	private static final int TYPE_YEAR_MONTH_DAY = 1;
	private int dataGoald;
	private int dateStyel;
	private String endStr = "";
	private RelativeLayout layout;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout4;
	private LinearLayout layout5;
	private CalendarUtil mCalendar;
	private CalendarData mCalendarData;
	private Context mContext;
	private TextView mDateTextView;
	private TextView mEndDate;
	private ArrayList<PedoMeter> mPedoMeter = new ArrayList();
	private RoundView mRoundView;
	private ShowDataView mShowDataView;
	private TextView mStarDate;
	private int mType;
	private String starStr = "";

	public DataTabView(Context context, CalendarData calendarData, int defStyle) {
		super(context);
		this.mContext = context;
		this.mType = defStyle;
		this.mCalendarData = calendarData;
		initView();
	}

	private void initView() {
		mCalendar = new CalendarUtil();

		switch (mType) {
		case TYPE_YEAR_MONTH:
			View.inflate(this.mContext, R.layout.year_month_type_item, this);
			mDateTextView = (TextView) findViewById(R.id.year_and_month_tv);
			mDateTextView.setText(mCalendarData.intiDataTab());
			break;

		case TYPE_YEAR_MONTH_DAY:
			View.inflate(this.mContext, R.layout.year_month_day_type_item, this);
			setLayoutParams(new LinearLayout.LayoutParams(MyApplication
					.getInstance().getScreenWidth(),
					LinearLayout.LayoutParams.WRAP_CONTENT));
			mStarDate = (TextView) findViewById(R.id.year_month_day_star);
			mEndDate = (TextView) findViewById(R.id.year_month_day_end);
			mStarDate.setText(mCalendar.getMondayOFWeek());
			mEndDate.setText(mCalendar.getCurrentWeekday());
			break;
		}
		setButtonListener();

	}

	private void setButtonListener() {
		((Button) findViewById(R.id.reduce_bt)).setOnClickListener(this);
		((Button) findViewById(R.id.add_bt)).setOnClickListener(this);
	}

	public DataTabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ?????
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		 case R.id.year_month_day_star:
		 onClickProgressPreBt();
		 break;
		 case R.id.year_month_day_end:
		 onClickProgressNextBt();
		 break;
		case R.id.reduce_bt:// 减
			if (MyApplication.isYearBtn) {
				MyApplication.isYearBtn_add = true;
			}
			if (this.mType == TYPE_YEAR_MONTH) {
				mDateTextView.setText(mCalendarData.onClickPreButton());
				return;
			}
			onClickProgressNextBt();
			break;
		case R.id.add_bt:
			if (MyApplication.isYearBtn) {
				MyApplication.isYearBtn_add = false;
			}
			if (this.mType == TYPE_YEAR_MONTH) {
				mDateTextView.setText(mCalendarData.onClickNextButton());
				return;
			}
			onClickProgressPreBt();

			break;
		}
	}

	private void onClickProgressNextBt() {
		switch (ProgressActivity.dateType) {
		case ProgressActivity.TYPE_WEEK:
			dateStyel = 0;
			starStr = mCalendar.getPreviousWeekday();
			endStr = mCalendar.getPreviousWeekSunday();
			break;
		case ProgressActivity.TYPE_MONTH:
			dateStyel = 1;
			starStr = mCalendar.getPreviousMonthFirst();
			endStr = mCalendar.getPreviousMonthEnd();
			break;
		case ProgressActivity.TYPE_YEAR:
			dateStyel = 2;
			starStr = mCalendar.getPreviousYearFirst();
			endStr = mCalendar.getPreviousYearEnd();
			break;
		default:
			break;
		}
		onDateTabButton();
	}

	private void onDateTabButton() {
		mStarDate.setText(starStr);
		mEndDate.setText(endStr);
		mPedoMeter = CalendarUtil.getPedometerDate(CalendarUtil
				.getBetweenDataString(starStr, endStr));
//		Log.i("APP", "DataTabView  mPedoMeter"+ mPedoMeter.size());
		SetGoald();
		mShowDataView.showData(CalendarUtil.getTotalData(mPedoMeter));
		upCharView();
	}
	
	/**
	 * 单机变化曲线图
	 * */
	private void upCharView() {
		ProgressActivity progressActivity = new ProgressActivity();
		String[] arr = new String[7];
		arr[0] = getResources().getString(R.string.date_list_mon);
		arr[1] = getResources().getString(R.string.date_list_tue);
		arr[2] = getResources().getString(R.string.date_list_wed);
		arr[3] = getResources().getString(R.string.date_list_thu);
		arr[4] = getResources().getString(R.string.date_list_fri);
		arr[5] = getResources().getString(R.string.date_list_sat);
		arr[6] = getResources().getString(R.string.date_list_sun);

		progressActivity.xWeekLable = arr;
		String[] arr1 = new String[12];
		arr1[0] = getResources().getString(R.string.date_tab_month_jan);
		arr1[1] = getResources().getString(R.string.date_tab_month_feb);
		arr1[2] = getResources().getString(R.string.date_tab_month_mar);
		arr1[3] = getResources().getString(R.string.date_tab_month_apr);
		arr1[4] = getResources().getString(R.string.date_tab_month_may);
		arr1[5] = getResources().getString(R.string.date_tab_month_jun);
		arr1[6] = getResources().getString(R.string.date_tab_month_jul);
		arr1[7] = getResources().getString(R.string.date_tab_month_aug);
		arr1[8] = getResources().getString(R.string.date_tab_month_sep);
		arr1[9] = getResources().getString(R.string.date_tab_month_oct);
		arr1[10] = getResources().getString(R.string.date_tab_month_nov);
		arr1[11] = getResources().getString(R.string.date_tab_month_dec);
		progressActivity.xYearLable = arr1;
		layout.removeView(mRoundView);
		layout1.removeAllViews();
		layout2.removeAllViews();
		layout3.removeAllViews();
		layout4.removeAllViews();
		layout5.removeAllViews();
		this.dateStyel = ProgressActivity.getDateType();
		layout1.addView(new ProgressCharView(progressActivity.xWeekLable,
				progressActivity.xYearLable).execute(this.mContext,
				this.dateStyel, 0, "steps", this.mPedoMeter));
		layout2.addView(new ProgressCharView(progressActivity.xWeekLable,
				progressActivity.xYearLable).execute(this.mContext,
				this.dateStyel, 1, "time", this.mPedoMeter));
		layout3.addView(new ProgressCharView(progressActivity.xWeekLable,
				progressActivity.xYearLable).execute(this.mContext,
				this.dateStyel, 2, "distance", this.mPedoMeter));
		layout4.addView(new ProgressCharView(progressActivity.xWeekLable,
				progressActivity.xYearLable).execute(this.mContext,
				this.dateStyel, 3, "calories", this.mPedoMeter));
		layout5.addView(new ProgressCharView(progressActivity.xWeekLable,
				progressActivity.xYearLable).execute(this.mContext,
				this.dateStyel, 4, "sleep", this.mPedoMeter));
		this.mRoundView = new HomeRoundView(this.mContext, null, this.layout,
				0, this.dataGoald);
		layout.addView(this.mRoundView);
		layout1.invalidate();
		layout2.invalidate();
		layout3.invalidate();
		layout4.invalidate();
		layout5.invalidate();
		layout.invalidate();

	}

	private void SetGoald() {
		switch (this.dateStyel) {
		case 0:// 0周
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 7;
			
			break;
		case 1:// 1月
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 30;
			break;

		case 2:// 日
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 365;
			break;
		}

	}
	
	/**
	 *右边点击
	 * */
	private void onClickProgressPreBt() {
		switch (ProgressActivity.getDateType()) {
		case ProgressActivity.TYPE_WEEK:
			dateStyel = 0;
			starStr = mCalendar.getNextMonday();
			endStr = mCalendar.getNextSunday();
			break;
		case ProgressActivity.TYPE_MONTH:
			dateStyel = 1;
			starStr = mCalendar.getNextMonthFirst();
			endStr = mCalendar.getNextMonthEnd();

			break;
		case ProgressActivity.TYPE_YEAR:
			dateStyel = 2;
			starStr = mCalendar.getNextYearFirst();
			endStr = mCalendar.getNextYearEnd();
			break;
		default:
			break;
		}
		onDateTabButton();
	}

	public void setShowData(ShowDataView showDataView) {
		this.mShowDataView = showDataView;
	}

	public void setCharLayout(RelativeLayout layoutR, LinearLayout layoutA,
			LinearLayout layoutB, LinearLayout layoutC, LinearLayout layoutD,
			LinearLayout layoutE) {
		this.layout = layoutR;
		this.layout1 = layoutA;
		this.layout2 = layoutB;
		this.layout3 = layoutC;
		this.layout4 = layoutD;
		this.layout5 = layoutE;

	}

}
