package com.example.hy.wanandroid.widget.customView;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.hy.wanandroid.utlis.LogUtil;
import com.example.hy.wanandroid.R;


/**
 * 会抖动的字
 * Created by 陈健宇 at 2019/7/24
 */
public class WaveTextView extends View {

    private static final String TAG = WaveTextView.class.getSimpleName();
    private static final int SAMPLING_SIZE = 200;//屏幕上采样点的个数，数值越大越精确

    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int isBold;

    private Paint mTextPaint;
    private Path mTextPath;
    private ValueAnimator mValueAnimator;
    private int mTextHeight;
    private int mTextWidth;
    private int mTextCenter;
    private float a, φ, A = 100;
    private float[] mSamplingX = new float[SAMPLING_SIZE + 1];//采样点坐标
    private float[] mMapX = new float[SAMPLING_SIZE + 1];//把采样点坐标映射到[-2, 2]范围的坐标

    public WaveTextView(Context context) {
        super(context);
        init(context, null);
    }

    public WaveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WaveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveTextView);
        mText = typedArray.getString(R.styleable.WaveTextView_text);
        mTextSize = typedArray.getDimension(R.styleable.WaveTextView_size, 26);
        mTextColor = typedArray.getColor(R.styleable.WaveTextView_color, Color.BLACK);
        A = typedArray.getInteger(R.styleable.WaveTextView_amplitude, 100);
        isBold = typedArray.getInteger(R.styleable.WaveTextView_style, 0);
        typedArray.recycle();

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        if(isBold == 1) mTextPaint.setFakeBoldText(true);
        //mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTextPath = new Path();
        mTextWidth = (int) mTextPaint.measureText(mText);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        mTextCenter = rect.height() / 2;

        //φ的取值范围为正弦函数的一个周期：2π
        mValueAnimator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            φ = progress;
            a = (float) (1 - progress / (2 * Math.PI)) * A;
//            LogUtil.d(TAG, "addUpdateListener(), a = " + a + ", φ = " + φ);
            postInvalidate();
        });
        mValueAnimator.setDuration(2500);
        postDelayed(() -> mValueAnimator.start(), 100);

        for(int i = 0; i <= SAMPLING_SIZE; i++) {
            float gap = (float) mTextWidth / SAMPLING_SIZE;
            mSamplingX[i] = i * gap;
            mMapX[i] = mSamplingX[i] / (float) mTextWidth * 4 - 2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        //文字的默认宽高
        int defaultHeight, defaultWidth;
        defaultHeight = (int) (A * 2);
        defaultWidth = mTextWidth;
        if(measureHeight < defaultHeight) measureHeight = defaultHeight;
        if(measureWidth < defaultWidth) measureWidth = defaultWidth;
        setMeasuredDimension(
                measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : defaultWidth,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : defaultHeight
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //让字体在中间
        canvas.translate((float) (getWidth() - mTextWidth) / 2, (float) getHeight() / 2 +  mTextCenter);
        mTextPath.reset();
//        LogUtil.d(TAG, "onDraw(), width = " + canvas.getWidth() + ", mTextWidth = " + mTextWidth);
        for(int i = 0; i <= SAMPLING_SIZE; i++){
            float y = calculateY(mMapX[i]);
            float x = mSamplingX[i];
//            LogUtil.d(TAG, "onDraw() "
//                    + ", samplingX = " + mSamplingX[i]
//                    + ", mapX = " + mMapX[i]
//                    + ", x = " + x
//                    + ", y = " + y);
            if(i == 0){
                mTextPath.moveTo(x, y);
            }else {
                mTextPath.lineTo(x, y);
            }
        }
        canvas.drawTextOnPath(mText, mTextPath, 0, 0, mTextPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        mValueAnimator.cancel();
        mValueAnimator = null;
        super.onDetachedFromWindow();
    }

    /**
     * 计算 y 轴值: y = A * sin(ω * x + φ) + k
     * A： 表示振幅，对应正弦曲线的最大高度
     * ω：表示角速度，对应正弦曲线的水平收缩幅度，换成频率是 2πf
     * φ：表示初始相位，对应正弦曲线初始在 x 轴的偏移量
     * k： 表示垂直偏距，对应正弦曲线初始在 y 轴上的偏移量
     * 这里是：y = A * (4 / (4 + x^4))^2.5 * sin(0.75πx)
     */
    private float calculateY(float x) {
        //b为衰减函数 b = (4 / (4 + x^4))^2.5
        double b = Math.pow(4 / (4 + Math.pow(x, 4)), 2.5f);
        return (float) (a * b * Math.sin(0.75f * Math.PI * x - φ));
    }


}
