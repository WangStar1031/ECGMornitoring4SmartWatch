package com.YHJstyle.b005.b005.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

public class DateListView extends ListView {
	private Context context;
	private int distance;
	private int firstOut;
	// 一天(星期)的手机监听
	private boolean outBound = false;
	GestureDetector gestureDetector = new GestureDetector(
			new OnGestureListener() {

				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void onShowPress(MotionEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float distanceX, float distanceY) {
					int i = DateListView.this.getFirstVisiblePosition();
					int j = DateListView.this.getLastVisiblePosition();
					int k = DateListView.this.getCount();
					if ((DateListView.this.outBound) && (i != 0)
							&& (j != k - 1)) {
						DateListView.this.scrollTo(0, 0);
						return false;
					}
					View localView = DateListView.this.getChildAt(i);
					if (!DateListView.this.outBound) {
						DateListView.this.firstOut = (int) e2.getRawY();
					}
					if (localView != null) {
						distance = (firstOut - (int) e2.getRawY());
						DateListView.this.scrollBy(0,
								DateListView.this.distance / 2);
						return true;
					}
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}
			});

	public DateListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DateListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DateListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 重载触碰
	 * */
	public boolean dispathTouchEvent(MotionEvent ev) {
		int i = ev.getAction();
		if (((i == ev.ACTION_UP) || (i == ev.ACTION_CANCEL)) && (!outBound)) {
			outBound = true;
			if (gestureDetector.onTouchEvent(ev)) {
				if (outBound) {
					Rect localRect = new Rect();
					getLocalVisibleRect(localRect);
					TranslateAnimation localTranslateAnimation = new TranslateAnimation(
							0.0F, 0.0F, -localRect.top, 0.0F);
					localTranslateAnimation.setDuration(300L);
					startAnimation(localTranslateAnimation);
					scrollTo(0, 0);
					outBound = false;
				}
			}
		}
		return super.dispatchTouchEvent(ev);
	}

}
