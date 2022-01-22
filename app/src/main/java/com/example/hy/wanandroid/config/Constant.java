package com.example.hy.wanandroid.config;
import android.os.Environment;

import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.utlis.FileUtil;

import java.io.File;

/**
 * 网路请求的base地址
 * Created by 陈健宇 at 2018/10/26
 */
public class Constant {

    //Url
    public final static String BASE_URL = "https://www.wanandroid.com/";
    public final static String BASE_APK_URL = "https://github.com/rain9155/WanAndroid/releases/download/";

    //MainActivity
    public static final int EXIT_WAIT_TIME = 2000;

    //common, 1~10
    public static final int REQUEST_LOGIN = 1;
    public static final String KEY_URL_APK = "urlApk";
    public static final String KEY_DOWNLOAD_ID = "downloadId";
    public static String NEW_VERSION_URL = "";

    //HierarchyFragment
    public static final String KEY_HIERARCHY_PAGENUM = "hierarchyPageNum";
    public static final String KEY_PROJECT_ID = "projectId";

    //WeChatFragment
    public static final String KEY_WECHAT_ID = "wechatId";

    //HierarchyActivity
    public static final String KEY_HIERARCHY_ID = "hierarchyId";
    public static final String KEY_HIERARCHY_NAMES = "hierarchyNames";
    public static final String KEY_HIERARCHY_NAME = "hierarchyName";

    //CoinsRankActivity
    public static final String KEY_COIN_RANK = "coinRank";
    public static final String URL_COIN_RANK_RULE = BASE_URL + "blog/show/2653";

    //HomeFragment, 11 ~ 20
    public static final int REQUEST_REFRESH_ARTICLE = 11;

    //MineFragment 21~30
    public static final int REQUEST_PICK_IMAGE_CHOOSER = 21;
    public static final int REQUEST_CROP_IMAGE_ACTIVITY = 22;
    public static final int CHANGE_FACE = 23;
    public static final int CHANGE_BACK = 24;
    public static final int CHANGE_NO = 25;

    //ArticleActivity
    public static final String KEY_ARTICLE_FLAG = "articleFlag";
    public static final String KEY_DATA_RETURN = "dataReturn";
    public static final String KEY_ARTICLE_BEAN = "articleBean";

    //CropperImageActivity
    public static final String KEY_IMAGE_URI = "imageUri";
    public static final String KEY_IMAGE_OPTIONS = "imageOptions";
    public static final String KEY_CROP_BUNDLE = "cropBundle";
    public static final String KEY_IS_FACE = "isFace";

    //settingActivity
    public static final String SHAREDPREFERENCES_NAME = "prefs";
    public static final String KEY_PREFS_CUR_MAIN_ITEM = "curMainItem";
    public static final String KEY_PREFS_CUR_WECHAT_ITEM = "curWechatItem";
    public static final String KEY_PREFS_CUR_PROJECT_ITEM = "curProjectItem";
    public static final String KEY_PREFS_NO_IMAGE = "noImage";
    public static final String KEY_PREFS_AUTO_CACHE = "autoCache";
    public static final String KEY_PREFS_STATUS_BAR = "statusBar";
    public static final String KEY_PREFS_NETWORK = "netWork";
    public static final String KEY_PREFS_AUTO_UPDATA = "autoUpdata";
    public static final String KEY_PREFS_LAN = "language";
    public static final String KEY_PREFS_THEME = "theme";

    //utils
    public static final String EMAIL_ADDRESS = "jianyu9155@gmial.com";

    //bugly
    public static final String BUGLY_ID = "a0768ddf34";

    //path
    public static final String PATH_NET_CACHE = FileUtil.getCachePath(App.getContext(), "netData");
    public static final String PATH_IMAGE_FACE = FileUtil.getFilePath(App.getContext(), "image");
    public static final String PATH_IMAGE_BACKGROUND = FileUtil.getFilePath(App.getContext(), "image");
    public static final String APK_NAME = "app-release.apk";
    public static final String PATH_APK = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + APK_NAME;
    public static final String FACE = "face.jpeg";
    public static final String BACK = "background.jpeg";

}
