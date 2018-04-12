package com.YHJstyle.b005.b005.view;

import java.util.concurrent.TimeUnit;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleepRoundGoalView extends RoundView {
	private static final String TAG = "SleepRoundGoalView";
	double sleepBestTime;
	double sleepTotalTime;

	public SleepRoundGoalView(Context context, AttributeSet attrs,
			LinearLayout linearLayout, int i, double m, double n) {
		super(context, attrs, linearLayout);
		this.compliance = i;
		initView();
		this.sleepTotalTime = m;
		this.sleepBestTime = n;
	}

	@Override
	protected void initView() {
		ringText = (TextView) mLayout.findViewById(R.id.sleep_accom_text);
		ringText2 = (TextView) mLayout.findViewById(R.id.sleep_accom_text2);
		roundRate = 0.0f;
		ringData = 0;
	}

	/**
	 * 
	 * */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int i = getWidth() / 2;
		int j = getHeight() / 2;
		int k = dip2px(mContext, 40.0F);
		int m = dip2px(mContext, 30.0F);
		RectF rectF = new RectF(i - (k + 1 + m / 2), j - (k + 1 + m / 2), i
				+ (k + 1 + m / 2), j + (k + 1 + m / 2));
		paint.setStrokeWidth(25.0F);//Ïß¿í
		
		roundRate = (4.5F + roundRate);
//		 Log.v("======", "SleepRoundGoalView roundRtae = " + roundRate);

		if (roundRate <= 360.0F) {
			RadialGradient localRadialGradient1 = new RadialGradient(0.0F,
					0.0F, k, Color.argb(254, 0, 255, 0), Color.argb(154, 177,
							205, 130), TileMode.MIRROR);
			this.paint.setAlpha(0xFF);
			this.paint.setShader(localRadialGradient1);
			canvas.drawArc(rectF, 270.0F, this.roundRate, false, this.paint);
			RadialGradient localRadialGradient2 = new RadialGradient(0.0F,
					0.0F, k, Color.argb(254, 255, 255, 255), Color.argb(154,
							255, 255, 255), TileMode.MIRROR);
			this.paint.setAlpha(0xFF);
			this.paint.setShader(localRadialGradient2);
			canvas.drawArc(rectF, 270.0F + roundRate, 360.0F - roundRate,
					false, paint);

			if ((this.sleepTotalTime == 0.0D) && (this.sleepBestTime == 0.0D)) {
				ringText.setText("0.0h/");
				ringText2.setText("0.0h");
			} else {
				this.ringText.setText(String.valueOf(this.sleepTotalTime)
						+ "h / ");
				this.ringText2
						.setText(String.valueOf(this.sleepBestTime) + "h");

			}



		}else{
			
			
			RadialGradient localRadialGradient3 = new RadialGradient(0.0F,
					0.0F, k, Color.argb(254, 0, 255, 0), Color.argb(154, 177,
							205, 130), TileMode.MIRROR);
			this.paint.setAlpha(255);
			this.paint.setShader(localRadialGradient3);
			canvas.drawArc(rectF, 270.0F, 360.0F, false, this.paint);
		}
			
			
		if ((roundRate < 4.5D * compliance) && (roundRate < 360.0f)) {
			
			
			postInvalidate();
		}
		
	}
}
