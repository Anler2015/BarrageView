package com.anler.barrage.barrageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
    private  String txt;
    private int screenHeight;
    private int screenWidth;
    private int x,y = 0;
    private  int speed = 4;
  //  private Paint.FontMetrics mFontMetrics;// 文本测量对象
    private int txtSize = 30;

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
        txtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setTextSize(txtSize);
        //mFontMetrics = txtPaint.getFontMetrics();
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        screenWidth = rect.width();
        screenHeight = rect.height();
        x = screenWidth;
    }


    @Override
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
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(txt, x, y, txtPaint);
        canvas.drawLine(0,screenHeight/2,screenWidth,screenHeight/2,new Paint());
        logic();
        invalidate();
    }


    private  void logic()
    {
        x -= speed;
//
//        if(x < - txtPaint.measureText(txt))
//        {
//            ((ViewGroup)this.getParent()).removeView(this);
//        }
    }

    public void setText(String str)
    {
        this.txt = str;
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
    public void setShowMode(showMode mode)
    {
        Log.v("gjh", screenHeight + "");
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
}
