package com.danny.framesSquencce;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * this is a ImageView used to display animated WebP files
 *
 * @author danny.jiang
 */

public class WebpImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "WebpImageView";

    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_NEUTRAL = 1;
    public static final int STATUS_FINAL = 2;

    // in default, there are only 3 status animations for a WebpImageView
    private List<FrameSequenceDrawable> drawableList = new ArrayList<>();

    // the default animation count
    private int defaultCount;

    // the neutral animation count
    private int neutralCount;

    // the final animation count
    private int finalCount;

    final CheckingProvider mProvider = new CheckingProvider();

    // stop all webp animation
    public void stop() {
        if (drawableList.get(STATUS_DEFAULT) != null) {
            drawableList.get(STATUS_DEFAULT).stop();
        }

        if (drawableList.get(STATUS_NEUTRAL) != null) {
            drawableList.get(STATUS_NEUTRAL).stop();
        }

        if (drawableList.get(STATUS_FINAL) != null) {
            drawableList.get(STATUS_FINAL).stop();
        }
    }

    public void destroy() {
        drawableList.clear();
    }

    public static class CheckingProvider implements FrameSequenceDrawable.BitmapProvider {
        HashSet<Bitmap> mBitmaps = new HashSet<Bitmap>();
        @Override
        public Bitmap acquireBitmap(int minWidth, int minHeight) {
            Bitmap bitmap =
                    Bitmap.createBitmap(minWidth + STATUS_NEUTRAL, minHeight + 4, Bitmap.Config.ARGB_8888);
            mBitmaps.add(bitmap);
            return bitmap;
        }

        @Override
        public void releaseBitmap(Bitmap bitmap) {
            if (!mBitmaps.contains(bitmap)) throw new IllegalStateException();
            mBitmaps.remove(bitmap);
            bitmap.recycle();
        }

        public boolean isEmpty() {
            return mBitmaps.isEmpty();
        }
    }

    private OnWebpFinishListener listener;

    /**
     * Listener which used to get if WebP animation has been finished
     */
    public interface OnWebpFinishListener {
        /**
         * a callback to call, pass which WebpIamgeView has been finished animation
         *
         * @param status    STATUS_DEFAULT or STATUS_NEUTRAL or STATUS_FINAL
         */
        void onAnimationFinished(int status);
    }

    public WebpImageView(Context context) {
        super(context);
    }

    public WebpImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // read all attrs for default & neutral & final status
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.webImg);

        defaultCount = mTypedArray.getInteger(R.styleable.webImg_defaultCount, STATUS_NEUTRAL);
        neutralCount = mTypedArray.getInteger(R.styleable.webImg_neutralCount, STATUS_NEUTRAL);
        finalCount = mTypedArray.getInteger(R.styleable.webImg_finalCount, STATUS_NEUTRAL);

        // load raw resources into streams, and get FrameSequenceDrawable, store them in array
        drawableList.add(initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_defaultRawId, -STATUS_NEUTRAL), defaultCount));
        drawableList.get(STATUS_DEFAULT).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_DEFAULT);
                }
            }
        });

        drawableList.add(initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_neutralRawId, -STATUS_NEUTRAL), neutralCount));
        if (drawableList.get(STATUS_NEUTRAL) != null) {
            drawableList.get(STATUS_NEUTRAL).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    if (listener != null) {
                        listener.onAnimationFinished(STATUS_NEUTRAL);
                    }
                }
            });
        }

        drawableList.add(initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_finalRawId, -STATUS_NEUTRAL), finalCount));
        if (drawableList.get(STATUS_FINAL) != null) {
            drawableList.get(STATUS_FINAL).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    if (listener != null) {
                        listener.onAnimationFinished(STATUS_FINAL);
                    }
                }
            });
        }

        mTypedArray.recycle();

        // default drawable must not be NULL !
        setImageDrawable(drawableList.get(STATUS_DEFAULT));
    }

    private FrameSequenceDrawable initWebpDrawable(int resId, int animationCount) {
        InputStream is = null;
        FrameSequenceDrawable drawable = null;
        FrameSequence fs = null;
        try {
            is = getResources().openRawResource(resId);
            fs = FrameSequence.decodeStream(is);
            fs.setDefaultLoopCount(animationCount);
            drawable = new FrameSequenceDrawable(fs, mProvider);
        } catch (Exception e) {
            Log.e(TAG, "error happens when get FrameSequenceDrawable : "
                    + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "io not closed in right way : " + e.getMessage());
            }
        }

        return drawable;
    }

    public void setDefaultAnimationCount(int defaultAnimationCount) {
        drawableList.get(STATUS_DEFAULT).setAnimationCount(defaultAnimationCount);
    }

    public void setNeutralAnimationCount(int neutralAnimationCount) {
        if (drawableList.get(STATUS_NEUTRAL) == null) return;
        drawableList.get(STATUS_NEUTRAL).setAnimationCount(neutralAnimationCount);
    }

    public void setFinalAnimationCount(int finalAnimationCount) {
        if (drawableList.get(STATUS_FINAL) == null) return;
        drawableList.get(STATUS_FINAL).setAnimationCount(finalAnimationCount);
    }

    public void setDefaultDrawable(int resId) {
        drawableList.remove(STATUS_DEFAULT);

        drawableList.add(STATUS_DEFAULT, initWebpDrawable(resId, 1));
        drawableList.get(STATUS_DEFAULT).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_DEFAULT);
                }
            }
        });
    }

    public void setNeutralDrawable(int resId) {
        drawableList.remove(STATUS_NEUTRAL);

        drawableList.add(STATUS_NEUTRAL, initWebpDrawable(resId, 1));
        
        drawableList.get(STATUS_NEUTRAL).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_NEUTRAL);
                }
            }
        });
    }

    public void setFinalDrawable(int resId) {
        drawableList.remove(STATUS_FINAL);

        drawableList.add(STATUS_FINAL, initWebpDrawable(resId, 1));
        
        drawableList.get(STATUS_FINAL).setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_FINAL);
                }
            }
        });
    }

    // only play default animation
    public void playAnimation() {
        playAnimation(STATUS_DEFAULT);
    }

    /**
     * there are 3 status for every Leading Actor in SubMap
     *
     * DEFAULT:         loop animation
     * WAIT:            displayed every 7 seconds
     * CELEBRATE:       displayed if the activity completed perfectly
     */
    public void playAnimation(int status) {
        switch (status) {
            case STATUS_DEFAULT:
                setImageDrawable(drawableList.get(STATUS_DEFAULT));

                drawableList.get(STATUS_DEFAULT).start();
                break;
            case STATUS_NEUTRAL:
                if (drawableList.get(STATUS_NEUTRAL) == null) return;
                setImageDrawable(drawableList.get(STATUS_NEUTRAL));

                drawableList.get(STATUS_NEUTRAL).start();
                break;
            case STATUS_FINAL:
                if (drawableList.get(STATUS_FINAL) == null) return;
                setImageDrawable(drawableList.get(STATUS_FINAL));

                drawableList.get(STATUS_FINAL).start();
                break;
        }
    }

    public void setFinishedListener(OnWebpFinishListener listener) {
        this.listener = listener;
    }
}
