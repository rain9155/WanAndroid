package com.example.hy.wanandroid.widget.customView;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.hy.wanandroid.R;

/**
 * 会抖动的字
 * Created by 陈健宇 at 2019/7/24
 */
public class WaveTextView extends View {

    private static final int A = 200;

    private String mText;
    private float mTextSize;
    private int mTextColor;

    private Paint mTextPaint;
    private Path mTextPath;
    private ValueAnimator mValueAnimator;
    private float mTextLen;
    private float a, m;

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
        mTextSize = typedArray.getDimension(R.styleable.WaveTextView_size, R.dimen.sp_26);
        mTextColor = typedArray.getColor(R.styleable.WaveTextView_color, Color.BLACK);
        typedArray.recycle();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(1);

        mTextPath = new Path();

        //正弦函数的一个周期
        mValueAnimator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            m = progress;
            a = (float) (1 - progress / (2 * Math.PI)) * A;
            invalidate();
        });
        mValueAnimator.setDuration(1000);

        mTextLen = mTextPaint.measureText(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(measureWidthMode);
        int measureHeight = MeasureSpec.getSize(measureHeightMode);
        //文字的默认宽高
        int defaultWidth, defaultHeight;
        defaultWidth = (int) mTextLen;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        defaultHeight = fontMetrics.bottom - fontMetrics.top;
        setMeasuredDimension(
                measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : defaultWidth,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : defaultHeight
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mTextPath.reset();


    }

    /**
     * 计算 y 轴值: y = A * sin(w * x + m) + k，这里k = 0
     * A：管理 正弦函数 的振幅，即上下摆动的幅度
     * w：管理 正弦函数 的水平收缩幅度
     * m：管理 正弦函数 的水平偏移量
     * k：管理 正弦函数 的垂直偏移量
     */
    private float calculateY(float x) {
        double b = Math.pow(4 / (4 + Math.pow(4 * x / mTextLen, 4)), 2.5f) * a;
        return (float) (b * Math.sin(Math.PI * x / 200 - m));
    }


}
