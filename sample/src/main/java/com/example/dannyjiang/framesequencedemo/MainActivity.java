package com.example.dannyjiang.framesequencedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.danny.framesSquencce.WebpImageView;

import static com.danny.framesSquencce.WebpImageView.STATUS_DEFAULT;
import static com.danny.framesSquencce.WebpImageView.STATUS_FINAL;
import static com.danny.framesSquencce.WebpImageView.STATUS_NEUTRAL;

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

        webpImageView.setDefaultDrawable(R.raw.ben_neutral_talk_right);
        webpImageView.setNeutralDrawable(R.raw.ben_sad_blink_right);
        webpImageView.setFinalDrawable(R.raw.vic_roll);

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
