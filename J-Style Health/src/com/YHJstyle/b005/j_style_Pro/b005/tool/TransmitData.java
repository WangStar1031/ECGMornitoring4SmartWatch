package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;

import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;

import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
//import android.util.Log;

/**
 * 传送数据
 * */
public class TransmitData {
	public static final byte ACTIVITY_INFOMATION = 7;
	public static final byte GET_TARGET_NUMBER_OF_STEPS = 75;
	public static final byte GET_TIME = 65;
	public static final byte GET_USER_INFO = 66;
	public static final byte SET_CLEAR_DATA = 4;
	public static final byte SET_CLOCK_VALUE = 35;
	public static final byte SET_DEVICE_ID = 61;
	public static final byte SET_DEVICE_ID_START = 46;
	public static final byte SET_FACTORY_VALUE = 18;
	public static final byte SET_FATIGUE_VALUE = 37;
	// public static final byte SET_GOALD = 11;
	public static final byte SET_TARGET_NUMBER_OF_STEPS = 11;
	public static final byte SET_TIME = 1;
	public static final byte SET_TIME_ONE = 55;
	public static final byte SET_TIME_INFO = 2;
	public static final byte SLEPT_INFORMATION = 67;
	public static final byte START_TIME_PEDOMETER = 9;
	public static final byte STOP_TIME_PEDOMETER = 10;
	public static final byte TARGET_ACHIEVEMENT_RATE = 8;
	public static final byte CURRENT_SPORT_INFORMATION = 72;// 当前运动信息

	public static final byte HEART_RATE = (byte) 99;// 153

	public static char[] devideID;
	public static int setDataGoald;
	public static ArrayList<String> userInfo = new ArrayList();

	static {
		setDataGoald = 0;
	}

	// public static byte[] readSomeDate(byte var0,byte var1){
	// byte[] var2;
	// byte var3;
	// var2 = new byte[16];
	// var3 = var2[0];
	// labe133:
	// switch (var0) {
	// case ACTIVITY_INFOMATION://读取某天总运动信息7
	// var2[1] = var1;
	// int var6= 0;
	// while(true){
	// if(var6 >= 14){
	// break labe133;
	// }
	// var2[var6 +2] = 0;
	// var3 += var2[var6 +1];
	// ++var6;
	// }
	//
	//
	// case TARGET_ACHIEVEMENT_RATE://读取某天总运动信息8
	// var2[1] = var1;
	// int var7 = 0;
	// while(true){
	// if(var7 >= 14){
	// break labe133;
	// }
	// var2[var7 +2] = 0;
	// var3 += var2[var7 + 1];
	// ++var7;
	// }
	// case SLEPT_INFORMATION:// 取得当天详细运动信息 67
	// for(int var4 = 0;var4 <6; ++var4){
	// var2[var4 + 1] = 0;
	// }
	// for(int var5 = 0;var5 < 14; ++var5){
	// var2[var5 +2] = 0;
	// var3 += var2[var5 + 1];
	// }
	// }
	//
	// var2[15] = (byte)(var3 & 255);
	// return var2;
	// }

	public static byte[] readSomeData(byte byte1, byte byte2) {
		byte[] arr = new byte[16];
		arr[0] = byte1;
		int i = arr[0];
		switch (byte1) {
		case 8:
			arr[1] = byte2;
			for (int l = 0; l < 14; l++) {
				arr[(l + 2)] = 0;
				i = (byte) (i + arr[(l + 1)]);
			}
			break;
		case 7:
			arr[1] = byte2;
			for (int m = 0; m < 14; m++) {
				arr[(m + 2)] = 0;
				i = (byte) (i + arr[(m + 1)]);
			}
			break;
		case 67:
			arr[1] = byte2;
			for (int n = 0; n < 14; n++) {
				arr[(n + 2)] = 0;
				i = (byte) (i + arr[(n + 1)]);
			}
			break;

		default:
			break;
		}
		arr[15] = (byte) (i & 0xFF);
		return arr;
	}

	public static byte[] sendDate(byte var0) {
		byte[] var1 = new byte[16];
		var1[0] = var0;
		byte var2 = var1[0];
		labe123: switch ((byte) var0) {
		case HEART_RATE:// 心率99

			int var99 = 0;
			while (true) {
				if (var99 >= 15) {
					break labe123;
				}
				var1[var99 + 1] = 0;
				var2 += var1[var99 + 1];
				++var99;
			}

		}
		var1[15] = (byte) var2;
//		Log.i("APP", "var1=" + Arrays.toString(var1));
		return var1;
	}

	public static byte[] sendDate(int var0) {
		byte[] var1 = new byte[16];
		var1[0] = (byte) var0;
		int var2 = var1[0];
		labe122: switch (var0) {
		
			
		
		case SET_TIME:// 设置时间1
			int[] var = CalendarUtil.getTime();
			for (int i = 0; i < 6; i++) {
				var1[i + 1] = (byte) (var[i] + 6 * (var[i] / 10));
			}

			int var10 = 0;
			while (true) {
				if (var10 >= 15) {
					break labe122;
				}
				var2 += var1[var10 + 1];
				++var10;
			}
		case SET_TIME_INFO:// 设置用户信息2
			if (userInfo != null) {
				var1[1] = ((byte) Integer.parseInt((String) userInfo.get(4)));
				var1[2] = ((byte) Integer.parseInt((String) userInfo.get(3)));
				var1[3] = ((byte) Integer.parseInt((String) userInfo.get(0)));
//				Log.i("time","var1[3]=="+Integer.parseInt((String) userInfo.get(0)));
				var1[4] = ((byte) Integer.parseInt((String) userInfo.get(1)));
				var1[5] = ((byte) Integer.parseInt((String) userInfo.get(2)));

			}
			userInfo.clear();
			for (int var5 = 6; var5 < 15; ++var5) {
				var1[var5] = 0;
			}
			int var6 = 0;
			while (true) {
				if (var6 >= 15) {
					break labe122;
				}
				var2 += var1[var6 + 1];
				++var6;
			}

		case 5:// 设置设备
			for (int var3 = 0; var3 < 6; ++var3) {
				var1[var3 + 1] = (byte) devideID[var3];
			}
			int var4 = 0;
			while (true) {
				if (var4 >= 15) {
					break labe122;
				}
				var2 += var1[var4 + 1];
				++var4;
			}
			// case 7: //读取某天总运动信息
			// int var7 = 0;
			// while(true){
			// if(var7 >=15){
			//
			// break labe122;
			// }
			// var2 += var1[var7 + 1 ];
			// ++var7;
			// }

			// case TARGET_ACHIEVEMENT_RATE://读取某天目标达成率
			// int var11 = 0;
			// while(true){
			// if(var11 >= 15){
			// break labe122;
			// }
			// var1[var11 + 1] = 0;
			// var2 += var1[var11 +1];
			// ++var11;
			// }
		case START_TIME_PEDOMETER:// 启动实时计步9
			int var19 = 0;
			while (true) {
				if (var19 >= 15) {
					break labe122;
				}
				var1[var19 + 1] = 0;
				var2 += var1[var19 + 1];
				++var19;
			}
		case 153://发送心率指令
			int var153 = 0;
			while(true){
				if(var153 >= 15){
					break labe122;
				}
				var1[var153 + 1] = 0;
				var2 += var1[var153 + 1];
				++var153;
			}
		case  152:
			int var98 = 0;
			while(true){
				if(var98 >=15){
					break labe122;
				}
				var1[var98 + 1] = 0;
				var2 += var1[var98+1];
				++var98;
			}
//			Log.i("APP", "-----到这里152-----");
		case STOP_TIME_PEDOMETER:// 停止实时计步 10
			int var18 = 0;
			while (true) {
				if (var18 >= 15) {
					break labe122;
				}
				var1[var18 + 1] = 0;
				var2 += var1[var18 + 1];
				++var18;
			}
		case SET_TARGET_NUMBER_OF_STEPS: // 向计步器写入目标步数11
			// 命令格式： 0x0B AA BB CC DD EE FF 00 00 00 00 00 00 00 00 CRC
			// 功能： 向计步器写入目标步数
			// 描述： AA BB CC为个人目标步数值，3字节，高字节在前
			// DD EE FF为公司目标步数值，3字节，高字节在前（仅限于WalkingSpree计步器）
			int i7 = setDataGoald / 65536;
			int i8,
			i9;
			if (i7 > 0) {
				i8 = (setDataGoald - 16 * (16 * (16 * (i7 * 16)))) / 256;
				i9 = setDataGoald - 16 * (16 * (16 * (i7 * 16))) - 16
						* (i8 * 16);
			} else {
				i8 = setDataGoald / 256;
				i9 = setDataGoald - 16 * (i8 * 16);
			}

			var1[1] = (byte) i7;
			var1[2] = (byte) i8;
			var1[3] = (byte) i9;

			int var16 = 0;
			while (true) {
				if (var16 >= 15) {
					break labe122;
				}
				var2 += var1[var16 + 1];
				++var16;
			}
		case GET_USER_INFO:// 取得用户个人信息
			int var66 = 0;
//			Log.i("APP", "个人信息66");
			while (true) {
				if (var66 >= 15) {
					break labe122;
				}
				var1[var66 + 1] = 0;
				var2 += var1[var66 + 1];
				++var66;
			}
		case SET_DEVICE_ID:// 设置蓝牙设备名称61
			for (int m = 0; m < devideID.length; m++) {
				var1[m+1] = (byte) devideID[m];
			}
				int var61 = 0;
				while(true){
					if(var61 >= 15){
						break labe122;
					}
					var2 += var1[var61 + 1];
					++var61;
				}

		case GET_TARGET_NUMBER_OF_STEPS:// 手机从设备中读取目标步数75
			int var17 = 0;
			while (true) {
				if (var17 >= 15) {
					break labe122;
				}
				var1[var17 + 1] = 0;
				var2 += var1[var17 + 1];
				++var17;
			}
//		case 37:
		case CURRENT_SPORT_INFORMATION:// 72
			int var72 = 0;
			while (true) {
				if (var72 >= 15) {
					break labe122;
				}
				var1[var72 + 1] = 0;
				var2 += var1[var72 + 1];
				++var72;
			}
			
		}

		var1[15] = (byte) (var2 & 255);

		return var1;
	}
}
