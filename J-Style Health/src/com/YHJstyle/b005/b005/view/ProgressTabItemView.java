package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgressTabItemView extends LinearLayout {

	private Context mContext;
	public ProgressTabItemView(Context context,int i) {
		super(context);
		this.mContext = context;
		initView(i);
	}

	private void initView(int i) {
		View.inflate(this.mContext,R.layout.view_progress_tab_cell, this);
		setView(i);
	}

	private void setView(int i) {
		((TextView)findViewById(R.id.label)).setText(i);
	}

	public ProgressTabItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ProgressTabItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
}
