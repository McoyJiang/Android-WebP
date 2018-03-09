package com.danny.framesSquencce;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

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
    private final FrameSequenceDrawable[] mDrawables = new FrameSequenceDrawable[3];

    final CheckingProvider mProvider = new CheckingProvider();

    public static class CheckingProvider implements FrameSequenceDrawable.BitmapProvider {
        HashSet<Bitmap> mBitmaps = new HashSet<Bitmap>();
        @Override
        public Bitmap acquireBitmap(int minWidth, int minHeight) {
            Bitmap bitmap =
                    Bitmap.createBitmap(minWidth + 1, minHeight + 4, Bitmap.Config.ARGB_8888);
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

        // load raw resources into streams, and get FrameSequenceDrawable, store them in array
        mDrawables[0] = initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_defaultRawId, -1));
        mDrawables[0].setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_DEFAULT);
                }
            }
        });
        mDrawables[1] = initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_neutralRawId, -1));
        mDrawables[1].setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_NEUTRAL);
                }
            }
        });
        mDrawables[2] = initWebpDrawable(mTypedArray.getResourceId(
                R.styleable.webImg_finalRawId, -1));
        mDrawables[2].setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                if (listener != null) {
                    listener.onAnimationFinished(STATUS_FINAL);
                }
            }
        });

        mTypedArray.recycle();

        // default drawable must not be NULL !
        setImageDrawable(mDrawables[0]);
    }

    private FrameSequenceDrawable initWebpDrawable(int resId) {
        InputStream is = null;
        FrameSequenceDrawable drawable = null;
        FrameSequence fs = null;
        try {
            is = getResources().openRawResource(resId);
            fs = FrameSequence.decodeStream(is);
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
                setImageDrawable(mDrawables[0]);

                mDrawables[0].start();
                break;
            case STATUS_NEUTRAL:
                setImageDrawable(mDrawables[1]);

                mDrawables[1].start();
                break;
            case STATUS_FINAL:
                setImageDrawable(mDrawables[2]);

                mDrawables[2].start();
                break;
        }
    }

    public void setFinishedListener(OnWebpFinishListener listener) {
        this.listener = listener;
    }
}
