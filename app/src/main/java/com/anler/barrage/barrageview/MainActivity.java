package com.anler.barrage.barrageview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends Activity {
    public static final int DELAY_TIME = 800;
    Random random = new Random();
    BarrageView barrageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置宽高全屏

        barrageView = (BarrageView)findViewById(R.id.barrage);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barrageView.clearScreen();
            }
        });


        final Handler handler = new Handler();
        Runnable createBarrageView = new Runnable() {
            @Override
            public void run() {

                int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                barrageView.setTextColor(color);
                barrageView.sendBarrage("你好");

                handler.postDelayed(this, DELAY_TIME);
            }
        };
        handler.post(createBarrageView);


    }
}
