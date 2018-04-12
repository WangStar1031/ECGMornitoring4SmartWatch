package com.YHJstyle.b005.j_style_Pro.b005.tool;

/**
 * 时间处理工具
 * */
public class TimeUtil {
	
	public static byte remindTime(String str){
		switch (Integer.parseInt(str)) {
		case 0:
			
			return 0;
		case 10:
			return 10;
		case 20:
			return 20;
		case 30:
			return 30;
		}
		return (byte) (Integer.parseInt(str));
	}
	
	public static int remindWeek(String str){
		int i = 0;
		int j = 0;
		String[] str1 = str.split("\t");
		for(int k =0; k<str1.length;k++){
			if(str1[k].equals("周一")){
				i = 2;
			}
			if(str1[k].equals("周二")){
				i = 4;
			}
			if(str1[k].equals("周三")){
				i = 8;
			}
			if(str1[k].equals("周四")){
				i = 16;
			}
			if(str1[k].equals("周五")){
				i = 32;
			}
			if(str1[k].equals("周六")){
				i = 64;
			}
			if(str1[k].equals("周日")){
				i = 128;
			}
			
			i += j;
			j = i;
		}
		return i;
	}
	
	/**
	 * 转换成时间
	 * */
	public static int[] splitTime(String str){
		int[] arr = new int[2];
		String[] str1 = str.split(":");
		for(int i = 0;i<str1.length;i++){
			arr[i] = Integer.parseInt(str1[i]);
		}
		
		return arr;
	}
	
	
	public static String[] splitTimeStr(String str){
		String[] str1  = new String[2];
		String[] str2 = str.split(":");
		for(int i = 0; i<str2.length;i++){
			str1[i]  = String.valueOf(str2[i]);
		}
		return str1;
	}

	public static String sportGoalToHex(int number) {
		char[] char1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99,
				100, 101, 102 };
		char[] char2 = new char[8];
		String str = "";
		int i = char2.length;
		if (number != 0) {
			for (int k = i; k < char2.length; k++) {
				int j = number & 0xF;
				i--;
				char2[i] = char1[j];
				number >>>= 4;
				str = str + char2[k];
			}
		}
		return "(byte)0x" + str;
	}

	/**
	 * 转换陈十六进制
	 * */
	public static String toHex(int number) {
		char[] char1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99,
				100, 101, 102 };
		char[] char2 = new char[8];
		String str = "";
		int i = char2.length;
		if (number != 0) {
			for (int k = i; k < char2.length; k++) {
				int j = number & 0xF;
				i--;
				char2[i] = char1[j];
				number >>>= 4;
				str = str + char2[k];
			}
		}
		return "(byte)0x" + str;
	}

}
