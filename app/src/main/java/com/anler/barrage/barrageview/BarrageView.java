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
import android.view.ViewGroup;


import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Anler on 2015/10/26.
 */
public class BarrageView extends View {

    enum showMode{
        allScreen,
        topOfScreen,
        bottomOfScreen
    }
    private TextPaint txtPaint;
    private int screenHeight;
    private int screenWidth;
    private int x,y = 0;
    private LinkedList<Point> pos = new LinkedList();
    private LinkedList<String> txts = new LinkedList();
    private  int speed = 4;
  //  private Paint.FontMetrics mFontMetrics;// 文本测量对象
    private int txtSize = 30;
    private showMode mShowMode = showMode.topOfScreen;

    private Thread rollThread;

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


/*    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenHeight = h;
        screenWidth = w;
        x = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      //  setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }

    private int measureWidth(int widthMeasureSpec)
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        }
        else
        {
            result = (int)txtPaint.measureText(txt)+1;
            if(specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result,specSize);
            }
        }
        return result;
    }

    private  int measureHeight(int heightMeasureSpec )
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        }
        else
        {
            result = txtSize;
            if(specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result,specSize);
            }
        }
        return result;
    }*/


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i =0;i<pos.size();i++)
        {
            canvas.drawText(txts.get(i), pos.get(i).x, pos.get(i).y, txtPaint);
        }
        canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, new Paint());
        logic();
        invalidate();
//       if (rollThread == null) {
//            rollThread = new RollThread(canvas);
//           rollThread.start();
//        }
    }


    private  void logic()
    {
        x -= speed;
        for(int i =0;i<pos.size();i++)
        {
             pos.get(i).x -=speed;
            if(pos.get(i).x < -txtPaint.measureText(txts.get(i)))
            {
                pos.remove(i);
                txts.remove(i);
            }
            Log.v("gjh",""+txts.size());
        }

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
                y = (new Random()).nextInt(screenHeight-txtSize)+txtSize;
                break;
            case topOfScreen:
                y = new Random().nextInt(screenHeight/2-txtSize)+txtSize;
                break;
            case bottomOfScreen:
                y = new Random().nextInt(screenHeight/2-txtSize)+screenHeight/2-txtSize;
                break;
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
        pos.add(new Point(screenWidth,setYShowMode(mShowMode)));
        txts.add(txt);
    }

    class RollThread extends Thread
    {
        Canvas canvas;
        public RollThread(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {
            while(true)
            {
                logic();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

        }
    }
}
