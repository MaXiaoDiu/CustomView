package com.busu.cyy.customview.View;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.busu.cyy.customview.R;

/**
 * Created by Cyy513 on 2018/4/10.
 */

public class CircleCheck extends View {


    private int btncolor;//按钮颜色
    private String btnText;//按钮上面的字
    private int btnTextColor;//按钮上面字的颜色
    private int circleColor;//旋转圆环颜色

    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mStrokePaint;
    private Paint mInnerCirclePaint;
    private Paint mWhiteStrokPaint;
    private Path mPath;

    private RectF mArcRectF;

    private int mDegree;
    private int mAngle;
    private int strokWidth;
    private int scaleValue;

    ValueAnimator loadingAnimator;
    ValueAnimator DrawCircleAnimator;
    ValueAnimator StartScaleAnimator;
    private boolean STATE_SUCCESS = false;
    private boolean START_SCALE = false;

    private Matrix mMatrix = new Matrix();

    public CircleCheck(Context context) {
        super(context);
        init(context,null);

    }


    public CircleCheck(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public CircleCheck(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs) {

        if(attrs != null){

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);
            btncolor = ta.getInt(R.styleable.LoadingButton_btnColor, Color.BLUE);
            btnText = ta.getString(R.styleable.LoadingButton_btnText);
            btnTextColor = ta.getInt(R.styleable.LoadingButton_btnTextColor,Color.WHITE);
            circleColor = ta.getInt(R.styleable.LoadingButton_circleColor,Color.RED);
            ta.recycle();
        }

        //按钮画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(btncolor);
        mPaint.setStyle(Paint.Style.FILL);
        //按钮文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //旋转圆环画笔
        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(circleColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);
        //内部圆
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setColor(Color.RED);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);
        //缩小的外部圆
        mWhiteStrokPaint = new Paint();
        mWhiteStrokPaint.setAntiAlias(true);
        mWhiteStrokPaint.setColor(Color.WHITE);
        mWhiteStrokPaint.setStyle(Paint.Style.STROKE);
        mWhiteStrokPaint.setStrokeWidth(10);


        mDegree = 0;
        mAngle = 0;

        mArcRectF = new RectF(100,50,300,250);

        mPath = new Path();

        StartPlayAnimator();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.addArc(mArcRectF,270+mAngle,360-mAngle);
        mTextPaint.setColor(Color.RED);
        if(mAngle != 0){
            mMatrix.setRotate(mDegree,200,150);
            mPath.transform(mMatrix);
            mDegree += 10;
        }
        canvas.drawPath(mPath,mStrokePaint);
        //绘制圆
        if (STATE_SUCCESS)
        {
            mPath.addArc(mArcRectF,0,360);
            canvas.drawPath(mPath,mStrokePaint);
        }
        canvas.drawCircle(200,150,strokWidth,mInnerCirclePaint);
        if (START_SCALE)
        {
            //开始缩小
            canvas.drawCircle(200,150,120-scaleValue,mWhiteStrokPaint);
            mTextPaint.setColor(btncolor);
            canvas.drawText("√",200,163,mTextPaint);
        }
    }

    //尺寸发生变化回调
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //计算控件的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void StartPlayAnimator()
    {
        //旋转圆环动画
        loadingAnimator = ValueAnimator.ofInt(0,90);
        loadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAngle = (Integer) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        loadingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                STATE_SUCCESS = true;
                invalidate();
                DrawCircleAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        loadingAnimator.setDuration(1000);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setRepeatMode(ValueAnimator.REVERSE);
        loadingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        loadingAnimator.start();
        //画个圆
        DrawCircleAnimator = ValueAnimator.ofInt(0,100);
        DrawCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                strokWidth = (Integer) valueAnimator.getAnimatedValue()+10;
                invalidate();
            }
        });
        DrawCircleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                    StartScaleAnimator.start();
                    invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        DrawCircleAnimator.setDuration(1000);
        DrawCircleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //画个圆
        StartScaleAnimator  = ValueAnimator.ofInt(0,10);
        StartScaleAnimator .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                START_SCALE = true;//开始缩小,开启一个动画
                scaleValue = (Integer) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        StartScaleAnimator .setDuration(500);
        StartScaleAnimator .setInterpolator(new AccelerateDecelerateInterpolator());

    }

    /**
     * 停止圆圈转动动画
     */

    public void StopLoadingAnimator()
    {
        loadingAnimator.end();
    }

}
