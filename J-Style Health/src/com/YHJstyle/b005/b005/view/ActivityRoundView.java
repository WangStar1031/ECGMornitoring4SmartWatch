package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 记录的圆
 * */
public class ActivityRoundView extends RoundView {

	private RotateAnimation rotateAnimation;
	private ImageView roundCusorImage;

	public ActivityRoundView(Context context, AttributeSet attrs,
			LinearLayout mLinearLayout, int var) {
		super(context, attrs, mLinearLayout);
		this.compliance = var;
		initView();
	}

	@Override
	protected void initView() {
		ringText = (TextView) mLayout
				.findViewById(R.id.activity_page_textview1);
		roundCusorImage = (ImageView) mLayout
				.findViewById(R.id.round_cusor_image);
		roundRate = 0.0F;
		ringData = 0;
	}

	/**
	 * 圆的指示表
	 * */
	private void roundCusor(float f1, float f2) {
		rotateAnimation = new RotateAnimation(f1, f2, 1, 1.0F, 1, 0.5F);
		// rotateAnimation = new RotateAnimation(fromDegrees, toDegrees,
		// pivotXType, pivotXValue, pivotYType, pivotYValue)
		LinearInterpolator linearInterpolator = new LinearInterpolator();
		rotateAnimation.setInterpolator(linearInterpolator);
		rotateAnimation.setDuration(0L);
		rotateAnimation.setFillAfter(true);
		if ((rotateAnimation != null) && (!rotateAnimation.equals(null))) {
			roundCusorImage.setAnimation(rotateAnimation);
			rotateAnimation.startNow();
		}
	}

	@Override
	protected void onDraw(Canvas paramCanvas) {
		int i = getWidth() / 2;
		int j = getHeight() / 2;
		int k = dip2px(mContext, 40.0F);
		int m = dip2px(mContext, 30.0F);
		// Log.i("APP", "i = "+i+",j = "+j+",k ="+k+",m ="+m);
		RectF localRectF = new RectF(i - (k + 1 + m / 2), j - (k + 1 + m / 2),
				i + (k + 1 + m / 2), j + (k + 1 + m / 2));
		paint.setStrokeWidth(25.0F);

		RadialGradient localRadialGradient1 = new RadialGradient(0.0F, 0.0F, k,
				Color.argb(254, 143, 185, 78), Color.argb(154, 166, 205, 85),
				TileMode.MIRROR);
		paint.setAlpha(0xFF);
		paint.setShader(localRadialGradient1);
		paramCanvas
				.drawArc(localRectF, 180.0F, 3.6F * compliance, false, paint);
		RadialGradient localRadialGradient2 = new RadialGradient(0.0F, 0.0F, k,
				Color.argb(254, 87, 87, 87), Color.argb(154, 111, 111, 111),
				TileMode.MIRROR);
		this.paint.setAlpha(0xFF);
		this.paint.setShader(localRadialGradient2);
		paramCanvas.drawArc(localRectF, 180.0F + 3.6F * compliance,
				360.0F - 3.6F * compliance, false, paint);

		roundRate = 3.6F + roundRate;
		if (ringData < compliance) {
			ringData = (1 + ringData);
		}

		if (compliance != 0) {
			if (ringData < 100) {
				ringText.setText(String.valueOf(ringData).trim());
			} else {
				ringText.setText(String.valueOf(100).trim());
			}

			roundCusor((float) (3.6D * ringData - 3.6D),
					(float) (3.6D * ringData));
		} else {
			ringText.setText(String.valueOf(0).trim());
			roundCusor(0, 0);
		}

		if ((roundRate >= 356.39999999999998D) && (compliance >= 100)) {
			RadialGradient localRadialGradient3 = new RadialGradient(0F, 0F, k,
					Color.argb(254, 143, 185, 78), Color.argb(154, 111, 111,
							111), TileMode.MIRROR);
			paint.setAlpha(0XFF);
			paint.setShader(localRadialGradient3);
			paramCanvas.drawArc(localRectF, 180F, 360F, false, paint);
		}

		RadialGradient localRadialGradient4 = new RadialGradient(0F, 0F, k,
				Color.argb(254, 143, 185, 78), Color.argb(154, 166, 205, 85),
				Shader.TileMode.MIRROR);
		paint.setAlpha(0xFF);
		paint.setShader(localRadialGradient4);
		paramCanvas.drawArc(localRectF, 180, roundRate, false, paint);
		RadialGradient localRadialGradient5 = new RadialGradient(0F, 0F, k,
				Color.argb(254, 87, 87, 87), Color.argb(154, 111, 111, 111),
				Shader.TileMode.MIRROR);
		paint.setAlpha(0xFF);
		paint.setShader(localRadialGradient5);
		paramCanvas.drawArc(localRectF, 180, roundRate, false, paint);
		if ((roundRate <= 3.6D * compliance) && (roundRate <= 360.0F)) {
			postInvalidate();
		}
		super.onDraw(paramCanvas);
	}

}
