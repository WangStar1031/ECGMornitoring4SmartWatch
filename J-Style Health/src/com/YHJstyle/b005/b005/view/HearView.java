package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.jump.HeartActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class HearView extends View {

	private Paint mPaint, blackPaint, wangge_Paint, pixPaint;
	private Path mPath;
	public int index = 0;

	private int SCREEN_WIDTH = 480;

	private int POINT_LEN = 3000;

	private int rulerStartX = 0;

	private int ecg_height = 0;
	private int ecg_width = 0;
	
	// public float[] x = new float[POINT_LEN];
	// public float[] y = new float[POINT_LEN];
	public float[] x = new float[POINT_LEN];
	public float[] y = new float[POINT_LEN];
	public float scale = 0;
	public float x0 = 240;
	public float y0 = 192;
	public float distanceY = 0;

	public HearView(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics dm = new DisplayMetrics();
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			SCREEN_WIDTH = dm.widthPixels;
		}
		mPath = new Path();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setTextSize(2);
		mPaint.setColor(Color.BLACK);

		blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackPaint.setStyle(Paint.Style.STROKE);
		blackPaint.setStrokeWidth(2);
		blackPaint.setTextSize(2);
		blackPaint.setColor(Color.BLACK);
		// 网格线
		wangge_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		wangge_Paint.setStyle(Paint.Style.STROKE);
		wangge_Paint.setStrokeWidth(1.0f);
		wangge_Paint.setTextSize(0.5f);
		// wangge_Paint.setColor(Color.rgb(255, 155, 165));
		wangge_Paint.setColor(Color.rgb(240, 128, 128));

		pixPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pixPaint.setStyle(Paint.Style.FILL);
		pixPaint.setStrokeWidth(2.0f);
		pixPaint.setTextSize(20.0f);
		pixPaint.setColor(Color.RED);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		float width = SCREEN_WIDTH;
		scale = 1;

		String ruleStr = new String(scale * 10 + "mm/100mv");
		// 移动后的坐标
		mPath = makeFollowPathOne();

		rulerStartX = SCREEN_WIDTH - 60;
		
		ecg_height = HeartActivity.heigh;
		ecg_width = HeartActivity.width;
		
		int row = ecg_height /32;
		
		int colomn = ecg_width/ 32;//
		// 画标尺
//		canvas.drawLine(0, 192 + 32 * distanceY, 8, 192 + 32 * distanceY,
//				pixPaint);
//		canvas.drawLine(24, 192 + 32 * distanceY, 32, 192 + 32 * distanceY,
//				pixPaint);
//		canvas.drawLine(8, 128 - (32 * (scale - 1)) + 32 * distanceY, 24, 128
//				- (32 * (scale - 1)) + 32 * distanceY, pixPaint);
//		canvas.drawLine(8, 128 - (32 * (scale - 1)) + 32 * distanceY, 8,
//				192 + 32 * distanceY, pixPaint);
//		canvas.drawLine(24, 128 - (32 * (scale - 1)) + 32 * distanceY, 24,
//				192 + 32 * distanceY, pixPaint);

		if (index <= 4) {
			canvas.drawPoint(index, y[index], blackPaint);
		} else {
			canvas.drawPoint(index, y[index], blackPaint);
			canvas.drawPoint(index - 1, y[index - 1], blackPaint);
			canvas.drawPoint(index - 2, y[index - 2], blackPaint);
			canvas.drawPoint(index - 3, y[index - 3], blackPaint);
			canvas.drawPoint(index - 4, y[index - 4], blackPaint);

		}

		for (int i = 0; i <= row; i++) {
//		for (int i = 0; i <= 13; i++) {
			canvas.drawLine(0, i * 32, width, i * 32, wangge_Paint);
		}
		for (int i = 0; i <= colomn; i++) {
			canvas.drawLine(i * 32, 0, i * 32, ecg_width, wangge_Paint);//400 3 12*32
//			canvas.drawLine(i * 32, 0, i * 32, 400, wangge_Paint);//400 3 12*32
		}

		// 画标尺

		// canvas.drawLine(rulerStartX+0, 192, rulerStartX+8, 192, pixPaint);
		// canvas.drawLine(rulerStartX+24, 192, rulerStartX+32, 192, pixPaint);
		// canvas.drawLine(rulerStartX+8, 128 , rulerStartX+24, 128, pixPaint);
		// canvas.drawLine(rulerStartX+8, 128, rulerStartX+8, 192, pixPaint);
		// canvas.drawLine(rulerStartX+24, 128, rulerStartX+24, 192, pixPaint);
		// canvas.drawText(ruleStr, rulerStartX-120, 40, pixPaint);
		// canvas.drawText("25mm/s", rulerStartX-200, 40, pixPaint);

		canvas.drawPath(mPath, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
			SCREEN_WIDTH = MeasureSpec.getSize(widthMeasureSpec);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private Path makeFollowPathOne() {
		Path p = new Path();
		p.moveTo(x[0], y[0]);

		for (int i = 1; i < POINT_LEN; i++) {
			p.lineTo(x[i], y[i]);
		}
		return p;

	}

	public void setYDistance(float distance) {
		distanceY = distance;
	}
}
