package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("DrawAllocation")
public class HomeRoundView extends RoundView {

	public HomeRoundView(Context context, AttributeSet attrs,
			RelativeLayout relativeLayout, int i, int j) {
		super(context, attrs, relativeLayout);
		this.goaldStyle = i;
		initView();
		this.compliance = j;
	}

	@Override
	protected void initView() {
		this.ringText = ((TextView) this.mLayout
				.findViewById(R.id.home_page_textView1));
		this.roundRate = 0.0F;
		this.ringData = 0;

	}

	/**
	 * 重绘
	 * 
	 * */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		super.onDraw(canvas);
		int a = getWidth() / 2;
		int b = getHeight() / 2;
		// 绘制的圆半径
		int c = dip2px(this.mContext, 60.0F);
		int d = dip2px(this.mContext, 30.0F);

		RectF rect1 = new RectF(a - (c + 1 + d / 2), b - (c + 1 + d / 2), a
				+ (c + 1 + d / 2), b + (c + 1 + d / 2));
		RectF rect2 = new RectF(a - (c + 6 + d / 2), b - (c + 6 + d / 2), a
				+ (c + 6 + d / 2), b + (c + 6 + d / 2));
		RectF rect3 = new RectF(a - (c - 4 + d / 2), b - (c - 4 + d / 2), a
				+ (c - 4 + d / 2), b + (c - 4 + d / 2));
		this.paint.setStrokeWidth(1.0F);// 设置线宽
		// 角度渐变，径向渐变 着色器 RadialGradient ra = new RadialGradient(x, y, radius,
		// color0, color1, tile
		RadialGradient radialGradient1 = new RadialGradient(0.0F, 0.0F, c,
				Color.argb(254, 66, 66, 66), Color.argb(254, 88, 88, 88),
				Shader.TileMode.MIRROR);
		// 透明度变化
		this.paint.setAlpha(0xFF);
		// 画笔渐变
		this.paint.setShader(radialGradient1);
		// oval :指定圆弧的外轮廓矩形区域。
		// startAngle: 圆弧起始角度，单位为度。
		// sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
		// useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
		// paint: 绘制圆弧的画板属性，如颜色，是否填充等。
		// canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint)
		canvas.drawArc(rect2, 98.0F, 344.0F, false, this.paint);

		this.paint.setStrokeWidth(1.0F);
		RadialGradient radialGradient2 = new RadialGradient(0.0F, 0.0F, c,
				Color.argb(254, 88, 88, 88), Color.argb(254, 66, 66, 66),
				Shader.TileMode.MIRROR);
		this.paint.setAlpha(0XFF);
		this.paint.setShader(radialGradient2);
		canvas.drawArc(rect3, 98.0F, 344.0F, false, this.paint);

		this.paint.setStrokeWidth(10.0F);
		this.paint.setARGB(30, 127, 255, 212);
		canvas.drawArc(rect1, 82.0F, 0.0F, false, this.paint);
		// 判断风格
		
		switch (this.goaldStyle) {
		case INITR_ROUND://0
			float f = (float) (3.44D * compliance);
			RadialGradient radialGradient3 = new RadialGradient(0.0F, 0.0F, c,
					Color.RED, Color.YELLOW, Shader.TileMode.MIRROR);
			this.paint.setAlpha(0XFF);
			this.paint.setShader(radialGradient3);
			canvas.drawArc(rect1, 98.0F, f, false, this.paint);
			RadialGradient radialGradient4 = new RadialGradient(0.0F, 0.0F, c,
					Color.argb(254, 48, 48, 48), Color.argb(254, 48, 48, 47),
					Shader.TileMode.MIRROR);
			this.paint.setAlpha(0xFF);
			this.paint.setShader(radialGradient4);
			canvas.drawArc(rect1, 98.0F + f, 344.0F - f, false, this.paint);
			//compliance空 在progressactivity
//			Log.i("APP", "圆返回的是"+String.valueOf(this.compliance).toString().trim());
			ringText.setText(String.valueOf(this.compliance).toString().trim());
			if (compliance == 0) {
				ringText.setText("0");
			}
			super.onDraw(canvas);
			break;
		case PROGRESS_ROUND://1
				RadialGradient radialGradient5 = new RadialGradient(0.0F, 0.0F,
						c, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
				this.paint.setAlpha(0xFF);
				this.paint.setShader(radialGradient5);
				canvas.drawArc(rect1, 98.0F, roundRate, false, paint);
				if (compliance == 0) {
					ringText.setText("0");
				}

				RadialGradient radialGradient6 = new RadialGradient(0.0F, 0.0F,
						c, Color.argb(254, 48, 48, 48), Color.argb(254, 48, 48,
								47), Shader.TileMode.MIRROR);
				paint.setAlpha(0xFF);
				paint.setShader(radialGradient6);
				canvas.drawArc(rect1, 98.0F + roundRate, 344.0F - roundRate,
						false, this.paint);
				roundRate = (3.44F + roundRate);
				if (ringData < compliance) {
					ringData = (1 + ringData);
					ringText.setText(String.valueOf(this.ringData).trim());
				}
				super.onDraw(canvas);
			break;

		}
		
		if(roundRate > 3.44 * compliance){
			
			postInvalidate();
		}

		
	}
}
