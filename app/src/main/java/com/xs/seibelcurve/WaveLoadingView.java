package com.xs.seibelcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: Xs
 * @date: 2016-10-26 14:13
 * @describe <>
 */
public class WaveLoadingView extends View {

    private Paint wavePaint;
    private Paint wavePaint2;

    private Paint textPaint;
    private int textColor = 0xffFFFFFF;
    private int waveColor = 0xff0099CC;
    private int textSize = 50;
    private Path path;
    private Path path2;


    // 左右偏移 φ
    private int fai = 0,fai2 = 0;
    // 上下偏移
    private float k = -50,k2 = -55;
    // 角速度
    private float w = 0.5f,w2 = 0.5f;
    // 振幅
    private int a = 20,a2 = 30;

    private int height;
    private int width;
    private float targetHeight;
    private float textHeight;
    private int progress = 0;
    // 0% 时，空白的高度
    private float baseBlank;

    private OnFinishedListener listener;

    private int ms = 4;

    private boolean isRun = true;

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveLoadingView(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(0x550099CC);

        wavePaint2 = new Paint();
        wavePaint2.setAntiAlias(true);
        wavePaint2.setColor(0x550099CC);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        path = new Path();
        path2 = new Path();

        new MyThread().start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPath();
        canvas.drawPath(path, wavePaint);
/*        setPath2();
        canvas.drawPath(path2, wavePaint2);*/

        String str = progress + "%";
        float strWidth = textPaint.measureText(str);
        canvas.drawText(str, width / 2 - (strWidth / 2), textHeight, textPaint);
    }

    private void setPath(){
        int x = 0;
        int y = 0;
        path.reset();
        for (int i = 0; i < width; i++) {
            x = i;
            y = (int) (a * Math.sin((i * w + fai) * Math.PI / 180) + k);
            if (i == 0) {
                path.moveTo(x, y);
            }
            path.quadTo(x, y, x + 1, y);
        }
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();
    }

    private void setPath2(){
        int x;
        int y;
        path2.reset();
        for (int i = 0; i < width; i++) {
            x = i;
            y = (int) (a2 * Math.sin((i * w2 + fai2) * Math.PI / 180) + k2);
            if (i == 0) {
                path2.moveTo(x, y);
            }
            path2.quadTo(x, y, x + 1, y);
        }
        path2.lineTo(width, height);
        path2.lineTo(0, height);
        path2.close();
    }


    /**
     *
     * @param p 0~1
     */
    public void updateProgress(float p) {
        if(p >=0 && p <= 1){
            targetHeight = (float) (baseBlank * (1 - p));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initLayoutParams();
    }

    private void initLayoutParams(){
        height = this.getHeight();
        width = this.getWidth();
        baseBlank = (float) (height * 0.9);
        targetHeight = baseBlank;
        k = baseBlank;
        k2 = baseBlank;
        textHeight = baseBlank;
    }

    public WaveLoadingView setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(textColor);
        return this;
    }

    public WaveLoadingView setTextSize(int size){
        this.textSize = size;
        textPaint.setTextSize(textSize);
        return this;
    }

    public WaveLoadingView setWaveColor(int color) {
        this.waveColor = color;
        wavePaint.setColor(waveColor);
        return this;
    }

    /**
     *
     * @param a
      波浪振幅， 默认为 20
     */
    public WaveLoadingView setAmplitude(int a) {
        this.a = a;
        this.a2 = a;
        return this;
    }

    /**
     *
     * @param w
     *            默认为0.5
     */
    public WaveLoadingView setPalstance(float w) {
        this.w = w;
        this.w2 = w;
        return this;
    }

    /**
     *
     * @param ms
     *            默认为4毫秒
     */
    public WaveLoadingView setRefreshTime(int ms) {
        this.ms = ms;
        return this;
    }

    public void setOnFinishedListener(OnFinishedListener l) {
        this.listener = l;
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            while (isRun) {
                fai++;
                fai2++;
                if (k > targetHeight) {
                    k -= 0.5;
                    progress = (int) ((baseBlank - k) / baseBlank * 100);
                    if (textHeight > (height / 2)) {
                        textHeight -= 0.5;
                    }
                }
                if (progress >= 100 && listener != null) {
                    listener.onFinished();
                    isRun = false;
                }
                if (fai == 360) {
                    fai = 0;
                }

                //path2
                if (k2 > targetHeight)
                    k2 -= 0.5;
                if (fai2 == 360)
                    fai2 = 0;

                mHandler.sendEmptyMessage(1);
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                invalidate();
            }
        }
    };

    public interface OnFinishedListener {
        public void onFinished();
    }

}
