package com.example.hy.wanandroid.widget.customView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.core.content.ContextCompat;

import com.example.hy.wanandroid.utlis.LogUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.utlis.PathData;
import com.example.hy.wanandroid.utlis.PathParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * 手写SVG图片 - 背景图
 * Created by 陈健宇 at 2019/7/23
 */
public class SVGBgView extends View {

    private static final int PARSER_SUCCESS = 0x000;
    private static final int PARSER_ERROR = 0x001;
    private static final int START_DRAW = 0x002;
    private static final int ANIM_TIME = 2500;
    private static final String WIDTH = "android:width";
    private static final String HEIGHT = "android:height";
    private static final String VIEWPORT_HEIGHT = "android:viewportHeight";
    private static final String VIEWPORT_WIDTH = "android:viewportWidth";
    private static final String PATH = "path";
    private static final String FILL_COLOR = "android:fillColor";
    private static final String PATH_DATA = "android:pathData";
    private static final String STROKE_COLOR = "android:strokeColor";
    private static final String STROKE_WIDTH = "android:strokeWidth";

    private final static String TAG = SVGBgView.class.getSimpleName();

    private int mSvgResId = R.raw.ic_launcher_background;
    private List<PathData> mPathDataList;
    private float mCurPathDataIndex = 0;
    private RectF mSvgRealSize;
    private float mSvgWidth, mSvgHeight;
    private Paint mSvgPaint;
    private ValueAnimator mValueAnimator;

    private Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PARSER_SUCCESS:
                    LogUtil.d(TAG, "解析SVG成功，mSvgWidth = " + mSvgWidth + ", mSvgHeight = " + mSvgHeight + ", mSvgRealSize = " + mSvgRealSize);
                    sendEmptyMessage(START_DRAW);
                    break;
                case PARSER_ERROR:
                    LogUtil.d(TAG, "解析SVG错误，mSvgWidth = " + mSvgWidth + ", mSvgHeight = " + mSvgHeight + ", mSvgRealSize = " + mSvgRealSize);
                    mCurPathDataIndex = -1;
                    invalidate();
                    break;
                case START_DRAW:
                    LogUtil.d(TAG, "开始绘制");
                    mValueAnimator.setFloatValues(0, mPathDataList.size());
                    mValueAnimator.start();
                    break;
                default:
                    break;
            }
        }
    };

    public SVGBgView(Context context) {
        super(context);
        init();
    }

    public SVGBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SVGBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPathDataList = new ArrayList<>();
        mSvgRealSize = new RectF();

        mSvgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mValueAnimator = ValueAnimator.ofFloat(0);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurPathDataIndex = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(ANIM_TIME);

        new ParserThread().start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurPathDataIndex == -1) {
            @SuppressLint("DrawAllocation") Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launch_background);
            canvas.drawBitmap(defaultBitmap, 0, 0, mSvgPaint);
        } else {
            //放大画布
            canvas.scale(getWidth() / mSvgRealSize.width(), getHeight() / mSvgRealSize.height());
            //重绘
            int realIndex = (int) mCurPathDataIndex;
            for (int i = 0; i < realIndex; i++) {
                PathData path = mPathDataList.get(i);
                setPaintAttr(path);
                canvas.drawPath(path.pathData, mSvgPaint);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        mValueAnimator.cancel();
        mValueAnimator = null;
        super.onDetachedFromWindow();
    }

    private class ParserThread extends Thread{

        @Override
        public void run() {
            Document document = null;
            try {
                // 打开 svg 的输入流
                InputStream inputStream = getContext().getResources().openRawResource(mSvgResId);
                //使用DocumentBuilderFactory从xml文件生成DOM树
                document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
                inputStream.close();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "打开DocumentBuilderFactory出错， e = " + e.getMessage());
                mHandler.sendEmptyMessage(PARSER_ERROR);
                return;
            }

            //获取<vector>根节点
            Element root = document.getDocumentElement();
            //获取<vector>根节点属性
            float viewWidth = getRealSize(root.getAttribute(VIEWPORT_WIDTH));
            float viewHeight = getRealSize(root.getAttribute(VIEWPORT_HEIGHT));
            if(viewHeight == 0 || viewWidth == 0) mHandler.sendEmptyMessage(PARSER_ERROR);
            mSvgWidth = getRealSize(root.getAttribute(WIDTH));
            mSvgHeight = getRealSize(root.getAttribute(HEIGHT));

            //获取<pathData>结点列表
            NodeList pathNodeList = root.getElementsByTagName(PATH);
            List<PathData> pathDataList = new ArrayList<>();
            for(int i = 0; i < pathNodeList.getLength(); i++){
                //取出<pathData>结点
                Element pathNode = (Element) pathNodeList.item(i);

                //获取<pathData>结点属性
                Path pathData = PathParser.createPathFromPathData(pathNode.getAttribute(PATH_DATA));
                int fillColor, stokeColor;
                float stokeWidth;
                try {
                    fillColor = Color.parseColor(pathNode.getAttribute(FILL_COLOR));
                    stokeColor = Color.parseColor(pathNode.getAttribute(STROKE_COLOR));
                    stokeWidth = getRealSize(pathNode.getAttribute(STROKE_WIDTH));
                }catch (Exception e){
                    e.printStackTrace();
                    fillColor = ContextCompat.getColor(getContext(), R.color.colorFill);
                    stokeColor = -1;
                    stokeWidth = 0.8f;
                }
                //计算出pathData的 rect
                RectF rect = new RectF();
                //计算出pathData的大小
                pathData.computeBounds(rect, true);
                //计算出svg的实际大小
                mSvgRealSize.left = Math.min(mSvgRealSize.left, rect.left);
                mSvgRealSize.right = Math.max(mSvgRealSize.right, rect.right);
                mSvgRealSize.top = Math.min(mSvgRealSize.top, rect.top);
                mSvgRealSize.bottom = Math.max(mSvgRealSize.bottom, rect.bottom);

                pathDataList.add(new PathData(pathData, fillColor, stokeColor, stokeWidth));
            }
            if(pathDataList.isEmpty()){
                mHandler.sendEmptyMessage(PARSER_ERROR);
                return;
            }
            mPathDataList.clear();
            mPathDataList.addAll(pathDataList);
            mHandler.sendEmptyMessage(PARSER_SUCCESS);
        }
    }

    private void setPaintAttr(PathData pathData) {
        mSvgPaint.reset();
        if(pathData.stokeColor < 0){
            mSvgPaint.setStyle(Paint.Style.FILL);
            mSvgPaint.setColor(pathData.fillColor);

        }else {
            mSvgPaint.setStyle(Paint.Style.STROKE);
            mSvgPaint.setStrokeWidth(pathData.stokeWidth * 2);
            mSvgPaint.setColor(pathData.stokeColor);
        }
    }

    private float getRealSize(String attr){
        float readSize = 0;
        if(attr.endsWith("px")){
            readSize = Float.parseFloat(attr.substring(0, attr.length() - 2));
        }else if(attr.endsWith("dp")){
            readSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    Float.parseFloat(attr.substring(0, attr.length() - 2)),
                    getResources().getDisplayMetrics()
            );
        }else {
            try {
                readSize = Float.parseFloat(attr);
            }catch (Exception e){
                Log.e(TAG, "转化数值失败");
            }
        }
        return readSize;
    }
}
