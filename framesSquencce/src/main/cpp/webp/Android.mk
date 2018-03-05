# Copyright 2010 The Android Open Source Project
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

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES := \
        dec/alpha.c \
        dec/buffer.c \
        dec/frame.c \
        dec/idec.c \
        dec/io.c \
        dec/quant.c \
        dec/tree.c \
        dec/vp8.c \
        dec/vp8l.c \
        dec/webp.c \
        demux/demux.c \
        dsp/alpha_processing.c \
        dsp/alpha_processing_sse2.c \
        dsp/cpu-features.c \
        dsp/cpu.c \
        dsp/dec.c \
        dsp/dec_clip_tables.c \
        dsp/dec_mips32.c \
        dsp/dec_neon.c \
        dsp/dec_sse2.c \
        dsp/lossless.c \
        dsp/lossless_mips32.c \
        dsp/lossless_neon.c \
        dsp/lossless_sse2.c \
        dsp/upsampling.c \
        dsp/upsampling_neon.c \
        dsp/upsampling_sse2.c \
        dsp/yuv.c \
        dsp/yuv_mips32.c \
        dsp/yuv_sse2.c \
        utils/bit_reader.c \
        utils/color_cache.c \
        utils/filters.c \
        utils/huffman.c \
        utils/quant_levels_dec.c \
        utils/random.c \
        utils/rescaler.c \
        utils/thread.c \
        utils/utils.c

LOCAL_CFLAGS := -DANDROID -DWEBP_SWAP_16BIT_CSP

LOCAL_C_INCLUDES += \
        $(LOCAL_PATH)/dec \
        $(LOCAL_PATH)/../include

LOCAL_SDK_VERSION := 9

LOCAL_MODULE := libwebp-decode

LOCAL_ADDITIONAL_DEPENDENCIES := $(LOCAL_PATH)/Android.mk

include $(BUILD_STATIC_LIBRARY)
#include $(BUILD_SHARED_LIBRARY)
