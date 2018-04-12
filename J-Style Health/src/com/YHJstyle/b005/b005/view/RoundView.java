package com.YHJstyle.b005.b005.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class RoundView extends View {
	public static final int INITR_ROUND = 0;
	public static final int PROGRESS_ROUND =1;
	protected int compliance;//���� ����
	protected int goaldStyle;//����
	protected Context mContext;
	protected final Paint paint;
	protected ViewGroup mLayout;
	protected int ringData;//��������
	protected TextView ringText;
	protected TextView ringText2;
	protected float roundRate;//Բ�ĽǶ�
	
	//���췽���� ViewGroup
	public RoundView(Context context, AttributeSet attrs, ViewGroup viewGroup) {
		super(context, attrs);
		this.mContext = context;
		this.mLayout = viewGroup;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setAntiAlias(true);
		this.paint.setStyle(Paint.Style.STROKE);
	}
	//������
	public RoundView(Context context,ViewGroup viewGroup){
		this(context,null,viewGroup);
	}
	
	/**
	 * �����ֻ��ֱ��ʴ�
	 * ��dp���px
	 * */
	public static int dip2px(Context context,float f){
		return (int)(0.5F+ f*context.getResources().getDisplayMetrics().density);
	}
	
	protected abstract void initView();
}
