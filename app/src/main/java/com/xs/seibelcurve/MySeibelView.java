package com.xs.seibelcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.NumberFormat;

/**
 * @author: Xs
 * @date: 2016-10-26 09:51
 * @describe <>
 */
public class MySeibelView extends View {
    private static final String TAG = "MySeibelView";

    private Paint mPaint,mPaint2;
    private Path mPath = new Path();
    protected int mViewWidth,mViewHeight;
    protected int mWidth,mHeight;
    private float r,rArc,x;
    private float percent=0.5f;
    private RectF rectF;
    private PointF mPointF = new PointF(0,0);

    public MySeibelView(Context context) {
        super(context);
    }

    public MySeibelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(100);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.CYAN);
        mPaint2.setStrokeWidth(8);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    public MySeibelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        mWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mHeight = mViewHeight - getPaddingTop() - getPaddingBottom();

        r = Math.min(mWidth,mHeight)*0.4f;
        rectF = new RectF(-r,-r,r,r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mViewWidth/2,mViewHeight/2);
        canvas.drawCircle(0,0,r,mPaint);
        rArc = r*(1-2*percent);
        double angle= Math.acos((double) rArc/r);
        x = r*(float) Math.sin(angle);
//        mPath.addArc(rectF,90-(float) Math.toDegrees(angle),(float) Math.toDegrees(angle)*2);
        mPath.moveTo(-x,rArc);
        mPath.rQuadTo(x/2,-r/8,x,0);
        mPath.rQuadTo(x/2,r/8,x,0);
        canvas.drawPath(mPath,mPaint2);
        mPath.rewind();
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(1);

    }
}
