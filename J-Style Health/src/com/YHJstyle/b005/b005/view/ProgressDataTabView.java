package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
	
	/**
	 * 年月日 -年月日
	 * 数据显示*/

public class ProgressDataTabView extends LinearLayout implements
		OnClickListener {
	
	private CalendarData mCalendarData;
	private Context mContext;
	private TextView mEndDate;
	private TextView mStarDate;
	
	
	public ProgressDataTabView(Context context, CalendarData calendarData) {
		super(context);
		this.mContext = context;
		this.mCalendarData = calendarData;
		initView();
	}

	private void initView() {
		View.inflate(this.mContext, R.layout.year_month_day_type_item, this);
		mStarDate = (TextView)findViewById(R.id.year_month_day_star);
		mEndDate = (TextView)findViewById(R.id.year_month_day_end);
		setButtonListener();
	}

	private void setButtonListener() {
		((Button)findViewById(R.id.reduce_bt)).setOnClickListener(this);
		((Button)findViewById(R.id.add_bt)).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.year_month_day_star:
			break;
		case R.id.year_month_day_end:
			break;
		case R.id.reduce_bt:
			break;
		case R.id.add_bt:
			break;
			

		default:
			break;
		}
	}

}
