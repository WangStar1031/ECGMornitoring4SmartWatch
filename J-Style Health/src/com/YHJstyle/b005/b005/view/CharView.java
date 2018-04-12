package com.YHJstyle.b005.b005.view;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.YHJstyle.b005.R;

/**
 * 抽象绘图
 * */
public abstract class CharView {
	/**
	 * 增加X Y 轴服务
	 * 
	 */
	public void addXYSeries(
			XYMultipleSeriesDataset paramXYMultipleSeriesDataset,
			String[] str, List<double[]> list1,
			List<double[]> list2, int position) {
		int i = str.length;
		int j = 0;
		if(j < i){
			XYSeries xySeries = new XYSeries(str[j], position);
			double[] d1 = list1.get(j);
			double[] d2 = list2.get(j);
			for(int m = 0;m<d2.length;m++){
				xySeries.add(d1[m], d2[m]);
			}
			paramXYMultipleSeriesDataset.addSeries(xySeries);
			j++;
		}
		
	}

	protected XYMultipleSeriesDataset buildBarDataset(
			String[] str, List<double[]> list) {
		XYMultipleSeriesDataset localXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
		int i = str.length;
		int j = 0;
		if(j < i){
			CategorySeries cSeries = new CategorySeries(str[j]);
			double[] d1 = list.get(j);
			for(int m = 0; m < d1.length; m++ ){
				cSeries.add(d1[m]);
			}
			localXYMultipleSeriesDataset.addSeries(cSeries.toXYSeries());
			j++;
		}
		
		
		
		
		return localXYMultipleSeriesDataset;
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] paramArrayOfInt) {
		XYMultipleSeriesRenderer localXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		localXYMultipleSeriesRenderer.setAxisTitleTextSize(16.0F);
		localXYMultipleSeriesRenderer.setChartTitleTextSize(20.0F);
		localXYMultipleSeriesRenderer.setLabelsTextSize(15.0F);
		localXYMultipleSeriesRenderer.setLegendTextSize(15.0F);
		for (int j = 0; j < paramArrayOfInt.length; j++) {
			SimpleSeriesRenderer localSimpleSeriesRenderer = new SimpleSeriesRenderer();
			localSimpleSeriesRenderer.setColor(paramArrayOfInt[j]);
			localXYMultipleSeriesRenderer
					.addSeriesRenderer(localSimpleSeriesRenderer);
		}
		return localXYMultipleSeriesRenderer;
	}

	protected CategorySeries buildCategoryDataset(String paramString,
			double[] paramArrayOfDouble) {
		CategorySeries localCategorySeries = new CategorySeries(paramString);
		int i = 0;
		int j = paramArrayOfDouble.length;
		for (int k = 0; k < j; k++) {

			double d = paramArrayOfDouble[k];
			StringBuilder localStringBuilder = new StringBuilder("Project ");
			localCategorySeries.add(String.valueOf(i++), d);
		}
		return localCategorySeries;
	}

	protected DefaultRenderer buildCategoryRenderer(int[] paramArrayOfInt) {
		DefaultRenderer localDefaultRenderer = new DefaultRenderer();
		localDefaultRenderer.setLabelsTextSize(15.0F);
		localDefaultRenderer.setLegendTextSize(15.0F);
		int[] arrayOfInt = new int[4];
		arrayOfInt[0] = 20;
		arrayOfInt[1] = 30;
		arrayOfInt[2] = 15;
		localDefaultRenderer.setMargins(arrayOfInt);
		for(int i = 0;i<paramArrayOfInt.length;i++) {

			int k = paramArrayOfInt[i];
			SimpleSeriesRenderer localSimpleSeriesRenderer = new SimpleSeriesRenderer();
			localSimpleSeriesRenderer.setColor(k);
			localDefaultRenderer.addSeriesRenderer(localSimpleSeriesRenderer);
		}
		return localDefaultRenderer;
	}

	protected XYMultipleSeriesDataset buildDataset(String[] paramArrayOfString,
			List<double[]> paramList1, List<double[]> paramList2) {
		XYMultipleSeriesDataset localXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
		addXYSeries(localXYMultipleSeriesDataset, paramArrayOfString,
				paramList1, paramList2, 0);
		return localXYMultipleSeriesDataset;
	}

	protected XYMultipleSeriesDataset buildDateDataset(
			String[] str, List<Date[]> list1,
			List<double[]> list2) {
		XYMultipleSeriesDataset localXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
		
		int i = str.length;
		int j = 0;
		if(j < i){
			TimeSeries tSeries  = new TimeSeries(str[j]);
			Date[] arr = list1.get(j);
			double[] doub = list2.get(j);
			for(int m = 0; m < arr.length; m++){
				tSeries.add(arr[m], doub[m]);
			}
			localXYMultipleSeriesDataset.addSeries(tSeries);
			j++;
		}
		
		return localXYMultipleSeriesDataset;
	}

	protected MultipleCategorySeries buildMultipleCategoryDataset(
			String paramString, List<String[]> paramList,
			List<double[]> paramList1) {
		MultipleCategorySeries localMultipleCategorySeries = new MultipleCategorySeries(
				paramString);
		int i = 0;
		Iterator localIterator = paramList1.iterator();
		while(localIterator.hasNext()){
			double[] d = (double[]) localIterator.next();
			localMultipleCategorySeries.add(paramList.get(i), paramList1.get(i));
			i++;
		}
		return localMultipleCategorySeries;
		
	}

	protected XYMultipleSeriesRenderer buildRenderer(int[] paramArrayOfInt,
			PointStyle[] paramArrayOfPointStyle) {
		XYMultipleSeriesRenderer localXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		setRenderer(localXYMultipleSeriesRenderer, paramArrayOfInt,
				paramArrayOfPointStyle);
		return localXYMultipleSeriesRenderer;
	}

	protected void setChartSettings(
			XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer,
			String paramString1, String paramString2, String paramString3,
			double paramDouble1, double paramDouble2, double paramDouble3,
			double paramDouble4, int paramInt1, int paramInt2) {
		paramXYMultipleSeriesRenderer.setChartTitle(paramString1);
		paramXYMultipleSeriesRenderer.setXTitle(paramString2);
		paramXYMultipleSeriesRenderer.setYTitle(paramString3);
		paramXYMultipleSeriesRenderer.setXAxisMin(paramDouble1);
		paramXYMultipleSeriesRenderer.setXAxisMax(paramDouble2);
		paramXYMultipleSeriesRenderer.setYAxisMin(paramDouble3);
		paramXYMultipleSeriesRenderer.setYAxisMax(paramDouble4);
		paramXYMultipleSeriesRenderer.setAxesColor(paramInt1);
		paramXYMultipleSeriesRenderer.setLabelsColor(paramInt2);
	}

	protected void setRenderer(
			XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer,
			int[] paramArrayOfInt, PointStyle[] paramArrayOfPointStyle) {
		paramXYMultipleSeriesRenderer.setAxisTitleTextSize(16.0F);
		paramXYMultipleSeriesRenderer.setChartTitleTextSize(20.0F);
		paramXYMultipleSeriesRenderer.setLabelsTextSize(15.0F);
		paramXYMultipleSeriesRenderer.setLegendTextSize(15.0F);
		paramXYMultipleSeriesRenderer.setPointSize(5.0F);
		paramXYMultipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 20 });
		int i = paramArrayOfInt.length;
		for (int j = 0; j < i; j++) {
			XYSeriesRenderer localXYSeriesRenderer = new XYSeriesRenderer();
			localXYSeriesRenderer.setColor(paramArrayOfInt[j]);
			localXYSeriesRenderer.setPointStyle(paramArrayOfPointStyle[j]);
			localXYSeriesRenderer.setLineWidth(4.0F);
			paramXYMultipleSeriesRenderer
					.addSeriesRenderer(localXYSeriesRenderer);
		}
	}

}
