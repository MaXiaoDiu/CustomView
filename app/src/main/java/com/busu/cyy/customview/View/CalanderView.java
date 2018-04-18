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
    Paint titletxtpaint;
    Paint weektitlepaint;
    Paint solidCircle;
    Paint hollowCircle;

    private int width;
    private int inityear = Integer.parseInt(DateUtil.getSysYear());
    private int initmonth = DateUtil.getCurrentMonth();

    private String date_text;


    private int TITLERECTHEIGHT = 150;
    private int TITLEPO = TITLERECTHEIGHT/2;

    private int WEEKWIDTH;

    private String[] weeks ={"日","一","二","三","四","五","六"};

    //用到的动画
    private ValueAnimator PreMonthAnimator;//滑到上一个月的动画
    private ValueAnimator NextMonthAnimator;//滑到下一个月的动画


    private int lastX;
    private int offsetX;//偏移量


    RelativeLayout.LayoutParams layoutParams;

    public CalanderView(Context context) {
        super(context);
        init(context);
    }


    //初始化
    private void init(Context context) {

        mpaint = new Paint();
        mpaint.setColor(getResources().getColor(R.color.calender));
        mpaint.setStrokeWidth(150);
        mpaint.setAntiAlias(true);
        titletxtpaint = new Paint();
        titletxtpaint.setColor(Color.WHITE);
        titletxtpaint.setTextSize(50);
        titletxtpaint.setTextAlign(Paint.Align.CENTER);
        titletxtpaint.setAntiAlias(true);
        weektitlepaint = new Paint();
        weektitlepaint.setColor(getResources().getColor(R.color.calender));
        weektitlepaint.setTextSize(50);
        weektitlepaint.setTextAlign(Paint.Align.CENTER);
        weektitlepaint.setAntiAlias(true);

        solidCircle = new Paint();
        solidCircle.setColor(getResources().getColor(R.color.calender));
        solidCircle.setStyle(Paint.Style.FILL);
        solidCircle.setAntiAlias(true);

        hollowCircle = new Paint();
        hollowCircle.setColor(getResources().getColor(R.color.calender));
        hollowCircle.setStyle(Paint.Style.STROKE);
        hollowCircle.setAntiAlias(true);
    }

    public CalanderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalanderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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
        date_text= inityear+"年"+initmonth+"月";
        //绘制Title
        DrawTitle(canvas);
        //绘制日期
        DrawText(canvas,date_text);
        //绘制星期
        DrawWeekTxt(canvas,weeks);
        //绘制天数
        DrawDate(canvas);
    }

    private void DrawDate(Canvas canvas) {

        int IRow =0;
        int ICol =0;
        //计算绘制几行数据
        for (int i=1;i<=DateUtil.getMonthDays(inityear,initmonth);i++)
        {
            ICol = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)%7;
            IRow = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)/7;
            //绘制
            if (DateUtil.getFirstDayWeek(inityear,initmonth)==7)
            {

                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2+TITLERECTHEIGHT,weektitlepaint);

            }else {

                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2+TITLERECTHEIGHT*2,weektitlepaint);
            }
        }

    }

    //绘制星期数
    private void DrawWeekTxt(Canvas canvas, String[] weeks) {
        Rect rect = new Rect();
        for (int i=0;i<weeks.length;i++)
        {
            weektitlepaint.getTextBounds(weeks[i],0,1,rect);
            canvas.drawText(weeks[i],(WEEKWIDTH/2+i*WEEKWIDTH),TITLERECTHEIGHT*2-TITLEPO+30,weektitlepaint);
        }
    }

    //绘制标题上的日期
    private void DrawText(Canvas canvas, String date_text) {
        Rect rect = new Rect();
        titletxtpaint.getTextBounds(date_text,0,1,rect);
        canvas.drawText(date_text,width/2,TITLEPO+rect.height()/2,titletxtpaint);
    }

    private void DrawTitle(Canvas canvas) {

        RectF rectF = new RectF(0,0,width,TITLERECTHEIGHT);
        canvas.drawRect(rectF,mpaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //记录触摸点的坐标
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                offsetX = x - lastX;
                //在当前left,top,right,bottom的基础上加上偏移量
                layout(getLeft() + offsetX, getTop(), getRight()+offsetX , getBottom());
                break;
            case MotionEvent.ACTION_UP:

                if (offsetX>-20)
                {
                    PreMonth();
                }
                break;

        }
        return true;
    }

    //向右滑,上一个月

    private void PreMonth()
    {
        //添加动画


    }

    //向左滑,下一个月

    private void NextMonth()
    {

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
