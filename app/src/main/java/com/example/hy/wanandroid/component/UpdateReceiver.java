package com.example.hy.wanandroid.component;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.InstallApkEvent;
import com.example.hy.wanandroid.event.OpenBrowseEvent;
import com.example.hy.wanandroid.utlis.LogUtil;
import com.example.hy.wanandroid.utlis.ToastUtil;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 下载回调广播
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdateReceiver extends BroadcastReceiver {

    private static final String TAG = "UpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //下载完成
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            long saveId = App.getContext().getAppComponent().getDataModel().getDownloadId();
            if(downloadApkId == saveId){
                checkDownloadStatus(context, downloadApkId);
            }
            context.stopService(new Intent(context, UpdateService.class));
        }
    }

    /**
     * 查询下载完成文件的状态
     */
    private void checkDownloadStatus(Context context, long downloadId) {
        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = manager.query(query);
         if (cursor.moveToFirst()) {
             int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
             switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL://下载完成
                    Uri apkDownloadedUri = manager.getUriForDownloadedFile(downloadId);
                    LogUtil.d(TAG, "checkDownloadStatus: STATUS_SUCCESSFUL, uri = " + apkDownloadedUri);
                    RxBus.getInstance().post(new InstallApkEvent(apkDownloadedUri.toString()));
                     break;
                case DownloadManager.STATUS_FAILED://下载失败
                    ToastUtil.showCustomToastInBottom(context, context.getString(R.string.download_fail));
                    String apkDownloadUrl = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                    LogUtil.d(TAG, "checkDownloadStatus: STATUS_FAILED, url = " + apkDownloadUrl);
                    RxBus.getInstance().post(new OpenBrowseEvent(apkDownloadUrl));
                    break;
                case DownloadManager.STATUS_RUNNING://正在下载
                    LogUtil.d(TAG, "checkDownloadStatus: STATUS_RUNNING");
                     break;
                default:
                    LogUtil.d(TAG, "checkDownloadStatus: state = " + status);
                    break;
             }
         }
    }

}
