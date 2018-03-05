APP_PLATFORM := android-8
APP_ABI := x86_64
LOCAL_ARM_NEON=true
ARCH_ARM_HAVE_NEON=true
# TODO: Have libjpeg do this
APP_CFLAGS := -D__ARM_HAVE_NEON=1
