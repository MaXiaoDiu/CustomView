package com.busu.cyy.customview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.busu.cyy.customview.R;
import com.busu.cyy.customview.Util.DateUtil;

import java.util.Calendar;

/**
 * Created by Cyy513 on 2018/4/17.
 */

public class CalanderView extends View {


    Paint mpaint;
    Paint solidCircle;
    Paint hollowCircle;
    Paint weektitlepaint;


    private int width;
    private int inityear;
    private int initmonth;



    private int TITLERECTHEIGHT = 150;

    private int WEEKWIDTH;


    private int year;
    private int month;



    public CalanderView(Context context,int year,int month) {
        super(context);
        init(context,year,month);
    }


    //初始化
    private void init(Context context,int year,int month) {

        this.year = year;
        this.month = month;

        inityear = year;
        initmonth = month;

        mpaint = new Paint();
        mpaint.setColor(getResources().getColor(R.color.calender));
        mpaint.setStrokeWidth(150);
        mpaint.setAntiAlias(true);

        weektitlepaint = new Paint();
        weektitlepaint.setColor(getResources().getColor(R.color.white));
        weektitlepaint.setTextSize(40);
        weektitlepaint.setTextAlign(Paint.Align.CENTER);
        weektitlepaint.setAntiAlias(true);

        solidCircle = new Paint();
        solidCircle.setColor(getResources().getColor(R.color.blue));
        solidCircle.setStyle(Paint.Style.FILL);
        solidCircle.setAntiAlias(true);

        hollowCircle = new Paint();
        hollowCircle.setColor(getResources().getColor(R.color.calender));
        hollowCircle.setStyle(Paint.Style.STROKE);
        hollowCircle.setAntiAlias(true);
    }

    public CalanderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,year,month);
    }

    public CalanderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,year,month);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        WEEKWIDTH = width/7;
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.DarkBlue));
        //绘制天数
        DrawDate(canvas);
    }

    private void DrawDate(Canvas canvas) {

        int IRow =0;
        int ICol =0;
        //计算绘制几行数据
        for (int i=1;i<=DateUtil.getMonthDays(inityear,initmonth);i++)
        {
            Rect rect = new Rect();
            weektitlepaint.getTextBounds(i+"",0,1,rect);
            ICol = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)%7;
            IRow = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)/7;
            //绘制
            if (DateUtil.getFirstDayWeek(inityear,initmonth)==7)
            {

                if (i==DateUtil.getSysDay())
                {
                    canvas.drawCircle(WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2-TITLERECTHEIGHT-rect.height()/2,40,solidCircle);
                }
                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2-TITLERECTHEIGHT,weektitlepaint);

            }else {
                if (i==DateUtil.getSysDay())
                {
                    canvas.drawCircle(WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2-rect.height()/2,40,solidCircle);
                }
                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2,weektitlepaint);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //用作判断点击事件时用
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    //向右滑,上一个月

    private void PreMonth()
    {

        SubMonth();

    }

    //向左滑,下一个月

    private void NextMonth()
    {
        AddMonth();
    }

    //月份或年增加

    private void AddMonth()
    {
        if (initmonth == 12)
        {
            initmonth = 1;
            inityear++;
            invalidate();
        }else {
            initmonth++;
            invalidate();
        }
    }

    //月份或年份减少
    private void SubMonth()
    {
        if (initmonth==1)
        {
            initmonth =12;
            inityear--;
            invalidate();

        }else {

            initmonth--;
            invalidate();
        }
    }
}
