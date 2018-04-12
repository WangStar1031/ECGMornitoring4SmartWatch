#include <jni.h>

#include <sys/types.h>

#include <stdio.h>

#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <sys/ipc.h>
#include <sys/time.h>
#include <unistd.h>
#include <time.h>
#include "com_YHJstyle_b005_jump_EcgNative.h"
//#include "de_PEARL_PX3756_jump_EcgNative.h"


/*
 * Class:     com_bltech_mobile_utils_EcgNative
 * Method:    EcgIni
 * Purpose:   initialize algorithm's library of ECG
 * Signature: ()I
 */

JNIEXPORT jboolean JNICALL Java_com_YHJstyle_b005_jump_EcgNative_TestCalTime(
		JNIEnv * env, jobject thiz) {

	return true;
}

/*
 * Class:     com_bltech_mobile_utils_EcgNative
 * Method:    EcgIni
 * Purpose:   initialize algorithm's library of ECG
 * Signature: ()I
 */

JNIEXPORT jint JNICALL Java_com_YHJstyle_b005_jump_EcgNative_EcgIni(
		JNIEnv * env, jobject thiz, jint i) {
	ecg_init(i);
	return 1;
}

/*
 * Class:     com_bltech_mobile_utils_EcgNative
 * Method:    EcgInserData
 * Purpose:   put ECG data to built-in buffer of algorithm's library
 * Signature: (S)I
 */

JNIEXPORT jint JNICALL Java_com_YHJstyle_b005_jump_EcgNative_EcgInserData(
		JNIEnv * env, jobject thiz, jshort a) {

	return InsertData(a);
}

/*
 * Class:     com_bltech_mobile_utils_EcgNative
 * Method:    EcgProcessData
 * Purpose:   process ECG data by algorithm
 * Parameters: b (out): improved ECG data; a (out): rhythm of the heart
 * Return :    1: Success; Others: Fail
 * Signature: ([S[S)I
 */

JNIEXPORT jint JNICALL Java_com_YHJstyle_b005_jump_EcgNative_EcgProcessData(
		JNIEnv * env, jobject thiz, jshortArray b, jshortArray a) {
	unsigned char heartbeat;
	jshort *pEcgdata;
	jshort *pHeartBeat;
	int ret = 0;


	pEcgdata = env->GetShortArrayElements(b, 0);
	pHeartBeat = env->GetShortArrayElements(a, 0);
	ret = ecg_hb(&heartbeat, pEcgdata);
	if (ret == 1) {
		pHeartBeat[0] = heartbeat;
	}

	env->ReleaseShortArrayElements(b, pEcgdata, 0);
	env->ReleaseShortArrayElements(a, pHeartBeat, 0);

	return ret;
}


