package com.YHJstyle.b005.jump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.tool.ImageUtils;

public final class LoadActivity extends Activity {
	public static final long SKIPTIME = 3000L;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_page);
		image = (ImageView) findViewById(R.id.welcome_image);
		image.setBackground(ImageUtils.ControlBitMap(LoadActivity.this,R.drawable.loading));
		AnimationSet localAnimationSet = new AnimationSet(true);
		ScaleAnimation localScaleAnimation = new ScaleAnimation(0.0F, 1.0F,
				0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation.setDuration(500L);
		localAnimationSet.addAnimation(localScaleAnimation);
		localAnimationSet.setFillAfter(true);
		this.image.startAnimation(localAnimationSet);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(LoadActivity.this,
						BaseViewActivity.class);
				startActivity(intent);
				finish();
			}
		}, SKIPTIME);
	}
}
