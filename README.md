# FrameSequence
This library is used to display animated WebP image file. 
It uses 3 native libraries to implement the function: libwebp, libgif, framesequence.

# Why this lib ?
For now only Facebook's `Fresco` which can be used to display WebP image file.<br>
But `Fresco` has some defect, such as it takes too much space in project, besides,<br>
when you are going to use different animated WebP image file on a SimpleDraweeView<br>
there will be a blank gap between different Controller, like the following discussion:<br>
[SimpleDraweeView black filckering on replacement image](https://github.com/facebook/fresco/issues/1468)<br>
[Smooth transition when changing image on SimpleDraweeView](https://github.com/facebook/fresco/issues/1167)<br>
[How to make it not flash when constantly switching Load picture?](https://github.com/facebook/fresco/issues/833)<br>


# How to import this library
You can import this library into your porject by two ways:

### 1 download all the source code of framesequence library, then import it into your own project as a library module
for this approach is very simple, no need to make any introductions

### 2 just use aar dependency
1. download the framesSquencce-debug.aar file in this project.

2. copy this file into your project's libs directory

3. add the following changes in your module's build.gradle file
```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```

4. add dependency in build.gradle
```
compile (name: 'framesSquencce', ext: 'aar')
```

# how to use in Android
there is a customized widget `WebpImageView`. Use this widget you can simply dispay animated WebP image by the following code:

### in xml layout
```
<com.danny.framesSquencce.WebpImageView
        android:id="@+id/webpImage"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:background="@color/colorAccent"
        webpImg:defaultRawId="@raw/ben_neutral_talk_right"
        webpImg:neutralRawId="@raw/ben_sad_blink_right"
        webpImg:finalRawId="@raw/ben_happy_talk_right"

        webpImg:defaultCount="2"
        webpImg:neutralCount="1"
        webpImg:finalCount="3"
        />
```
there are 3 raw resources you can set to a WebpImageView, they are respectively for default & neutral & final animation status.<br>
besides, you can set animation count by setting `defaultCount` & `neutralCount` & `finalCount`

### in Activity
you can set animation finish Listener
```
public class MainActivity extends AppCompatActivity {

    WebpImageView webpImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webpImageView = ((WebpImageView) findViewById(R.id.webpImage));

        // add finish callback
        webpImageView.setFinishedListener(new WebpImageView.OnWebpFinishListener(){

            @Override
            public void onAnimationFinished(int status) {
                switch (status) {
                    case STATUS_DEFAULT:
                        Toast.makeText(MainActivity.this, "default webp animation finished",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case STATUS_NEUTRAL:
                        Toast.makeText(MainActivity.this, "neutral webp animation finished",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case STATUS_FINAL:
                        Toast.makeText(MainActivity.this, "final webp animation finished",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        // set animation count for DEFAULT & NEUTRAL & FINAL animation
        webpImageView.setDefaultAnimationCount(1);
        webpImageView.setNeutralAnimationCount(2);
        webpImageView.setFinalAnimationCount(1);
    }

    public void defaultAnim(View view) {
        webpImageView.playAnimation(STATUS_DEFAULT);
    }

    public void neutralAnim(View view) {
        webpImageView.playAnimation(WebpImageView.STATUS_NEUTRAL);
    }

    public void finalAnim(View view) {
        webpImageView.playAnimation(WebpImageView.STATUS_FINAL);
    }
}
```

## for more details, please take references from `FrameSequenceTest.java` in sample
