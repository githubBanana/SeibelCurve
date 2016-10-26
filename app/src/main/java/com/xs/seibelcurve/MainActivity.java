package com.xs.seibelcurve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        WaveLoadingView mWaveLoadingView = new WaveLoadingView(this);

        mWaveLoadingView.setWaveColor(0xff0099CC)
                        .setTextColor(0xffFFFFFF)
                        .setTextSize(40)
                        .setTextColor(0xff000000)
                        .setAmplitude(20)
                        .setPalstance(0.5f)
                        .setRefreshTime(4);
        mWaveLoadingView.setOnFinishedListener(new WaveLoadingView.OnFinishedListener() {
            @Override
            public void onFinished() {
                System.out.println("完成");
            }
        });
        layout.addView(mWaveLoadingView);*/
    }
}
