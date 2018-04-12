LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libecgh
LOCAL_SRC_FILES := libecg.a  
include $(PREBUILT_STATIC_LIBRARY)  


include $(CLEAR_VARS)

LOCAL_MODULE    := Ped_heart
LOCAL_SRC_FILES := Ped_heart.cpp

LOCAL_STATIC_LIBRARIES := libecgh

include $(BUILD_SHARED_LIBRARY)
