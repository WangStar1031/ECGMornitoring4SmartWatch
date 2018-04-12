package com.YHJstyle.b005.b005.view;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
//import android.util.Log;
import android.view.View;

/**
 * 睡眠时间界面的描绘
 * */
public class CharSleepView {
	private static final int SERIES_NR = 2;
	private static final String TAG = "CharSleepView";
	public static double currentBestSleepTime;
	public static double currentSleepTime;
	private int[] dataResult;
	private Context context;
	private View mView;
	private CategorySeries series;
	private String[] str_time;

	public CharSleepView(Context mContext) {
		this.context = mContext;
		this.series = new CategorySeries("");
		this.str_time = new String[] { "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
				"23:00", "24:00", "1:00", "2:00", "3:00", "4:00", "5:00",
				"6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00" };
	}

	/**
	 * 睡眠时间分为96个时间段 15分钟一个段落
	 * */
	private XYMultipleSeriesDataset getBarDataset() {
		XYMultipleSeriesDataset mXyMultipleSeriesDataset = new XYMultipleSeriesDataset();

		for (int i = 0; i < 96; i++) {
			series.add(dataResult[i]);

		}
		// Log.i("APP", Arrays.toString(dataResult));
		mXyMultipleSeriesDataset.addSeries(series.toXYSeries());
		series.clear();
		return mXyMultipleSeriesDataset;
	}

	/**
	 * 设置X轴 Y轴
	 * */
	private void setCharSettings(
			XYMultipleSeriesRenderer mypMultipleSeriesRenderer) {
		mypMultipleSeriesRenderer.setYTitle("");
		mypMultipleSeriesRenderer.setXAxisMin(0.0);
		mypMultipleSeriesRenderer.setXAxisMax(24.0);
		mypMultipleSeriesRenderer.setYAxisMin(0.0);
		mypMultipleSeriesRenderer.setYAxisMax(20.0);

	}

	/**
	 * 睡眠时间 图像怎么显示 X轴 Y轴
	 * */
	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer mXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
		simpleSeriesRenderer.setGradientEnabled(true);// 是否设置梯度
		simpleSeriesRenderer.setGradientStart(0.0D, Color.BLUE);
		simpleSeriesRenderer.setGradientStop(128.0D, Color.GREEN);
		mXYMultipleSeriesRenderer.addSeriesRenderer(simpleSeriesRenderer);
		setCharSettings(mXYMultipleSeriesRenderer);
		mXYMultipleSeriesRenderer.setMargins(new int[4]);
		mXYMultipleSeriesRenderer.setBackgroundColor(Color.BLACK);
		mXYMultipleSeriesRenderer.setBarSpacing(1.0D);
		mXYMultipleSeriesRenderer.setXLabelsAlign(Paint.Align.LEFT);
		mXYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.RIGHT);
		mXYMultipleSeriesRenderer.setMarginsColor(Color.argb(0, 255, 0, 0));
		mXYMultipleSeriesRenderer.setPanLimits(new double[] { 0.0D, 100.0D,
				0.0D, 128.0D });
		mXYMultipleSeriesRenderer.setShowLegend(false);
		mXYMultipleSeriesRenderer.setZoomEnabled(true, false);
		mXYMultipleSeriesRenderer.setXLabels(0);
		mXYMultipleSeriesRenderer.setLabelsTextSize(25.0F);
		int i = 0;
		for (int j = 0; j < 96; j += 4) {
			mXYMultipleSeriesRenderer.addXTextLabel(j, str_time[i]);
			i++;
		}
		mXYMultipleSeriesRenderer.setXLabelsColor(Color.RED);
		mXYMultipleSeriesRenderer.setPanEnabled(true, false);
		mXYMultipleSeriesRenderer.setZoomEnabled(false, false);
		return mXYMultipleSeriesRenderer;

	}

	/**
	 * 得到图像显示
	 * */

	public View getView() {
		mView = ChartFactory.getBarChartView(context, getBarDataset(),
				getBarRenderer(), Type.DEFAULT);
		return mView;
	}

	// public int[] getDataResult() {
	// return dataResult;
	// }
	// public void setDataResult(int[] dataResult) {
	// this.dataResult = dataResult;
	// }
	/**
	 * 设置睡眠数据 当天的下午和后天的00 -- 12
	 * */
	public void setDATE(int[] mon, int[] after) {
		int[] ped = new int[96];
		currentSleepTime = 0.0d;
		currentBestSleepTime = 0.d;
		if ((mon != null) && (mon.length != 0)) {
			for (int i = 48; i <= 95; i++) {
				ped[i - 48] = mon[i];
			}
		}
		if ((after != null) && (after.length != 0)) {
			for (int k = 0; k <= 47; k++) {
				ped[k + 47] = after[k];
			}
		}
		this.dataResult = ped;
		//
		double d1 = 0.0d;
		double d2 = 0.0d;
		int i = 0;
		for (int j = 0; j < dataResult.length; j++) {
			if (ped[j] != 0) {
//				Log.i("APP", "MyApplication  = " + ped[j] + "     "
//						+ dataResult[j]);
				if (ped[j] == -1) {
					ped[j] = 1;

				}
				i = 1;
				d2 += 1.0d;
				if (ped[j] <= 3) {
					d1 += 1.0d;
				}
			}
		}
		if (i != 0) {
			// Log.d("=====", "MyApplication Time = " + 8.0D * d2 + "    " +
			// 8.0D
			// * d1);
			currentSleepTime = new BigDecimal(15.0D * d2 / 60.d).setScale(1, 4)
					.doubleValue();
			currentBestSleepTime = new BigDecimal(15.0D * d1 / 60.d).setScale(
					1, 4).doubleValue();
		}
	}

	public void setData(int[] arr) {
		int[] res = new int[96];

		if (arr != null) {
			arr = res;
		}
		double d1 = 0.0D;
		double d2 = 0.0D;
		dataResult = res;
		int i = 0;
		for (int j = 0; j < dataResult.length; j++) {
			if (res[j] != 0) {
				if (res[j] == -1) {
					res[j] = 0;
				}
				i = 1;
				d2 += 1.0d;
				if (arr[j] <= 3) {
					d1 += 1.0D;
				}
			}
		}

		if (i != 0) {
			currentSleepTime = new BigDecimal(15.0D * d2 / 60.0D)
					.setScale(1, 4).doubleValue();
			currentBestSleepTime = new BigDecimal(15.0D * d1 / 60.0d).setScale(
					1, 4).doubleValue();
		}

	}

}
