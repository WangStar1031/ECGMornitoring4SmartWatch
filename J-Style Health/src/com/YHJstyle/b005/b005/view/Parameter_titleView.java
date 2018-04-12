package com.YHJstyle.b005.b005.view;

import java.util.jar.Attributes;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 头标
 * 之定义图*/
public class Parameter_titleView extends LinearLayout implements
		OnClickListener {
	private boolean isEdit;
	//自定义的两个 接口
	private OnLeftButtonClickListener onLeftButtonClickListener;
	private OnRightButtonClickListener onRightButtonClickListener;
	private TextView pCenterTxt;
	private Button pLeftBtn;
	private TextView pLeftTxt;
	private TextView pRightTxt;
	
	
	
	public Parameter_titleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		
		
		
	}
	
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.parameter_title_view, this);
		this.pLeftBtn = (Button)findViewById(R.id.parameter_left_btn);
		this.pLeftBtn.setOnClickListener(this);
		this.pCenterTxt = ((TextView)findViewById(R.id.parameter_center_txt));
	    this.pRightTxt = ((TextView)findViewById(R.id.parameter_right_txt));
	    this.pRightTxt.setOnClickListener(this);
		
	}

	//?/
	public void setConterText(String paramString){
		this.pCenterTxt.setText(paramString);
	}
	
	public void setLeftButton(int i, int j,OnLeftButtonClickListener paramsOnLeftButtonClickListener){
		this.pCenterTxt.setText(j);
		this.pRightTxt.setVisibility(View.GONE);
		this.onLeftButtonClickListener = paramsOnLeftButtonClickListener;
	}
	
	public void setRightButton(OnRightButtonClickListener paramsoOnRightButtonClickListener){
		this.onRightButtonClickListener = paramsoOnRightButtonClickListener;
	}
	
	public void setRightText(int paramInt){
		this.pRightTxt.setText(paramInt);
		this.pRightTxt.setVisibility(View.VISIBLE);
		this.pRightTxt.setTextSize(14.0f);
	}
	
	

	public Parameter_titleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	public Parameter_titleView(Context context) {
		super(context);
		initView();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.parameter_left_btn:
			if (this.onLeftButtonClickListener != null)
			this.onLeftButtonClickListener.onClick(v);
			break;
		case R.id.parameter_right_txt:
			if(this.onRightButtonClickListener != null)
			this.onRightButtonClickListener.onClick(v);
			break;
		}
	}
	
	public static abstract interface OnLeftButtonClickListener{
		public abstract void onClick(View v);
	}
	public static abstract interface OnRightButtonClickListener{
		public abstract void onClick(View v);
	
	}	
	
}
