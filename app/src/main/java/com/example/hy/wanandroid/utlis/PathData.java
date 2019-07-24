package com.example.hy.wanandroid.utlis;

import android.graphics.Path;

/**
 * Path路径
 * Created by 陈健宇 at 2019/7/24
 */
public class PathData {

    public Path pathData;
    public int fillColor;
    public int stokeColor;
    public float stokeWidth;

    public PathData() {
    }

    public PathData(Path pathData, int fillColor, int stokeColor, float stokeWidth) {
        this.pathData = pathData;
        this.fillColor = fillColor;
        this.stokeColor = stokeColor;
        this.stokeWidth = stokeWidth;
    }

    @Override
    public String toString() {
        return "PathData[" +
                "pathData = " + pathData +
                ", fillColor = " + fillColor +
                ", stokeColor = " + stokeColor +
                ", stokeWidth = " + stokeWidth;
    }

}
