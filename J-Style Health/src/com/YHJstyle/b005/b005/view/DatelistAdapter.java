package com.YHJstyle.b005.b005.view;

import java.util.Calendar;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DatelistAdapter extends BaseAdapter {
	
	private CalendarData mCalendarData;
	private Context mContext;
	private int pressItem = -1;
	
	
	

	public DatelistAdapter(Context context,CalendarData mCalendarData) {
		this.mCalendarData = mCalendarData;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCalendarData.getMonthTotalDay();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0L;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View localView = LayoutInflater.from(this.mContext).inflate(R.layout.data_list_cell_view, null);
		
		TextView textView1  = (TextView)localView.findViewById(R.id.date_textview);
		TextView textView2  = (TextView)localView.findViewById(R.id.week_textview);
		textView1.setText(String.valueOf(position +1));
		textView2.setText(String.valueOf(this.mCalendarData.getDateWeek(position +1 )));
		if(position != this.pressItem){
			localView.refreshDrawableState();
			localView.setBackgroundResource(R.drawable.bg_date_list_unselected);
		}else{
			localView.setBackgroundResource(R.drawable.bg_date_list_selected);
		}
			return localView;
		
		
	}
	
	
	
	public void setPosition(int i){
		this.pressItem = i;
	}
}
