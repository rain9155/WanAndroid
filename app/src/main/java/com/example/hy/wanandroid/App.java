package com.example.hy.wanandroid;

import android.content.ComponentCallbacks2;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.component.DaggerAppComponent;
import com.example.hy.wanandroid.di.module.CommonModule;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.GlideApp;
import com.example.hy.wanandroid.utlis.LanguageUtil;
import com.example.hy.wanandroid.utlis.ThemeUtil;
import com.example.hy.wanandroid.widget.dialog.LanguageDialog;
import com.example.loading.Loading;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by 陈健宇 at 2018/10/20
 */
public class App extends LitePalApplication {

    private static App mContext;

    //static代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context); //经典Header
        }));
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(((context, layout) ->
                new ClassicsFooter(context).setDrawableSize(20))); //经典Footer
    }

    private AppComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().commonModule(new CommonModule(this)).build();
        mContext = this;
        initLitepal();
        initBugly();
        initLeakCanary();
        Loading.beginBuildCommit()
                .addErrorView(R.layout.error_view)
                .addLoadingView(R.layout.loading_view)
                .addEmptyView(R.layout.empty_view)
                .commit();
        ThemeUtil.setTheme(this, getAppComponent().getDataModel().getSelectedTheme());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //当应用所有UI隐藏时应该释放UI上所有占用的资源
        if(ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN == level) {
            GlideApp.get(this).clearMemory();
        }
        //根据level级别来清除一些图片缓存
        GlideApp.get(this).onTrimMemory(level);
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void initLitepal() {
        LitePal.getDatabase();
    }

    private void initBugly() {
        if(BuildConfig.DEBUG) {
            return;
        }
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = CommonUtil.getProcessName(context, android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_ID, true);
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public static App getContext(){
        return mContext;
    }
}
