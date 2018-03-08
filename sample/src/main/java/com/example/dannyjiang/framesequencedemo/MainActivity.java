package com.example.dannyjiang.framesequencedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danny.framesSquencce.FrameSequence;
import com.danny.framesSquencce.FrameSequenceDrawable;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    FrameSequenceDrawable mDrawable;

    final FrameSequenceTest.CheckingProvider mProvider = new FrameSequenceTest.CheckingProvider();
    private ImageView imageVic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageVic = ((ImageView) findViewById(R.id.imageVic));

        InputStream is = getResources().openRawResource(R.raw.vic_roll);
        FrameSequence fs = FrameSequence.decodeStream(is);

        mDrawable = new FrameSequenceDrawable(fs, mProvider);
        imageVic.setImageDrawable(mDrawable);
    }

    public void loadVic(View view) {
        mDrawable.start();
    }

    public void stop(View view) {
        mDrawable.stop();
    }
}
