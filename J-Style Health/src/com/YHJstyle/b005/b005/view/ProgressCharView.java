package com.YHJstyle.b005.b005.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
//import android.util.Log;
import android.view.View;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.jump.ProgressActivity;

/**
 * 进度画面
 * */

public class ProgressCharView extends CharView {
	public static final int TAB_CALORIES = 3;
	public static final int TAB_DISTANCE = 2;
	public static final int TAB_SLEEP = 4;
	public static final int TAB_STEPS = 0;
	public static final int TAB_TIME = 1;
	private int[] colors = null;
	private List<double[]> data = new ArrayList();
	private int dataStyle;
	private int date;
	private ArrayList<PedoMeter> mPedometer = new ArrayList();
	private int nullData;//
	private XYMultipleSeriesRenderer renderer;//
	private PointStyle[] styles = null;
	private String title;
	private String[] titles;
	private List<double[]> values = new ArrayList();
	private List<double[]> x = new ArrayList();
	private String[] xWeekLable;
	private String[] xYearLable;

	public ProgressCharView() {
		// TODO Auto-generated constructor stub
	}

	// 构造函数 两个字符数组 x轴 y轴
	public ProgressCharView(String[] stringA, String[] stringB) {
		this.xWeekLable = stringA;
		this.xYearLable = stringB;
	}

	@SuppressWarnings("deprecation")
	public View execute(Context context, int paramInt1, int paramInt2,
			String str, ArrayList<PedoMeter> list) {
		this.dataStyle = paramInt2;
		this.date = paramInt1;
		this.title = str;
		this.mPedometer = list;
		splitObject();// 中间分离数据 判断年月日
		switch (dataStyle) {
		case TAB_STEPS:// 0
			if (data.get(0) != null) {
				this.values.add(data.get(0));
				this.colors = new int[] { Color.YELLOW };
				PointStyle[] arrayOfPointStyle5 = new PointStyle[1];
				arrayOfPointStyle5[0] = PointStyle.CIRCLE;
				this.styles = arrayOfPointStyle5;
				if (MyApplication.isYearBtn) {
					setData(this.date, 160000, 160000);
				} else {
					setData(this.date, 16000, 100000);
				}
			}
			break;
		case TAB_TIME: // 1
			if (data.get(1) != null) {
				values.add(data.get(1));
				colors = new int[] { Color.CYAN };
				PointStyle[] arrayOfPointStyle4 = new PointStyle[1];
				arrayOfPointStyle4[0] = PointStyle.DIAMOND;
				styles = arrayOfPointStyle4;
//				setData(this.date, 1, 6);
				if(MyApplication.isYearBtn){
					setData(date, 10, 60);
				}else{
					setData(date, 1, 6);
				}
				
			}

			break;
		case TAB_DISTANCE:// 2
			if (this.data.get(2) != null) {
				this.values.add(data.get(2));
				this.colors = new int[] { Color.MAGENTA };
				PointStyle[] arrayOfPointStyle3 = new PointStyle[1];
				arrayOfPointStyle3[0] = PointStyle.TRIANGLE;
				this.styles = arrayOfPointStyle3;
//				setData(this.date, 4, 80);
				if(MyApplication.isYearBtn){
					setData(date, 40, 800);
				}else{
					setData(date, 4, 80);
				}
			}

			break;
		case TAB_CALORIES:// 3
			if (this.data.get(3) != null) {
				this.values.add((double[]) this.data.get(3));
				this.colors = new int[] { Color.GREEN };
				PointStyle[] arrayOfPointStyle2 = new PointStyle[1];
				arrayOfPointStyle2[0] = PointStyle.SQUARE;
				this.styles = arrayOfPointStyle2;
//				setData(this.date, 100, 2000);
				if(MyApplication.isYearBtn){
					setData(date, 1000, 20000);
				}else{
					setData(date,100,2000);
				}
			}
			break;
		case TAB_SLEEP: // 4
			if (this.data.get(0) != null) {
				this.values.add(data.get(4));
				this.colors = new int[] { Color.BLUE };
				PointStyle[] arrayOfPointStyle1 = new PointStyle[1];
				arrayOfPointStyle1[0] = PointStyle.POINT;
				this.styles = arrayOfPointStyle1;
//				setData(this.date, 10, 40);
				if(MyApplication.isYearBtn){
					setData(date, 100, 400);
				}else{
					setData(date, 10, 40);
				}
			}
			break;
		}
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(25.0F);
		renderer.setYLabels(10);
		renderer.setLabelsTextSize(30.F);
		renderer.setShowGrid(true);
		renderer.setBackgroundColor(Color.TRANSPARENT);// 0
		renderer.setMarginsColor(Color.argb(0, 255, 0, 0));
		renderer.setYLabelsAlign(Paint.Align.RIGHT);
		renderer.setShowLegend(false);
		renderer.setZoomEnabled(true, false);

		return ChartFactory.getLineChartView(context,
				buildDataset(titles, x, values), renderer);
	}

	/**
	 * 区分年月周 分离数据
	 * 
	 * */
	private void splitObject() {
		// double[] arrayOfDouble1 = null;
		// double[] arrayOfDouble2 = null;
		// double[] arrayOfDouble3 = null;
		// double[] arrayOfDouble4 = null;
		// double[] arrayOfDouble5 = null;
		// double[] arrayOfDouble6 = null;
		if (date == ProgressActivity.TYPE_WEEK) {
			nullData = 7;
		} else if (date == ProgressActivity.TYPE_MONTH) {
			nullData = 31;
		} else if (date == ProgressActivity.TYPE_YEAR) {
			nullData = 12;
		}

		if ((!(mPedometer.equals(null))) && (!(mPedometer.equals("")))
				&& (mPedometer.size() != 0)) {

			double[] arrayOfDouble7 = new double[mPedometer.size()];
			double[] arrayOfDouble8 = new double[mPedometer.size()];
			double[] arrayOfDouble9 = new double[mPedometer.size()];
			double[] arrayOfDouble10 = new double[mPedometer.size()];
			double[] arrayOfDouble11 = new double[mPedometer.size()];
			double[] arrayOfDouble12 = new double[mPedometer.size()];

			if ((MyApplication.isYearBtn) && (mPedometer.size() > 12)) {
				double[] arrayOfDouble13 = new double[this.nullData];
				double[] arrayOfDouble14 = new double[this.nullData];
				double[] arrayOfDouble15 = new double[this.nullData];
				double[] arrayOfDouble16 = new double[this.nullData];
				double[] arrayOfDouble17 = new double[this.nullData];
				double[] arrayOfDouble18 = new double[this.nullData];
				for (int l = 0; l < nullData; l++) {
					arrayOfDouble13[l] = 0.0D;
					arrayOfDouble14[l] = 0.0D;
					arrayOfDouble15[l] = 0.0D;
					arrayOfDouble16[l] = 0.0D;
					arrayOfDouble17[l] = 0.0D;
					arrayOfDouble18[l] = 0.0D;
				}
				data.add(arrayOfDouble13);
				data.add(arrayOfDouble14);
				data.add(arrayOfDouble15);
				data.add(arrayOfDouble16);
				data.add(arrayOfDouble17);
				data.add(arrayOfDouble18);
			} else {
				int j = 0;
//				Log.i("APP", mPedometer.size() + "size");
				for (int k = 0; k < mPedometer.size(); k++) {

					j++;
					 arrayOfDouble7[k] = ((PedoMeter)
					 mPedometer.get(k)).steps;
//					arrayOfDouble7[k] = 3888;
//					Log.i("APP", "steps = " + arrayOfDouble7[k]);
					arrayOfDouble8[k] = new BigDecimal(
							((PedoMeter) mPedometer.get(k)).activityTtime / 60.0D)
							.setScale(2, 4).doubleValue();
					arrayOfDouble9[k] = (Math
							.round(100.0F * ((PedoMeter) mPedometer.get(k)).distance / 100.0F) / 100.0D);
					arrayOfDouble10[k] = (((PedoMeter) mPedometer.get(k)).calories / 100.0D);
					arrayOfDouble11[k] = (((PedoMeter) mPedometer.get(k)).sleepTime / 60.0D);
					arrayOfDouble12[k] = ((PedoMeter) mPedometer.get(k)).day;
				}
				data.add(arrayOfDouble7);
				data.add(arrayOfDouble8);
				data.add(arrayOfDouble9);
				data.add(arrayOfDouble10);
				data.add(arrayOfDouble11);
				data.add(arrayOfDouble12);
			}
		} else {
			double[] arrayOfDouble1 = new double[this.nullData];
			double[] arrayOfDouble2 = new double[this.nullData];
			double[] arrayOfDouble3 = new double[this.nullData];
			double[] arrayOfDouble4 = new double[this.nullData];
			double[] arrayOfDouble5 = new double[this.nullData];
			double[] arrayOfDouble6 = new double[this.nullData];
			for (int i = 0; i < nullData; i++) {
				arrayOfDouble1[i] = 0.0D;
				arrayOfDouble2[i] = 0.0D;
				arrayOfDouble3[i] = 0.0D;
				arrayOfDouble4[i] = 0.0D;
				arrayOfDouble5[i] = 0.0D;
				arrayOfDouble6[i] = 0.0D;
			}
			data.add(arrayOfDouble1);
			data.add(arrayOfDouble2);
			data.add(arrayOfDouble3);
			data.add(arrayOfDouble4);
			data.add(arrayOfDouble5);
			data.add(arrayOfDouble6);
		}

	}


	/**
	 * 根据年月日 来 b Y轴
	 * 
	 */

	private void setData(int a, int b, int c) {
		renderer = buildRenderer(colors, styles);
		int i = renderer.getSeriesRendererCount();
		for (int j = 0; j < i; j++) {
			switch (a) {
			case ProgressActivity.TYPE_WEEK:
				MyApplication.isYearBtn = false;
				this.titles = new String[] { "Week" };
				setChartSettings(this.renderer, this.title, " ", " ", 0.0D,
						7.5D, 0.0D, b, Color.LTGRAY, Color.GRAY);
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer5 = this.renderer;
				double[] arrayOfDouble4 = new double[4];
				arrayOfDouble4[0] = 0.0D;
				arrayOfDouble4[1] = 7.0D;
				arrayOfDouble4[2] = 0.0D;
				arrayOfDouble4[3] = c;
				localXYMultipleSeriesRenderer5.setPanLimits(arrayOfDouble4);
				this.renderer.setMargins(new int[] { 40, 100, 30, 10 });
				this.renderer.setXLabels(0);
				this.renderer.setPanEnabled(false, true);
				for (int i1 = 0; i1 < 7; i1++) {
					this.renderer.addXTextLabel(i1 + 1, this.xWeekLable[i1]);
				}
				this.x.add(new double[] { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D,
						7.0D });
				break;
			case ProgressActivity.TYPE_MONTH:
				MyApplication.isYearBtn = false;
				this.titles = new String[] { "Month" };
				setChartSettings(this.renderer, this.title, " ", " ", 0.0D,
						7.0D, 0.0D, b, Color.LTGRAY, Color.GRAY);
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer3 = this.renderer;
				double[] arrayOfDouble2 = new double[4];
				arrayOfDouble2[0] = 0.0D;
				arrayOfDouble2[1] = 32.0D;
				arrayOfDouble2[2] = 0.0D;
				arrayOfDouble2[3] = c;
				localXYMultipleSeriesRenderer3.setPanLimits(arrayOfDouble2);
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer4 = this.renderer;
				int[] arrayOfInt2 = new int[4];
				arrayOfInt2[0] = 40;
				arrayOfInt2[1] = 100;
				arrayOfInt2[2] = 30;
				localXYMultipleSeriesRenderer4.setMargins(arrayOfInt2);
				this.renderer.setPanEnabled(true, true);
				renderer.setXLabels(0);
				double[] arrayOfDouble3 = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D,
						7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D,
						15.0D, 16.0D, 17.0D, 18.0D, 19.0D, 20.0D, 21.0D, 22.0D,
						23.0D, 24.0D, 25.0D, 26.0D, 27.0D, 28.0D, 29.0D, 30.0D,
						31.0D };
				for (int l = 0; l < 31; l++) {
					renderer.addXTextLabel(l + 1,
							String.valueOf(arrayOfDouble3[l]));
				}
				this.x.add(arrayOfDouble3);
				break;
			case ProgressActivity.TYPE_YEAR:
				MyApplication.isYearBtn = true;
				this.titles = new String[] { "Year" };
				setChartSettings(this.renderer, this.title, " ", " ", 0.0D,
						7.0D, 0.0D, b, Color.LTGRAY, Color.GRAY);
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer1 = this.renderer;
				double[] arrayOfDouble1 = new double[4];
				arrayOfDouble1[0] = 0.0D;
				arrayOfDouble1[1] = 12.5D;
				arrayOfDouble1[2] = 0.0D;
				arrayOfDouble1[3] = c;
				localXYMultipleSeriesRenderer1.setPanLimits(arrayOfDouble1);
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer2 = this.renderer;
				int[] arrayOfInt1 = new int[4];
				arrayOfInt1[0] = 40;
				arrayOfInt1[1] = 100;
				arrayOfInt1[2] = 30;
				localXYMultipleSeriesRenderer2.setMargins(arrayOfInt1);
				this.renderer.setPanEnabled(true, true);
				this.renderer.setXLabels(0);
				for (int k = 0; k < 12; k++) {
					this.renderer.addXTextLabel(k + 1, this.xYearLable[k]);
				}
				this.x.add(new double[] { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D,
						7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D });

				break;
			}

			((XYSeriesRenderer) this.renderer.getSeriesRendererAt(j))
					.setFillPoints(true);
		}

	}
}
