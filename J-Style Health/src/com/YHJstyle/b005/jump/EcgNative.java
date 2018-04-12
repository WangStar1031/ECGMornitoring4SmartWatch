package com.YHJstyle.b005.jump;

public class EcgNative {
	
	public static native int EcgIni(int a);
	
	public static native int EcgInserData(short data);
	
	public static native int EcgProcessData(short [] ecgData, short [] heartBeat);
	
	public static native int EcgProcessDebug();
	
	public static native boolean TestCalTime();
	
//	public static native int EcgGetAnalyzedData(int id, short[] b);
//
//	public static native int EcgGetAnalyzedResult(byte[] b);
//	
//	public static native int EcgGetString(byte[] b);
//
//	public static native int EcgSetDetectedSeconds(int seconds);
}
