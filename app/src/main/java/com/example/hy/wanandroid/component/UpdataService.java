package com.example.hy.wanandroid.component;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.utils.ToastUtil;

import androidx.annotation.Nullable;

/**
 * 下载apk服务
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdataService extends Service {

    private DataModel mDataModel;
    private Context mContext = this;
    private BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataModel = App.getContext().getAppComponent().getDataModel();
        mReceiver = new UpdataReceiver();
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(Constant.KEY_URL_APK);
        if(!TextUtils.isEmpty(url)){
            long downloadId = downloadApk(url);
            mDataModel.setDownloadId(downloadId);
            ToastUtil.toastInBottom(mContext, mContext.getString(R.string.downloading), null);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 下载apk
     */
    private long downloadApk(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //创建目录, 外部存储--> Download文件夹
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS  , "WanAndroid.apk");
        //设置Notification的标题
        request.setTitle(mContext.getString(R.string.app_name));
        //设置描述
        request.setDescription(mContext.getString(R.string.downloading)) ;
        // 在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED ) ;
        //下载的文件可以被系统的Downloads应用扫描到并管理
        request.setVisibleInDownloadsUi( true ) ;
        //设置请求的Mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        request.setMimeType(mimeTypeMap.getMimeTypeFromExtension(url));
        //下载网络需求 - 手机数据流量、wifi
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI ) ;
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        return  downloadManager.enqueue(request);
    }
}
