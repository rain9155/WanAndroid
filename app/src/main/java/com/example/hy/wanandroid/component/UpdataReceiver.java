package com.example.hy.wanandroid.component;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.commonlib.utils.FileProvider7;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.InstallApkEvent;
import com.example.hy.wanandroid.event.OpenBrowseEvent;
import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.ToastUtil;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 下载回调广播
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdataReceiver extends BroadcastReceiver {

    private DownloadManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        //下载完成
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            long saveId = App.getContext().getAppComponent().getDataModel().getDownloadId();
            if(downloadApkId == saveId){
                checkDownloadStatus(context, downloadApkId);
            }
            context.stopService(new Intent(context, UpdataService.class));
        }
    }

    /**
     * 查询下载完成文件的状态
     */
    private void checkDownloadStatus(Context context, long downloadId) {
        mManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = mManager.query(query);
         if (cursor.moveToFirst()) {
             int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
             switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    LogUtil.d(LogUtil.TAG_COMMON, "下载完成！");
                    RxBus.getInstance().post(new InstallApkEvent());
                     break;
                case DownloadManager.STATUS_FAILED://下载失败
                    ToastUtil.toastInBottom(context, context.getString(R.string.download_fail));
                    RxBus.getInstance().post(new OpenBrowseEvent());
                    LogUtil.d(LogUtil.TAG_COMMON, "下载失败.....");
                    break;
                case DownloadManager.STATUS_RUNNING://正在下载
                     LogUtil.d(LogUtil.TAG_COMMON, "正在下载.....");
                     break;
                default:
                    break;
             }
         }
    }

}
