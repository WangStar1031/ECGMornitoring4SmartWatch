package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabItemView extends LinearLayout {
	private Context context;
	
	
	public TabItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public TabItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public TabItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	public TabItemView(Context mcontext,int i, int j) {
		super(mcontext);
		this.context = mcontext;
		initView(i,j);
	}


	private void initView(int i, int j) {
		View.inflate(this.context, R.layout.view_tab_indicator, this);
		setView(i,j);
		
	}


	private void setView(int i, int j) {
		TextView textView = (TextView)findViewById(R.id.label);
		Drawable drawable = this.context.getResources().getDrawable(i);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		textView.setBackground(drawable);
		textView.setText(j);
		
	}


}
