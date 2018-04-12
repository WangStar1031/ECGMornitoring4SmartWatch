package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.task.NewAgreementBackgroundThreadManager;
import com.YHJstyle.b005.j_style_Pro.b005.task.WriteOneDataTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

//import android.util.Log;

public class ResolveData {
	private static boolean bBreak = false;
	private static ArrayList<String> data = new ArrayList();
	private static Thread mThread;

	private static ArrayList<Byte> heart = new ArrayList();
	private static int nCurDataCount = 0;// 当前返回的数据条数
	private static int nLastDataCount = 0;
	private static int sleepDataLength = 0;// 睡眠时间数据
	public static byte[] dataBuffer = new byte[16];// 心电数据
	public static byte[] sendDate = new byte[14];

	/**
	 * 编译接收回来的数据
	 * */

	public static void DecodeRecvData(byte[] arrayByte) {
		nCurDataCount = 1 + nCurDataCount;
		ArrayList arrayList = new ArrayList();
		int i = arrayByte.length;
		int j = 0;
		for (int k = 0; k < i; k++) {
			arrayList.add(Byte.valueOf(arrayByte[k]));
			if (k < i - 1) {
				j = (byte) (j + arrayByte[k]);
			}
		}
		data.clear();
		if (i >= 16 && (0xFF & arrayByte[i - 1]) == (j & 0xFF)) {
			switch (arrayByte[0]) {

			case (byte) 0xA9:

				Intent intent = new Intent("com.youhong.ACTION_DATA_AVAILABLE");
				intent.putExtra("com.youhong.EXTRA.DATA", arrayByte);
				MyApplication.getInstance().sendBroadcast(intent);
				// for(int sendB = 0;sendB <= 10;sendB++){
				// Log.i("Test", sendB++ +"-sendb-"+Arrays.toString(arrayByte));
				// }
				// Bundle bundle99 = new Bundle();
				// Message message99 =
				// Message.obtain(BluetoothLeService.mActivityHandler, 99);
				// // data.add(arrayByte[1]);
				break;
			case TransmitData.SET_TIME_INFO:// 设置个人信息2
				Bundle bundle8 = new Bundle();
				Message message8 = Message.obtain(
						BluetoothLeService.mActivityHandler, 9);
				data.add("ok");
				bundle8.putStringArrayList("SET_USER_INFO", data);
				message8.setData(bundle8);
				message8.sendToTarget();
				break;
			case TransmitData.SET_TIME:// 设置时间1
				Bundle bundle7 = new Bundle();
				Message message7 = Message.obtain(
						BluetoothLeService.mActivityHandler, 10);
				data.add("ok");
				bundle7.putStringArrayList("SET_TIME", data);
				message7.setData(bundle7);
				message7.sendToTarget();
				break;
			case TransmitData.START_TIME_PEDOMETER:// 9读取同步数据
				Bundle bundle9 = new Bundle();
				Message message9 = Message.obtain(
						BluetoothLeService.mActivityHandler, 4);
				if (((arrayByte[1] >> 7) & 0x1) == 1) {

					data.add("");
				} else {

					data.add(String.valueOf(256 * (256 * (0xFF & arrayByte[1]))
							+ 256 * (0xFF & arrayByte[2])
							+ (0xFF & arrayByte[3])));
				}
				data.add(String.valueOf(256 * (256 * (0xFF & arrayByte[7]))
						+ 256 * (0xFF & arrayByte[8]) + (0xFF & arrayByte[9])));
				data.add(String
						.valueOf(256 * (256 * (0xFF & arrayByte[10])) + 256
								* (0xFF & arrayByte[11])
								+ (0xFF & arrayByte[12])));
				data.add(String.valueOf(256 * (0xFF & arrayByte[13])
						+ (0xFF & arrayByte[14])));
				if (((arrayByte[1] >> 7) & 0x1) == 1)
					data.add(String.valueOf(+256 * (0xFF & arrayByte[2])
							+ (0xFF & arrayByte[3])));
				else
					data.add("");
				bundle9.putStringArrayList("HOME_TIMES", data);
				// BluetoothLeService.mActivityHandler.removeMessages(9);
				// BluetoothLeService.mActivityHandler.sendEmptyMessageAtTime(9,
				// 100l);
				message9.setData(bundle9);
				message9.sendToTarget();
				break;
			case 10:
				Bundle bundle10 = new Bundle();
				Message message10 = Message.obtain(
						BluetoothLeService.mActivityHandler, 101);
				data.add("0");
				data.add("0.0");
				data.add("0.00");
				data.add("00:00");
				data.add("0000");
				bundle10.putStringArrayList("END_TIMES", data);
				message10.setData(bundle10);
				message10.sendToTarget();
				break;
			case 5:// 设置设备ID码
				break;
			case TransmitData.SET_CLEAR_DATA:// 删除某天的运动信息4
				Bundle bundle2 = new Bundle();
				Message message2 = Message.obtain(
						BluetoothLeService.mActivityHandler, 16);
				data.add("OK");
				bundle2.putStringArrayList("SET_CLEAR_DATA", data);
				message2.setData(bundle2);
				message2.sendToTarget();
				break;
			case 61:// 设置蓝牙设备
				Bundle bundle6 = new Bundle();
				Message message6 = Message.obtain(
						BluetoothLeService.mActivityHandler, 11);
				data.add("ok");
				bundle6.putStringArrayList("SET_DEVICE", data);
				message6.setData(bundle6);
				message6.sendToTarget();
				break;
			case 75:// 手机从设备中读取目标步数
				Bundle bundle11 = new Bundle(); // SETTING_GOALD
				Message message11 = Message.obtain(
						BluetoothLeService.mActivityHandler, 7);
				data.add(String.valueOf(256 * (256 * (0XFF & arrayByte[1]))
						+ 256 * (0xFF & arrayByte[2]) + (0xFF & arrayByte[3])));
				// Log.i("APP", "Setting_goald " + data.toString());
				bundle11.putStringArrayList("SETTING_GOALD", data);
				message11.setData(bundle11);
				message11.sendToTarget();
				// Log.i("APP", "75");
				break;

			case 66:// 得到用户的信息
				// Log.i("APP", "USERINFORMATION ==");
				String[] arrayString = new String[6];
				Bundle bundle12 = new Bundle();
				Message message12 = Message.obtain(
						BluetoothLeService.mActivityHandler, 8);
				for (int m = 0; m < 5; m++) {
					arrayString[m] = String.valueOf(0xFF & arrayByte[(m + 1)]);
				}
				arrayString[5] = (String
						.valueOf(256 * (256 * (256 * (256 * (256 * (0xFF & arrayByte[6]))))))
						+ String.valueOf(256 * (256 * (256 * (256 * (0xFF & arrayByte[7])))))
						+ String.valueOf(256 * (256 * (256 * (0xFF & arrayByte[8]))))
						+ String.valueOf(256 * (256 * (0xFF & arrayByte[9])))
						+ String.valueOf(256 * (0xFF & arrayByte[10])) + String
						.valueOf(0xFF & arrayByte[11]));
				// Log.i("APP", "USERINFORMATION ==" + arrayString.toString());
				bundle12.putStringArray("GET_USER_INFO", arrayString);
				message12.setData(bundle12);
				message12.sendToTarget();

				break;
			case 37://
				Bundle bundle14 = new Bundle();
				Message message14 = Message.obtain(
						BluetoothLeService.mActivityHandler, 13);
				data.add("ok");
				bundle14.putStringArrayList("SET_FATIGUE", data);
				message14.setData(bundle14);
				message14.sendToTarget();
				break;
			case 35:
				Bundle bundle15 = new Bundle();
				Message message15 = Message.obtain(
						BluetoothLeService.mActivityHandler, 14);
				data.add("ok");
				bundle15.putStringArrayList("SET_CLOCK", data);
				message15.setData(bundle15);
				message15.sendToTarget();
				break;
			case 18://
				Bundle bundle16 = new Bundle();
				Message message16 = Message.obtain(
						BluetoothLeService.mActivityHandler, 15);
				data.add("ok");
				bundle16.putStringArrayList("SET_FACTORY", data);
				message16.setData(bundle16);
				message16.sendToTarget();
				break;
			case 72://
				// Log.i("APP", "jinru  72");
				Bundle bundle72 = new Bundle();
				Message message72 = Message.obtain(
						BluetoothLeService.mActivityHandler, 4);
				data.add(String.valueOf(256 * (256 * (0xFF & arrayByte[1]))
						+ 256 * (0xFF & arrayByte[2]) + (0xFF & arrayByte[3])));
				data.add(String.valueOf(256 * (256 * (0xFF & arrayByte[7]))
						+ 256 * (0xFF & arrayByte[8]) + (0xFF & arrayByte[9])));
				data.add(String
						.valueOf(256 * (256 * (0xFF & arrayByte[10])) + 256
								* (0xFF & arrayByte[11])
								+ (0xFF & arrayByte[12])));
				data.add(String.valueOf(256 * (0xFF & arrayByte[13])
						+ (0xFF & arrayByte[14])));
				// Log.i("APP", "data.toString" + data.toString());
				bundle72.putStringArrayList("HOME_TIMES", data);
				message72.setData(bundle72);
				message72.sendToTarget();
				break;
			case 8:// 读取某一天的目标达成率
					// int m1 = 0;
					// Bundle bundle88 = new Bundle();
					// Message message88 = Message.obtain(
					// BluetoothLeService.mActivityHandler, 6);
					// int i27 = arrayByte[2] - (6 * arrayByte[2] / 16);
					// int i28 = arrayByte[3] - (6 * arrayByte[3] / 16);
					// int i29 = arrayByte[4] - (6 * arrayByte[4] / 16);
					// // 年月日全部为0时 目标达成率为空
					// if ((i27 != 0) || (i28 != 0) || (i29 != 0)) {
					// String[] arr = CalendarUtil
					// .getFormatDateTime(
					// CalendarUtil
					// .getDateBeforeOrAfter(MyApplication.saveDataTimes),
					// "yyyy-MM-dd").split("-");
					// int i30 = Integer.valueOf(arr[0]).intValue();
					// int i31 = Integer.valueOf(arr[1]).intValue();
					// int i32 = Integer.valueOf(arr[2]).intValue();
					// int i33 = 0XFF & arrayByte[5];
					// // int i33 = 256 * (0xFF & arrayByte[13]) + (0xFF &
					// arrayByte[14]);
					// PedoMeter pedoMeter = SaveDataBase.getPeodeterInfo(
					// MyApplication.getInstance(), i30, i31, i32);
					// if (pedoMeter == null) {
					// pedoMeter = new PedoMeter();
					// }
					// pedoMeter.year = i30;
					// pedoMeter.month = i31;
					// pedoMeter.day = i32;
					// pedoMeter.dailyGoal = i33;
					// Log.i("APP", "GOALD" + pedoMeter.dailyGoal);
					// SaveDataBase.savePedometerInfo(MyApplication.getInstance(),
					// pedoMeter);
					//
					// }
					// MyApplication.saveDataTimes = -1 +
					// MyApplication.saveDataTimes;
					// Log.i("======", "ACTIVITY_INFORMATION == "
					// + MyApplication.saveDataTimes);
					// if (MyApplication.saveDataTimes >=0) {
					// m1 = 209;
					// syncDataThread((byte) 67);
					// } else {
					// m1 = 109;
					// syncThreadStop();
					// }
					//
					// bundle88.putInt("HOME_GOALD", m1);
					// message88.setData(bundle88);
					// message88.sendToTarget();

				break;
			case 7://

				// Log.i("APP", "运行到这里7");
				// CalendarUtil.setSavaDateAndId();
				Bundle bundle17 = new Bundle();
				Message message17 = Message.obtain(
						BluetoothLeService.mActivityHandler, 6);
				int p = 0;
				if ((0xFF & arrayByte[1]) == 0) {

					MyApplication.getInstance().mPedoMeter.steps = (256 * 256
							* (0xFF & arrayByte[6]) + 256
							* (0xFF & arrayByte[7]) + (0xFF & arrayByte[8]));
					double d2 = new BigDecimal(
							MyApplication.getInstance().mPedoMeter.steps / 1000.0D)
							.setScale(2, 4).doubleValue();
					MyApplication.getInstance().mPedoMeter.fuel = (int) d2;
					MyApplication.getInstance().mPedoMeter.calories = (256
							* 256 * (0xFF & arrayByte[12]) + 256
							* (0xFF & arrayByte[13]) + (0xFF & arrayByte[14]));
				} else {
					int m = (0xFF) & arrayByte[1];
					if (m == 1) {
						MyApplication.getInstance().mPedoMeter.distance = (256
								* 256 * (0xFF & arrayByte[6]) + 256
								* (0xFF & arrayByte[7]) + (0xFF & arrayByte[8]));
						double d1 = 256 * (0xFF & arrayByte[9])
								+ (0xFF & arrayByte[10]);
						MyApplication.getInstance().mPedoMeter.activityTtime = (int) (Math
								.round(100.0D * d1) / 100.0D);
						MyApplication.getInstance().mPedoMeter.dailyGoal = (MyApplication
								.getInstance().mPedoMeter.steps / 100);
						if (MyApplication.getInstance().mPedoMeter.dailyGoal > 100) {

							MyApplication.getInstance().mPedoMeter.dailyGoal = 100;
						}

					}
					CalendarUtil.setSavaDateAndId();

					SaveDataBase.savePedometerInfo(MyApplication.getInstance(),
							MyApplication.getInstance().mPedoMeter);
					MyApplication.getInstance().mPedoMeter.sleepTime = 0;
					MyApplication.saveDataTimes += -1;
					// Log.i("======", "ACTIVITY_INFORMATION == "
					// + MyApplication.saveDataTimes);
					if (MyApplication.saveDataTimes >= 0) {
						p = 209;
//						syncDataThread((byte) 67);
						WriteOneDataTask task = new WriteOneDataTask((byte)67);
						NewAgreementBackgroundThreadManager.getInstance().addTask(task);
					} else {
						p = 109;
						syncThreadStop();
					}
					bundle17.putInt("HOME_GOALD", p);
					message17.setData(bundle17);
					message17.sendToTarget();
				}

				break;
			case 67:// 只为得到睡眠数据段
				// Log.i("APP", "运行到这里67");
				if ((0xFF & arrayByte[1]) == 240) {
					if ((0xFF & arrayByte[6]) != 0) {
						int n = 0;
						for (int m = 0; m < 8; m++) {
							n += 0xFF & arrayByte[m + 7];
						}
						MyApplication.getInstance().mPedoMeter.slept[(0xFF & arrayByte[5])] = n / 8;
						if (n / 8 == 0) {
//							MyApplication.getInstance().mPedoMeter.slept[(0xFF & arrayByte[5])] = 1;
							MyApplication.getInstance().mPedoMeter.slept[(0xFF & arrayByte[5])] = -1;
						}
						 if (n != 0) {
						PedoMeter pedoMeter = MyApplication.getInstance().mPedoMeter;
						pedoMeter.sleepTime += 15;
						 }
					}
					if ((255 & arrayByte[5]) == 95) {// 255 & arrayByte[5]) == 95
						// Log.e("APP", "运行到这里95");
//						syncDataThread((byte) 7);
						WriteOneDataTask task = new WriteOneDataTask((byte)7);
						NewAgreementBackgroundThreadManager.getInstance().addTask(task);
					}
				} else if ((0xFF & arrayByte[1]) == 255) {
//					syncDataThread((byte) 7);
					WriteOneDataTask task = new WriteOneDataTask((byte)7);
					NewAgreementBackgroundThreadManager.getInstance().addTask(task);
				}

				break;

			default:
				break;
			}
		}

	}

	/**
	 * 同步数据 启动一线程
	 * */

	public static void syncDataThread(final byte paramByte) {
		bBreak = true;
		nCurDataCount = 0;
		if (mThread != null) {
			mThread.interrupt();
		}
		mThread = new Thread() {
			public void run() {
				if (paramByte == (byte) 67) {
					MyApplication.getInstance().mPedoMeter.slept = new int[96];
				}
				MyApplication.getInstance().mService.WriteSomedayData(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND,
						MyApplication.mDevice, paramByte,
						(byte) MyApplication.saveDataTimes);
				// Log.i("APP", "指令编号：" + paramByte + "几天前的数据："
				// + MyApplication.saveDataTimes);
			}
		};

		mThread.start();
	}

	/**
	 * 停止数据同步
	 * */
	public static void syncThreadStop() {
		if (mThread != null) {
			mThread.interrupt();
		}
		bBreak = true;
		mThread = null;
	}
}
