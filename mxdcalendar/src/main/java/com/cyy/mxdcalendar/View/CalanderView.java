package com.cyy.mxdcalendar.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.busu.cyy.customview.Entity.DateModel;
import com.busu.cyy.customview.R;
import com.busu.cyy.customview.Util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyy513 on 2018/4/17.
 */

public class CalanderView extends View{


    Paint mpaint;
    Paint solidCircle;
    Paint hollowCircle;
    Paint weektitlepaint;


    private int width;
    private int inityear;
    private int initmonth;


    private List<DateModel> dateModels;

    private int TITLERECTHEIGHT = 150;

    private int WEEKWIDTH;
    private Rect rect;

    private int year;
    private int month;


    private boolean ISINVALID = false;
    private int clickrow ;
    private int clickcol;
    private int clickcount;

    public CalanderView(Context context,int year,int month) {
        super(context);
        init(context,year,month);
    }


    //初始化
    private void init(Context context,int year,int month) {

        dateModels = new ArrayList<>();
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
        hollowCircle.setColor(getResources().getColor(R.color.blue));
        hollowCircle.setStrokeWidth(3);
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
        System.out.println("===AKM开始绘制");
        //绘制天数
        DrawDate(canvas);
    }


    private void DrawDate(Canvas canvas) {
        int IRow =0;
        int ICol =0;
        //计算绘制几行数据
        for (int i=1;i<=DateUtil.getMonthDays(inityear,initmonth);i++)
        {
            rect = new Rect();
            weektitlepaint.getTextBounds(i+"",0,1,rect);
            ICol = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)%7;
            IRow = (i+DateUtil.getFirstDayWeek(inityear,initmonth)-1)/7;

            DateModel dateModel = new DateModel();
            dateModel.setCol(ICol);
            dateModel.setRow(IRow);
            dateModel.setCount(i);
            dateModels.add(dateModel);
            //绘制
            if (DateUtil.getFirstDayWeek(inityear,initmonth)==7)
            {
                //点击其他日期之后
                if (ISINVALID==true)
                {
                    DrawOneCircle(canvas,clickcol,clickrow,rect);
                    canvas.drawText(clickcount+"",WEEKWIDTH*clickcol+WEEKWIDTH/2,TITLERECTHEIGHT*clickrow+TITLERECTHEIGHT/2-TITLERECTHEIGHT,weektitlepaint);
                    //如果是今天的话
                    if (i==DateUtil.getSysDay() && initmonth == DateUtil.getCurrentMonth())
                    {
                        //绘制今天为空心圆
                        DrawOneHollowCircle(canvas,ICol,IRow,rect);
                    }

                }else {

                    //如果是今天的话
                    if (i==DateUtil.getSysDay() && initmonth == DateUtil.getCurrentMonth())
                    {
                        DrawOneCircle(canvas,ICol,IRow,rect);
                    }
                }
                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2-TITLERECTHEIGHT,weektitlepaint);

            }else {

                if (ISINVALID==true)
                {
                    DrawTwoCircle(canvas,clickcol,clickrow,rect);
                    canvas.drawText(clickcount+"",WEEKWIDTH*clickcol+WEEKWIDTH/2,TITLERECTHEIGHT*clickrow+TITLERECTHEIGHT/2,weektitlepaint);
                    if (i==DateUtil.getSysDay() && initmonth == DateUtil.getCurrentMonth())
                    {
                        //绘制今天为空心圆
                        DrawTwoHollowCircle(canvas,ICol,IRow,rect);
                    }

                }else {
                    if (i==DateUtil.getSysDay() && initmonth == DateUtil.getCurrentMonth())
                    {
                        DrawTwoCircle(canvas,ICol,IRow,rect);
                    }
                }
                canvas.drawText(i+"",WEEKWIDTH*ICol+WEEKWIDTH/2,TITLERECTHEIGHT*IRow+TITLERECTHEIGHT/2,weektitlepaint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //用作判断点击事件时用
        int x;
        int y;
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x = (int) event.getX();
                y = (int) event.getY();

                if (DateUtil.getFirstDayWeek(inityear,initmonth)==7)
                {
                    int Col = x/WEEKWIDTH;
                    int Row = y/TITLERECTHEIGHT+1;
                    for (int i=0;i<dateModels.size();i++)
                    {
                        if (Row==dateModels.get(i).getRow() && Col == dateModels.get(i).getCol())
                        {
                            ISINVALID = true;
                            clickrow = Row;
                            clickcol = Col;
                            clickcount = dateModels.get(i).getCount();
                        }
                    }
                } else {
                    int Col = x/WEEKWIDTH;
                    int Row = y/TITLERECTHEIGHT;
                    for (int i=0;i<dateModels.size();i++)
                    {
                        if (Row==dateModels.get(i).getRow() && Col == dateModels.get(i).getCol())
                        {
                            ISINVALID = true;
                            clickrow = Row;
                            clickcol = Col;
                            clickcount = dateModels.get(i).getCount();
                        }
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    //等于7天
    public void DrawOneCircle(Canvas canvas,int Col,int Row,Rect rect)
    {
        canvas.drawCircle(WEEKWIDTH*Col+WEEKWIDTH/2,TITLERECTHEIGHT*Row+TITLERECTHEIGHT/2-TITLERECTHEIGHT-rect.height()/2,40,solidCircle);
    }
    //不等于7天
    public void DrawTwoCircle(Canvas canvas,int Col,int Row,Rect rect)
    {
        canvas.drawCircle(WEEKWIDTH*Col+WEEKWIDTH/2,TITLERECTHEIGHT*Row+TITLERECTHEIGHT/2-rect.height()/2,40,solidCircle);
    }
    //等于7天空心圆
    public void DrawOneHollowCircle(Canvas canvas,int Col,int Row,Rect rect)
    {
        canvas.drawCircle(WEEKWIDTH*Col+WEEKWIDTH/2,TITLERECTHEIGHT*Row+TITLERECTHEIGHT/2-TITLERECTHEIGHT-rect.height()/2,40,hollowCircle);
    }
    //不等于7天空心圆
    public void DrawTwoHollowCircle(Canvas canvas,int Col,int Row,Rect rect)
    {
        canvas.drawCircle(WEEKWIDTH*Col+WEEKWIDTH/2,TITLERECTHEIGHT*Row+TITLERECTHEIGHT/2-rect.height()/2,40,hollowCircle);
    }
}
