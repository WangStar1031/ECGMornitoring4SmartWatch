package com.YHJstyle.b005.b005.view;

import com.YHJstyle.b005.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoundGoalView extends View {
	private static final String TAG = "RoundGoalView";
	private int compliance;
	private final Context mContext;
	private RelativeLayout mLayout;
	private final Paint paint;
	private int ringData;
	private TextView ringText;
	private float roundRate;
	
	public RoundGoalView(Context context,AttributeSet attr,RelativeLayout relativeLayout){
		super(context,attr);
		this.mContext = context;
		this.mLayout  = relativeLayout;
		initView();
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setAntiAlias(true);
		this.paint.setStyle(Paint.Style.STROKE);
	}

	private void initView() {
		ringText = (TextView)this.mLayout.findViewById(R.id.home_page_textView1);
		roundRate = 0.0F;
		ringData = 0;
		compliance = 88;
		
	}
	
	public RoundGoalView(Context context,RelativeLayout relativeLayout){
		this(context,null,relativeLayout);
		initView();
	}
	
	public static int dip2px(Context context,float paramFolat){
		
		return (int)(0.5F + paramFolat * context.getResources().getDisplayMetrics().density);
	}
	
	
	
	
	@Override
	protected void onDraw(Canvas paramCanvas) {
		
		int i = getWidth() / 2;
	    int j = getHeight() / 2;
	    int k = dip2px(this.mContext, 60.0F);
	    int m = dip2px(this.mContext, 30.0F);
	    RectF localRectF1 = new RectF(i - (k + 1 + m / 2), j - (k + 1 + m / 2), i + (k + 1 + m / 2), j + (k + 1 + m / 2));
	    RectF localRectF2 = new RectF(i - (k + 6 + m / 2), j - (k + 6 + m / 2), i + (k + 6 + m / 2), j + (k + 6 + m / 2));
	    RectF localRectF3 = new RectF(i - (k - 4 + m / 2), j - (k - 4 + m / 2), i + (k - 4 + m / 2), j + (k - 4 + m / 2));
	    this.paint.setStrokeWidth(10.0F);
	    RadialGradient localRadialGradient1 = new RadialGradient(0.0F, 0.0F, k, -65536, -256, TileMode.MIRROR);
	    this.paint.setAlpha(0xFF);
	    this.paint.setShader(localRadialGradient1);
	    paramCanvas.drawArc(localRectF1, 98.0F, this.roundRate, false, this.paint);
	    this.paint.setARGB(30, 127, 255, 212);
	    paramCanvas.drawArc(localRectF1, 82.0F, 0.0F, false, this.paint);
	    RadialGradient localRadialGradient2 = new RadialGradient(0.0F, 0.0F, k, Color.argb(254, 48, 48, 48), Color.argb(254, 48, 48, 47), TileMode.MIRROR);
	    this.paint.setAlpha(0xFF);
	    this.paint.setShader(localRadialGradient2);
	    paramCanvas.drawArc(localRectF1, 98.0F + this.roundRate, 344.0F - this.roundRate, false, this.paint);
	    this.paint.setStrokeWidth(1.0F);
	    RadialGradient localRadialGradient3 = new RadialGradient(0.0F, 0.0F, k, Color.argb(254, 66, 66, 66), Color.argb(254, 88, 88, 88), TileMode.MIRROR);
	    this.paint.setAlpha(0xFF);
	    this.paint.setShader(localRadialGradient3);
	    paramCanvas.drawArc(localRectF2, 98.0F, 344.0F, false, this.paint);
	    this.paint.setStrokeWidth(1.0F);
	    RadialGradient localRadialGradient4 = new RadialGradient(0.0F, 0.0F, k, Color.argb(254, 88, 88, 88), Color.argb(254, 66, 66, 66), TileMode.MIRROR);
	    this.paint.setAlpha(0xFF);
	    this.paint.setShader(localRadialGradient4);
	    paramCanvas.drawArc(localRectF3, 98.0F, 344.0F, false, this.paint);
	    this.roundRate = (3.44F + this.roundRate);
	    if (this.ringData < this.compliance)
	    {
	      this.ringData = (1 + this.ringData);
	      this.ringText.setText(String.valueOf(this.ringData).trim());
	    }
	    super.onDraw(paramCanvas);
	    if (this.roundRate <= 3.44D * this.compliance) {
	      postInvalidate();
	    }
		
		
		
	}
	
	
}
