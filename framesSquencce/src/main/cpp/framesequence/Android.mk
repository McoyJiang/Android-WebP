#
# Copyright (C) 2014 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

### include giflib as a prebuilt lib 使用静态链接库的方式 ###
LOCAL_PATH := $(call my-dir)
#include $(CLEAR_VARS)
#LOCAL_MODULE            := giflib-prebuilt
#LOCAL_SRC_FILES         := external/giflib/x86_64/libgiflib.a

#include $(PREBUILT_STATIC_LIBRARY)

### include giflib as a prebuilt lib 使用动态链接库的方式 ###
include $(CLEAR_VARS)
LOCAL_MODULE            := giflib-prebuilt
LOCAL_SRC_FILES         := external/giflib/x86_64/libgif.so
include $(PREBUILT_SHARED_LIBRARY)


### include libwebp-decode as a prebuilt lib 使用静态链接库的方式 ###
include $(CLEAR_VARS)
LOCAL_MODULE            := libwebp-decode-prebuilt
LOCAL_SRC_FILES         := external/webp/x86_64/libwebp-decode.a
include $(PREBUILT_STATIC_LIBRARY)

## Main library
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES = giflib-prebuilt
FRAMESEQUENCE_INCLUDE_WEBP = true
LOCAL_LDFLAGS := -llog -ljnigraphics
#LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
LOCAL_C_INCLUDES := \
	external/giflib

LOCAL_MODULE    := libframesequence
LOCAL_SRC_FILES := \
	BitmapDecoderJNI.cpp \
	FrameSequence.cpp \
	FrameSequenceJNI.cpp \
	FrameSequence_gif.cpp \
	JNIHelpers.cpp \
	Registry.cpp \
	Stream.cpp  
	

ifeq ($(FRAMESEQUENCE_INCLUDE_WEBP),true)
	LOCAL_C_INCLUDES += external/webp/include
	LOCAL_SRC_FILES += FrameSequence_webp.cpp
	LOCAL_STATIC_LIBRARIES += libwebp-decode-prebuilt
endif

LOCAL_CFLAGS += -Wall -Wno-unused-parameter -Wno-unused-variable -Wno-overloaded-virtual
LOCAL_CFLAGS += -fvisibility=hidden

LOCAL_SDK_VERSION := 14

include $(BUILD_SHARED_LIBRARY)
