package com.supermap.demo.test.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by zenghaiqiang on 2019/4/6.
 * 描述：复制文件工具类
 */
public class AssetsFileCopyUtils {

    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private FileOperateCallback callback;
    private volatile boolean isSuccess;
    private String errorStr;

    public static AssetsFileCopyUtils getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final AssetsFileCopyUtils instance = new AssetsFileCopyUtils();
    }

    private AssetsFileCopyUtils() {
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                switch (msg.what){
                    case SUCCESS :
                        callback.onSuccess();
                        break;
                    case FAILED:
                        callback.onFailed(msg.obj.toString());
                        break;
                }
            }
        }
    };

    public AssetsFileCopyUtils copyAssetsToSD(Context context,final String srcPath, final String sdPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyAssetsToDst(context, srcPath, sdPath);
                if (isSuccess) handler.obtainMessage(SUCCESS).sendToTarget();
                else handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    private void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName,
                                dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName,
                                dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory(), dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }


    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

}
