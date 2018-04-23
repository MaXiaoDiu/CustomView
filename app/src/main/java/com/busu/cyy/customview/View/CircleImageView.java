package com.busu.cyy.customview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.busu.cyy.customview.R;

/**
 * Created by Cyy513 on 2018/4/23.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {


    private Paint mPaint; //画笔

    private int mRadius; //圆形图片的半径
    private Paint framePaint;

    private int type;//View的形状
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;


    /**
     * 圆角图片区域
     */
    private RectF mRoundRect;
    private Path mRoundPath;
    private float mCornerRadius;//圆角的大小
    //左上角圆角大小
    private float mLeftTopCornerRadius;
    //右上角圆角大小
    private float mRightTopCornerRadius;
    //左下角圆角大小
    private float mLeftBottomCornerRadius;
    //右下角圆角大小
    private float mRightBottomCornerRadius;


    private int mBorderColor;//描边的颜色
    private float mBorderWidth;//描边的宽度



    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mBorderWidth = array.getDimension(R.styleable.CircleImageView_border_width,0);
        mBorderColor = array.getColor(R.styleable.CircleImageView_border_color,Color.WHITE);
        mCornerRadius = array.getDimension(R.styleable.CircleImageView_corner_radius,0);
        mLeftTopCornerRadius = array.getDimension(R.styleable.CircleImageView_leftTop_corner_radius,0);
        mRightTopCornerRadius = array.getDimension(R.styleable.CircleImageView_rightTop_corner_radius,0);
        mLeftBottomCornerRadius = array.getDimension(R.styleable.CircleImageView_leftBottom_corner_radius,0);
        mRightBottomCornerRadius = array.getDimension(R.styleable.CircleImageView_rightBottom_corner_radius,0);
        type = array.getInt(R.styleable.CircleImageView_type, TYPE_CIRCLE);
        array.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //因为是圆形图片，所以应该让宽高保持一致
        if (type==TYPE_CIRCLE)
        {
            int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = size / 2;
            setMeasuredDimension(size, size);

        }
    }

    private void init() {
        mRoundPath = new Path();
        framePaint = new Paint();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setAntiAlias(true);
        framePaint.setColor(mBorderColor);
        framePaint.setStrokeWidth(mBorderWidth);
        setUpShader();
        //画圆形，指定好中心点坐标、半径、画笔
       if (type==TYPE_CIRCLE)
       {
           canvas.drawCircle(mRadius, mRadius, mRadius-mBorderWidth, mPaint);
           canvas.drawCircle(mRadius, mRadius, mRadius-mBorderWidth, framePaint);

       }else if (type ==TYPE_ROUND)
       {
           //圆角
           setRoundPath();
           canvas.drawPath(mRoundPath,mPaint);
           canvas.drawPath(mRoundPath,framePaint);
       }
    }

    //写一个drawble转BitMap的方法
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 初始化Shader
     */
    private void setUpShader()
    {
        Bitmap bitmap = drawableToBitmap(getDrawable());
        //初始化BitmapShader，传入bitmap对象
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float mScale = 1.0f; //图片的缩放比例
        Matrix matrix = new Matrix();
        if (type==TYPE_CIRCLE)
        {
            //计算缩放比例
            mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());

        }else if (type == TYPE_ROUND)
        {
            mScale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
                    getHeight() * 1.0f / bitmap.getHeight());
        }

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        matrix.preScale(mScale, mScale);
        bitmapShader.setLocalMatrix(matrix);
        // 设置shader
        mPaint.setShader(bitmapShader);
    }

    //设置圆角路径
    private void setRoundPath()
    {
        mRoundPath.reset();

       if (mLeftBottomCornerRadius==0 && mLeftTopCornerRadius==0 && mRightBottomCornerRadius == 0 && mRightTopCornerRadius==0)
       {
           //使用mCornerRadius
           mRoundPath.addRoundRect(mRoundRect,
                   new float[]{mCornerRadius, mCornerRadius,
                           mCornerRadius, mCornerRadius,
                           mCornerRadius, mCornerRadius,
                           mCornerRadius, mCornerRadius
                   },
                   Path.Direction.CW);
       }else
       {
           //使用mCornerRadius
           mRoundPath.addRoundRect(mRoundRect,
                   new float[]{mLeftTopCornerRadius, mLeftTopCornerRadius,
                           mLeftBottomCornerRadius, mLeftBottomCornerRadius,
                           mRightTopCornerRadius, mRightTopCornerRadius,
                           mRightBottomCornerRadius, mRightBottomCornerRadius
                   },
                   Path.Direction.CW);
       }
    }


    /**
     * 设置图片类型
     */

    public CircleImageView setType(int type)
    {
        this.type = type;
        requestLayout();
        return this;
    }

    /**
     * 设置统一圆角大小
     */
    public CircleImageView setCornerRadius(int radius)
    {
        mCornerRadius = radius;
        invalidate();
        return this;
    }

    /**
     * 设置边框颜色
     */

    public CircleImageView setBorderColor(int color)
    {
        mBorderColor = color;
        invalidate();
        return this;
    }

    /**
     * 设置边框宽度
     */
    public CircleImageView setBorderWidth(int width)
    {
        mBorderWidth = width;
        invalidate();
        return this;
    }

    /**
     * 设置左上角度
     */
    public CircleImageView setLeftTopCornerRadius(int radius)
    {
        mLeftTopCornerRadius = radius;
        invalidate();
        return this;
    }
    /**
     * 设置左下角度
     */
    public CircleImageView setLeftBottomCornerRadius(int radius)
    {
        mLeftBottomCornerRadius = radius;
        invalidate();
        return this;
    }
    /**
     * 设置右上角度
     */
    public CircleImageView setRightTopCornerRadius(int radius)
    {
        mRightTopCornerRadius = radius;
        invalidate();
        return this;
    }
    /**
     * 设置右下角度
     */
    public CircleImageView setRightBottomCornerRadius(int radius)
    {
        mRightBottomCornerRadius = radius;
        invalidate();
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND)
        {
            mRoundRect = new RectF(mBorderWidth/2, mBorderWidth/2, MeasureSpec.getSize(getMeasuredWidth()) - mBorderWidth/2, MeasureSpec.getSize(getMeasuredHeight()) - mBorderWidth/2);
        }
    }
}
