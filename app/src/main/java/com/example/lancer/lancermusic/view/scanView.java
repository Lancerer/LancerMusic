package com.example.lancer.lancermusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class scanView extends View {
    private int AddDegress = 1;//每次转动增加的角度
    private int w, h;//屏幕宽高
    private Paint paint;
    private Paint mPaint;//绘制渐变圆画笔
    private float degrees;
    private Matrix mMatrix;
    private Shader mShader;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (degrees > 360){
                degrees = 0;
            }
            degrees += AddDegress;
            mMatrix.postRotate(degrees,w/6,h/6);
            invalidate();
            mHandler.postDelayed(mRunnable,50);
        }
    };

    public scanView(Context context) {
        super(context);

    }

    public scanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        h = context.getResources().getDisplayMetrics().heightPixels;
        w = context.getResources().getDisplayMetrics().widthPixels;
        initPaint();
        mHandler.post(mRunnable);

    }
    public scanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(4);//设置画笔的宽度
        paint.setColor(Color.parseColor("#ffffff"));//设置画笔的颜色
        paint.setAntiAlias(true);//设置画笔光滑
        paint.setStyle(Paint.Style.STROKE);//设置画笔画空心圆

        mPaint = new Paint();
        mPaint.setColor(0x88888888);
        mPaint.setAntiAlias(true);
        //使用shader来实现一个渐变的效果
        mShader = new SweepGradient(w/6,h/6,Color.TRANSPARENT,Color.parseColor("#AAAAAAAA"));
        mPaint.setShader(mShader);
        //创建一个Matrix对象实现实心渐变圆的旋转
        mMatrix = new Matrix();
    }
   

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0;i < 4;i++){
            canvas.drawCircle(w/6,h/6,(i+1)*h/32,paint);
        }
        canvas.concat(mMatrix);//把Matrix和canvas关联起来
        //绘制一个渐变的实心圆
        canvas.drawCircle(w/6,h/6,h/6,mPaint);
        mMatrix.reset();
    }
}
