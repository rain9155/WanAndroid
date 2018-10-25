package com.example.hy.wanandroid.config;

import android.database.sqlite.SQLiteDatabase;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.component.DaggerAppComponent;
import com.example.hy.wanandroid.di.module.AppModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by 陈健宇 at 2018/10/20
 */
public class App extends LitePalApplication {

    //static 代码段可以防止内存泄露
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
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

}
