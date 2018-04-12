package com.YHJstyle.b005.j_style_Pro.b005.adapter;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DateListAdapter extends BaseAdapter {
	protected  static final String TAG = "DateListAdapter";
	private CalendarData calendarData;
	private int pressItem = -1;
	private Context context;
	
	public DateListAdapter(Context mContext,CalendarData mc) {
		this.context = mContext;
		this.calendarData = mc;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return calendarData.getMonthTotalDay();
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
		View view = LayoutInflater.from(context).inflate(R.layout.data_list_cell_view,null);
		TextView textView1 = (TextView)view.findViewById(R.id.date_textview);
		TextView textView2 = (TextView)view.findViewById(R.id.week_textview);
		textView1.setText(String.valueOf(position +1));
		textView2.setText(String.valueOf(calendarData.getDateWeek(position)+1));
		if(position == pressItem){
			view.setBackgroundResource(R.drawable.bg_date_list_selected);
		}else{
			view.refreshDrawableState();
			view.setBackgroundResource(R.drawable.bg_date_list_unselected);
		}
		
		return view;
	}	
	
	public void setPosition(int position){
		this.pressItem = position;
	}
	
}
