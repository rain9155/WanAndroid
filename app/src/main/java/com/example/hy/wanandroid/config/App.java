package com.example.hy.wanandroid.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.component.DaggerAppComponent;
import com.example.hy.wanandroid.di.module.AppModule;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import androidx.appcompat.app.AppCompatDelegate;

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
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = LitePal.getDatabase();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mContext = this;
        initBugly();
    }

    private void initBugly() {
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
