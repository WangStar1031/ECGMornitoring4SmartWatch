package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Arrays;

import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import android.util.Log;

/**
 * 保存数据
 * */
public class SaveDataBase {
	// 得到某年某月某日的数据
	public static final String GET_YEAR_MONTH_DAY = "pedometer_year =? AND pedometer_month =? AND pedometer_day =?";
	private static final String TAG = "SavaeDataBase";
	private static byte[] b = null;

	// 读取输出流 转换成byte
	public static byte[] getByte(Object object) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
					localByteArrayOutputStream);
			localObjectOutputStream.writeObject(object);
			b = localByteArrayOutputStream.toByteArray();
			localObjectOutputStream.close();
			localByteArrayOutputStream.close();

		} catch (IOException e) {
			// Log.i("APP", "IOException" + e);
		}
		return b;
	}

	/**
	 * 获取输入 转换成byte[]
	 * 
	 * */
	public static Object getObject(byte[] arrayOfByte, Object object) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				arrayOfByte);

		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			object = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}


	/**
	 * 保存计步器信息(syncThread 67)
	 * */
	public static void savePedometerInfo(Context paramContext,
			PedoMeter paramPedoMeter) {
		SaveDateBaseHelp saveDateBaseHelp = null;
		SQLiteDatabase sqLiteDatabase = null;

		try {
			saveDateBaseHelp = new SaveDateBaseHelp(paramContext);
			sqLiteDatabase = saveDateBaseHelp.getWritableDatabase();
			sqLiteDatabase.beginTransaction();
			insertPdeometerInfo(paramContext, sqLiteDatabase, paramPedoMeter);
			// Log.i("APP", "进入数据储存");
			sqLiteDatabase.setTransactionSuccessful();
			sqLiteDatabase.endTransaction();

		} catch (Exception e) {
			// Log.e("SaveDataBase", "Save data failes!!!");
			e.printStackTrace();
		} finally {

			sqLiteDatabase.close();
			saveDateBaseHelp.close();
		}
	}

	/**
	 * 具体某年某日 获取计步器的信息
	 * */
	public static PedoMeter getPeodeterInfo(Context context, int i, int j, int k) {
		byte[] arrayOfByte = null;
		SQLiteDatabase database = new SaveDateBaseHelp(context)
				.getWritableDatabase();
		String[] array = new String[3];
		array[0] = String.valueOf(i);
		array[1] = String.valueOf(j);
		array[2] = String.valueOf(k);
		Cursor cursor = database.query("db_pedometer_info", null,
				"pedometer_year =?AND pedometer_month =?AND pedometer_day =?",
				array, null, null, null);
		PedoMeter pedoMeter = new PedoMeter();
		PedoMeter[] arrayOfPedoMeter = new PedoMeter[cursor.getCount()];
//		Log.i("time", "pedometer数组长度=="+cursor.getCount());
		if ((cursor != null) && (cursor.getColumnCount() != 0)
				&& (cursor.getCount() != 0)) {
			// 移动光标到第一行
			cursor.moveToFirst();
			if (cursor.moveToFirst()) {
				arrayOfPedoMeter[0] = new PedoMeter();
				arrayOfByte = cursor.getBlob(cursor.getColumnIndex("obj"));
//				Log.i("time", Arrays.toString(arrayOfByte));
			}
			do {
				pedoMeter = (PedoMeter) getObject(arrayOfByte, pedoMeter);
				// Log.v("SaveDataBase", "house == " + pedoMeter.steps + "  "
				// + pedoMeter.distance + " " + pedoMeter.calories);

			} while (cursor.moveToNext());
			// Log.v("SaveDataBase", "house == " + pedoMeter.steps + "  "
			// + pedoMeter.distance + " " + pedoMeter.calories);
			String[] arrayOfString2;
			arrayOfString2 = new String[3];
			arrayOfString2[0] = String.valueOf(i);
			arrayOfString2[1] = String.valueOf(j);
			arrayOfString2[2] = String.valueOf(k);

			cursor = database
					.query("db_pedometer_info",
							null,
							"pedometer_year =? AND pedometer_month =? AND pedometer_day =? ",
							arrayOfString2, null, null, null);
		}

		cursor.close();
		database.close();

		return pedoMeter;
	}

	// 得到保存的数据
	public static String getSaveDate(Context context, String str1, String str2) {
		return context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
				.getString(str1, str2);
	}

	/*
	 * 插入计步器信息 *
	 */
	public static void insertPdeometerInfo(Context context,
			SQLiteDatabase dataBase, PedoMeter pedoMeter) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("pedometer_year", Integer.valueOf(pedoMeter.year));
		contentValues.put("pedometer_month", Integer.valueOf(pedoMeter.month));
		contentValues.put("pedometer_day", Integer.valueOf(pedoMeter.day));
		contentValues.put("obj", getByte(pedoMeter));
		dataBase.insertWithOnConflict("db_pedometer_info", null, contentValues,
				SQLiteDatabase.CONFLICT_REPLACE);// 5
		saveUserInfo(context, "date", CalendarUtil.getFormatDateTime(
				CalendarUtil.getDateBeforeOrAfter(MyApplication.saveDataTimes),
				"yyyy-MM-dd"));
		// Log.i("APP",
		// "同步时间："
		// + CalendarUtil.getFormatDateTime(
		// CalendarUtil
		// .getDateBeforeOrAfter(MyApplication.saveDataTimes),
		// "yyyy-MM-dd"));
	}

	public static void saveUserInfo(Context context, String str1, String str2) {
		Editor editor = context.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE).edit();
		editor.putString(str1, str2);
		editor.commit();
	}

	public static class SaveDateBaseHelp extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "db_yh";
		private static final int DATABASE_VERSION = 1;
		public static final String TABLE_NAME_SAVE_DATA = "db_pedometer_info";

		public SaveDateBaseHelp(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Log.i("APP", "Create tables....");
			createTableSavePedometer(db);
		}

		private void createTableSavePedometer(SQLiteDatabase db) {
			// db.execSQL("CREATE TABLE db_pedometer_info(_id INTEGER PAIMARY KEY AUTOINCREMENT,pedometer_year INTEGERY, pedometer_month INTEGERY,pedometer_day INTEGERY,obj BLOB);");
			db.execSQL("CREATE TABLE db_pedometer_info(_id INTEGER PRIMARY KEY AUTOINCREMENT,pedometer_year INTEGERY,pedometer_month INTEGERY,pedometer_day INTEGERY,obj BLOB);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
}
