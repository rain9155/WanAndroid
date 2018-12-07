package com.example.hy.wanandroid.component;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.utils.LogUtil;
import com.example.hy.wanandroid.utils.ToastUtil;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 下载回调广播
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdataReceiver extends BroadcastReceiver {

    private Context mContext;
    private DownloadManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        //下载完成
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            LogUtil.d(LogUtil.TAG_COMMON, "下载完成！");
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
                    installApk(context);
                     break;
                case DownloadManager.STATUS_FAILED://下载失败
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

    /**
     * 安装应用
     */
    private void installApk(Context context) {
        File file = new File(Constant.PATH_APK);
        if (file.exists()) {
            Intent install = new Intent("android.intent.action.VIEW");
            Uri downloadFileUri = Uri.fromFile(file);
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }else {
            ToastUtil.showToast(context, context.getString(R.string.setup_fail));
        }
    }
}
