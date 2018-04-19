package com.busu.cyy.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.busu.cyy.customview.R;

/**
 * Created by Cyy513 on 2018/4/19.
 */

public class CalanderTitleView extends View {

    Paint titletxtpaint;
    Paint weektitlepaint;
    Paint mpaint;

    private String date_text;

    private int TITLERECTHEIGHT = 130;
    private int TITLEPO = TITLERECTHEIGHT/2;

    private int WEEKWIDTH;

    private int width;
    private int inityear;
    private int initmonth;

    private int year;
    private int month;

    private String[] weeks ={"日","一","二","三","四","五","六"};



    public CalanderTitleView(Context context,int year,int month) {
        super(context);
        init(context,year,month);
    }

    public CalanderTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,year,month);
    }

    public CalanderTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,year,month);
    }

    private void init(Context context,int year,int month) {


        this.year = year;
        this.month = month;

        inityear = year;
        initmonth = month;

        mpaint = new Paint();
        mpaint.setColor(getResources().getColor(R.color.red));
        mpaint.setStrokeWidth(TITLERECTHEIGHT);
        mpaint.setAntiAlias(true);
        titletxtpaint = new Paint();
        titletxtpaint.setColor(Color.WHITE);
        titletxtpaint.setTextSize(45);
        titletxtpaint.setTextAlign(Paint.Align.CENTER);
        titletxtpaint.setAntiAlias(true);
        weektitlepaint = new Paint();
        weektitlepaint.setColor(getResources().getColor(R.color.white));
        weektitlepaint.setTextSize(40);
        weektitlepaint.setTextAlign(Paint.Align.CENTER);
        weektitlepaint.setAntiAlias(true);
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
        date_text= inityear+"年"+initmonth+"月";
        //绘制Title
        DrawTitle(canvas);
        //绘制日期
        DrawText(canvas,date_text);
        //绘制星期
        DrawWeekTxt(canvas,weeks);

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

    //设置年月

    public void setYearAndMonth(int year,int month)
    {
        this.inityear = year;
        this.initmonth = month;
        invalidate();
    }
}
