package com.anler.barrage.barrageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Anler on 2015/10/26.
 */
public class BarrageView extends View {

    enum showMode{
        allScreen,
        topOfScreen,
        bottomOfScreen,
     //   showOntheCenter
    }


    private TextPaint txtPaint;
    private int screenHeight;
    private int screenWidth;
    private int x,y = 0;
    private LinkedList<Point> pos = new LinkedList();
    private LinkedList<String> txts = new LinkedList();
    private LinkedList<TextPaint> txtPaints = new LinkedList();

    private LinkedList<centerPoint> centerPos = new LinkedList();
    private LinkedList<TextPaint> centetTxtPaints = new LinkedList();
    private LinkedList<String> centerTxts = new LinkedList();

    private  int speed = 4;
    private int txtSize = 30;
    private showMode mShowMode = showMode.topOfScreen;
    private Random random = new Random();
    private RollThread rollThread;

    public BarrageView(Context context) {
        this(context, null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init()
    {
        txtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        txtPaint.setTextSize(txtSize);
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        screenWidth = rect.width();
        screenHeight = rect.height();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i =0;i<pos.size();i++)
        {
            canvas.drawText(txts.get(i), pos.get(i).x, pos.get(i).y, txtPaints.get(i));
        }
        for(int i =0;i<centerTxts.size();i++)
        {
            canvas.drawText(centerTxts.get(i), centerPos.get(i).x, centerPos.get(i).y, centetTxtPaints.get(i));
            centerPos.get(i).flag = false;
        }

        canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, new Paint());
        logic();
        invalidate();
        if(rollThread == null)
        {
            rollThread = new RollThread();
            rollThread.start();
        }

    }


    private  void logic()
    {

        for(int i =0;i<pos.size();i++)
        {
             pos.get(i).x -=speed;
            if(pos.get(i).x < -txtPaint.measureText(txts.get(i)))
            {
                pos.remove(i);
                txts.remove(i);
                txtPaints.remove(i);
            }

        }

        Log.v("gjh", "" + txts.size());
        Log.v("gjh", "txtPaints" + txtPaints.size());
    }


    public void setTextSize(int txtSize)
    {
        this.txtSize = txtSize;
        txtPaint.setTextSize(txtSize);
    }

    public void setTextColor(int color)
    {
        txtPaint.setColor(color);
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    public int setYShowMode(showMode mode)
    {

        switch (mode)
        {
            case allScreen:
                y = random.nextInt(screenHeight-txtSize)+txtSize;
                break;
            case topOfScreen:
                y = random.nextInt(screenHeight / 2 - txtSize) + txtSize;
                break;
            case bottomOfScreen:
                y = random.nextInt(screenHeight / 2 - txtSize) + screenHeight/2-txtSize;
                break;
//            case showOntheCenter:
//                y = random.nextInt(screenHeight *3 / 4 - txtSize)+ screenHeight/2 - txtSize;
//                break;
        }
        return y;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                speed = 0;
                break;
            case MotionEvent.ACTION_UP:
                speed = 4;
                break;
        }
        return true;
    }

    public void sendBarrage(String txt)
    {
        pos.add(new Point(screenWidth, setYShowMode(mShowMode)));
        TextPaint newPanit = new TextPaint(txtPaint);
        txtPaints.add(newPanit);
        txts.add(txt);
    }

    public void sendBarrageOnCenter(String txt)
    {
        centerTxts.add(txt);
        x = (int)(screenWidth - txtPaint.measureText(centerTxts.getLast()))/2;
        centerPos.add(new centerPoint(x,setYShowMode(showMode.bottomOfScreen)));
        TextPaint newPanit = new TextPaint(txtPaint);
        centetTxtPaints.add(newPanit);

    }



    public void clearScreen()
    {
        pos.clear();
        txts.clear();
        txtPaints.clear();
        centerTxts.clear();
        centetTxtPaints.clear();
        centerPos.clear();
    }

    class RollThread extends Thread
    {
        @Override
        public void run() {
            while(true)
            {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i =0;i<centerTxts.size();i++)
                {
                   if(centerPos.get(i).flag ==false)
                   {
                       centerPos.remove(i);
                       centerTxts.remove(i);
                       centetTxtPaints.remove(i);
                   }
                }
            }
        }
    }

    class centerPoint{
        int x,y;
        boolean flag = true;

        public centerPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
