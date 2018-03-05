# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#################  GIF LIB   ################################
add_library(
            # add gif library
            native-gif

            STATIC

            src/main/cpp/gif/dgif_lib.c
            src/main/cpp/gif/egif_lib.c
            src/main/cpp/gif/gifalloc.c
            src/main/cpp/gif/gif_err.c
            src/main/cpp/gif/gif_hash.c
            src/main/cpp/gif/quantize.c
            )

include_directories(src/main/cpp/gif/)
##############################################################

#################   GIF WEBP  ##################################
add_library(
            native-webp

            STATIC

            src/main/cpp/webp/dec/alpha.c
            src/main/cpp/webp/dec/buffer.c
            src/main/cpp/webp/dec/frame.c
            src/main/cpp/webp/dec/idec.c
            src/main/cpp/webp/dec/io.c
            src/main/cpp/webp/dec/quant
            src/main/cpp/webp/dec/tree.c
            src/main/cpp/webp/dec/vp8.c
            src/main/cpp/webp/dec/vp8l.c
            src/main/cpp/webp/dec/webp.c

            src/main/cpp/webp/demux/demux.c

            src/main/cpp/webp/dsp/alpha_processing.c
            src/main/cpp/webp/dsp/alpha_processing_sse2.c
            src/main/cpp/webp/dsp/cpu-features.c
            src/main/cpp/webp/dsp/cpu.c
            src/main/cpp/webp/dsp/dec.c
            src/main/cpp/webp/dsp/dec_clip_tables.c
            src/main/cpp/webp/dsp/dec_mips32.c
            src/main/cpp/webp/dsp/dec_neon.c
            src/main/cpp/webp/dsp/dec_sse2.c
            src/main/cpp/webp/dsp/lossless.c
            src/main/cpp/webp/dsp/lossless_mips32.c
            src/main/cpp/webp/dsp/lossless_neon.c
            src/main/cpp/webp/dsp/lossless_sse2.c
            src/main/cpp/webp/dsp/upsampling.c
            src/main/cpp/webp/dsp/upsampling_neon.c
            src/main/cpp/webp/dsp/upsampling_sse2.c
            src/main/cpp/webp/dsp/yuv.c
            src/main/cpp/webp/dsp/yuv_mips32.c
            src/main/cpp/webp/dsp/yuv_sse2.c

            src/main/cpp/webp/utils/bit_reader.c
            src/main/cpp/webp/utils/color_cache.c
            src/main/cpp/webp/utils/filters.c
            src/main/cpp/webp/utils/huffman.c
            src/main/cpp/webp/utils/quant_levels_dec.c
            src/main/cpp/webp/utils/random.c
            src/main/cpp/webp/utils/rescaler.c
            src/main/cpp/webp/utils/thread.c
            src/main/cpp/webp/utils/utils.c
            )

include_directories(
                src/main/cpp/webp/include
                src/main/cpp/webp/dec
                )
################################################################

####################  FRAME_SEQUENCE LIB   ######################
add_library( native-frame-sequence

             SHARED

             src/main/cpp/framesequence/BitmapDecoderJNI.cpp
             src/main/cpp/framesequence/FrameSequence.cpp
             src/main/cpp/framesequence/FrameSequenceJNI.cpp
             src/main/cpp/framesequence/FrameSequence_gif.cpp
             src/main/cpp/framesequence/FrameSequence_webp.cpp
             src/main/cpp/framesequence/JNIHelpers.cpp
             src/main/cpp/framesequence/Registry.cpp
             src/main/cpp/framesequence/Stream.cpp
             )

include_directories(
                src/main/cpp/webp/include
                )
################################################################

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

#target_link_libraries( # Specifies the target library.
#                       native-lib
#
#                       # Links the target library to the log library
#                       # included in the NDK.
#                       ${log-lib} )
target_link_libraries(
                        native-frame-sequence
                        -ljnigraphics

                        native-gif native-webp ${log-lib}
                     )