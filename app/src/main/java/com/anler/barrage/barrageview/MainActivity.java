package com.anler.barrage.barrageview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import java.util.Random;

public class MainActivity extends Activity {
    public static final int DELAY_TIME = 800;
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置宽高全屏
        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final BarrageView barrageView = new BarrageView(MainActivity.this);
        addContentView(barrageView, lp);
        final Handler handler = new Handler();
        Runnable createBarrageView = new Runnable() {
            @Override
            public void run() {

//                int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
//                barrageView.setTextColor(color);
                barrageView.sendBarrage("你好");

                handler.postDelayed(this, DELAY_TIME);
            }
        };
        handler.post(createBarrageView);


    }
}
