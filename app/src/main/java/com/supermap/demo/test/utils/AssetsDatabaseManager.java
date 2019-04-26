package com.supermap.demo.test.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zenghaiqiang on 2018/4/5.
 * 描述：将数据库文件拷贝到data/data/{包名}/database文件夹下
 */
public class AssetsDatabaseManager {
    private static String tag = "AssetsDatabase"; // for LogCat
    private static String databasepath = "/data/data/%s/database"; // %s is packageName


    // A mapping from assets database file to SQLiteDatabase object
    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();

    // Context of application
    private Context context = null;

    // Singleton Pattern
    private static AssetsDatabaseManager mInstance = null;

    /**
     * Initialize AssetsDatabaseManager
     *
     * @param context, context of application
     */
    public static void initManager(Context context) {
        if (mInstance == null) {
            mInstance = new AssetsDatabaseManager(context);
        }
    }

    /**
     * Get a AssetsDatabaseManager object
     *
     * @return, if success return a AssetsDatabaseManager object, else return null
     */
    public static AssetsDatabaseManager getManager() {
        return mInstance;
    }

    private AssetsDatabaseManager(Context context) {
        this.context = context;
    }

    /**
     * Get a assets database, if this database is opened this method is only return a copy of the opened database
     *
     * @param dbfile, the assets file which will be opened for a database
     * @return, if success it return a SQLiteDatabase object else return null
     */
    public SQLiteDatabase getDatabase(String dbfile) {
        if (databases.get(dbfile) != null) {
            Log.i(tag, String.format("Return a database copy of %s", dbfile));
            return (SQLiteDatabase) databases.get(dbfile);
        }
        if (context == null)
            return null;

        Log.i(tag, String.format("Create database %s", dbfile));
        String spath = getDatabaseFilepath();
        String sfile = getDatabaseFile(dbfile);

        File file = new File(sfile);
        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);
        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid
        if (!flag || !file.exists()) {
            file = new File(spath);
            if (!file.exists() && !file.mkdirs()) {
                Log.i(tag, "Create \"" + spath + "\" fail!");
                return null;
            }
            if (!copyAssetsToFilesystem(dbfile, sfile)) {
                Log.i(tag, String.format("Copy %s to %s fail!", dbfile, sfile));
                return null;
            }

            dbs.edit().putBoolean(dbfile, true).commit();
        }

        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        if (db != null) {
            databases.put(dbfile, db);
        }
        return db;
    }

    private String getDatabaseFilepath() {
        return String.format(databasepath, context.getApplicationInfo().packageName);
    }

    /**
     * 获取内部数据库存储路径
     *
     * @param dbfile
     * @return
     */
    private String getDatabaseFile(String dbfile) {
        return getDatabaseFilepath() + "/" + dbfile;
    }

    /**
     * 获取sd卡存储路径
     *
     * @param filePath
     * @return
     */
    public String getFileSD(String filePath) {
        return android.os.Environment.getExternalStorageDirectory().getAbsolutePath()  + filePath;
    }

    public boolean copyAssetsToFilesystem(String assetsSrc, String des) {
        Log.i(tag, "Copy " + assetsSrc + " to " + des);
        InputStream istream = null;
        OutputStream ostream = null;
        try {
            AssetManager am = context.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(new File(des));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer)) > 0) {
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (istream != null)
                    istream.close();
                if (ostream != null)
                    ostream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * Close assets database
     *
     * @param dbfile, the assets file which will be closed soon
     * @return, the status of this operating
     */
    public boolean closeDatabase(String dbfile) {
        if (databases.get(dbfile) != null) {
            SQLiteDatabase db = (SQLiteDatabase) databases.get(dbfile);
            db.close();
            databases.remove(dbfile);
            return true;
        }
        return false;
    }

    /**
     * Close all assets database
     */
    static public void closeAllDatabase() {
        Log.i(tag, "closeAllDatabase");
        if (mInstance != null) {
            for (int i = 0; i < mInstance.databases.size(); ++i) {
                if (mInstance.databases.get(i) != null) {
                    mInstance.databases.get(i).close();
                }
            }
            mInstance.databases.clear();
        }
    }


    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath) {
        try {
            String fileNames[] = context.getAssets().list(assetsPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(savePath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName,
                            savePath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(assetsPath);
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

