package com.example.dannyjiang.framesequencedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by danny.jiang on 3/27/18.
 */

public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
    }

    public void startTest(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
