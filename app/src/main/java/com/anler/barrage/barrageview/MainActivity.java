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
        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final Handler handler = new Handler();
        Runnable createBarrageView = new Runnable() {
            @Override
            public void run() {
                //新建一条弹幕，并设置文字
                final BarrageView barrageView = new BarrageView(MainActivity.this);
                barrageView.setText("你好");
                int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                barrageView.setTextColor(color);
           //     barrageView.setY(new Random().nextInt(1280));
               barrageView.setShowMode(BarrageView.showMode.topOfScreen);
                addContentView(barrageView,lp);

                //发送下一条消息
                handler.postDelayed(this, DELAY_TIME);
            }
        };
        handler.post(createBarrageView);


    }
}
