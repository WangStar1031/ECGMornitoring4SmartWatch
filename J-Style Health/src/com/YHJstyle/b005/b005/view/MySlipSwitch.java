package com.YHJstyle.b005.b005.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 闹钟控制�?��
 * */
public class MySlipSwitch extends View implements OnTouchListener {

	private float currentX;
	private boolean isSlipping = false;
	private boolean isSwitchListenerOn = false;
	private boolean isSwitchOn = false;
	private Rect on_Rect;
	private OnSwitchListener onSwitchListener;// 滑动监听
	private Rect off_Rect;
	private float previousX;
	private Bitmap slip_Btn;
	private Bitmap switch_off_Bkg;// �?
	private Bitmap switch_on_Bkg;

	public MySlipSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MySlipSwitch(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOnTouchListener(this);

	}

	// 得到选中状�?
	protected boolean getSwitchState() {
		return this.isSwitchOn;

	}

	/**
	 * 闹钟�?��绘制
	 * */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Matrix localMatrix = new Matrix();
		Paint localPaint = new Paint();
		float f = 0;
		//根据currentX设置背景，开或�?关状�?
		if (currentX < switch_on_Bkg.getWidth() / 2) {
			canvas.drawBitmap(switch_off_Bkg, localMatrix, localPaint);
			
		} else {
			canvas.drawBitmap(switch_on_Bkg, localMatrix, localPaint);

		}
		
		if(isSlipping){
			if(currentX >= switch_on_Bkg.getWidth())
				f = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
			else
				f = currentX- (slip_Btn.getWidth() / 2);
		}else{
			if(isSwitchOn){
				f = on_Rect.left;
			}else{
				f = off_Rect.left;
			}
		}
		
		//对滑块滑动进行异常出来，不能让滑块出�?
		if(f < 0){
			f = 0;
		}else if(f > switch_on_Bkg.getWidth() - slip_Btn.getWidth()){
			f = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
		}
		
		canvas.drawBitmap(slip_Btn, f, 0.0F, localPaint);
		
		
	}

	// 根据int值绘�?

	public void setImageResource(int paramInt1, int paramInt2, int paramInt3) {
		switch_on_Bkg = BitmapFactory.decodeResource(getResources(), paramInt1);
		switch_off_Bkg = BitmapFactory
				.decodeResource(getResources(), paramInt2);
		slip_Btn = BitmapFactory.decodeResource(getResources(), paramInt3);
		on_Rect = new Rect(switch_off_Bkg.getWidth() - slip_Btn.getWidth(), 0,
				switch_off_Bkg.getWidth(), slip_Btn.getHeight());
		off_Rect = new Rect(0, 0, slip_Btn.getWidth(), slip_Btn.getHeight());
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(switch_on_Bkg.getWidth(),
				switch_on_Bkg.getHeight());
	}

	public MySlipSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		boolean i = false;
		invalidate();
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:// �?��0
//			Log.i("APP", "DWON-----0");
			
			if ((event.getX() > switch_on_Bkg.getWidth())
					&& (event.getY() > switch_on_Bkg.getHeight())) {
				return false;
			}else{
				isSlipping = true;
				previousX = event.getX();
				currentX = previousX;
				
			}
			break;
		case MotionEvent.ACTION_UP:// 结束1
//			Log.i("APP", "UP-----0");
			isSlipping = false;
			if (event.getX() >= switch_on_Bkg.getWidth() / 2) {
				isSwitchOn = true;
			} else {
				isSwitchOn = false;
			}
			if ((isSwitchListenerOn) && (isSwitchOn)) {

				onSwitchListener.onSwitched(isSwitchOn);
			}
			break;
		case MotionEvent.ACTION_MOVE:// 移动�?
//			Log.i("APP", "MOVE-----0");
			currentX = event.getX();
			break;
		}
		invalidate();
		
		return true;
	}

	public void setOnSwitchListener(OnSwitchListener paramOnSwitchListener) {
		this.onSwitchListener = paramOnSwitchListener;
		this.isSwitchListenerOn = true;
	}

	public void setSwitchState(boolean paramBoolean) {
		this.isSwitchOn = paramBoolean;
	}

	protected void updateSwitchState(boolean paramBoolean) {
		this.isSwitchOn = paramBoolean;
		invalidate();
	}

	public static abstract interface OnSwitchListener {
		public abstract void onSwitched(boolean paramBoolean);
	}
}
