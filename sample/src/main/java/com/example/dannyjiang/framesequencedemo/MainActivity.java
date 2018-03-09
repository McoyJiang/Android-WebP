package com.example.dannyjiang.framesequencedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.danny.framesSquencce.WebpImageView;

public class MainActivity extends AppCompatActivity {

    WebpImageView webpImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webpImageView = ((WebpImageView) findViewById(R.id.webpImage));
    }

    public void defaultAnim(View view) {
        webpImageView.playAnimation(WebpImageView.STATUS_DEFAULT);
    }

    public void neutralAnim(View view) {
        webpImageView.playAnimation(WebpImageView.STATUS_NEUTRAL);
    }

    public void finalAnim(View view) {
        webpImageView.playAnimation(WebpImageView.STATUS_FINAL);
    }
}
