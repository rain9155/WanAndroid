package com.example.hy.wanandroid.config;

/**
 * 网路请求的base地址
 * Created by 陈健宇 at 2018/10/26
 */
public class Constant {

    //Url
    public final static String BASE_URL = "http://www.wanandroid.com/";

    //HierarchyFragment
    public static final String KEY_HIERARCHY_PAGENUM = "hierarchyPageNum";
    public static final String KEY_PROJECT_ID = "projectId";

    //HierarchyActivity
    public static final String KEY_HIERARCHY_ID = "hierarchyId";
    public static final String KEY_HIERARCHY_NAMES = "hierarchyNames";
    public static final String KEY_HIERARCHY_NAME = "hierarchyName";

    //MainActivity
    public static final long WAIT_TIME = 2000L; // 再点一次退出程序时间设置
    public static long TOUCH_TIME = 0;//第一次按下返回键的时间

    //ArticleActivity
    public static final String KEY_ARTICLE_ADDRESS = "articleAddress";
    public static final String KEY_ARTICLE_FLAG = "articleFlag";
    public static final String KEY_ARTICLE_TITLE = "articleTitle";
    public static final String KEY_ARTICLE_ISCOLLECTION = "articleIsCollection";

    //BaseLoadState
    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

}
