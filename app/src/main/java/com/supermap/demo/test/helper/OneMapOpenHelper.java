package com.supermap.demo.test.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.supermap.demo.test.AppLike;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.DaoMaster.OpenHelper;
import com.supermap.demo.test.utils.MLog;

import org.greenrobot.greendao.database.StandardDatabase;

public class OneMapOpenHelper extends OpenHelper {

    public static boolean isVersion = false;

    public static OneMapOpenHelper getInstance(){
        return Holder.Instance;
    }

    private OneMapOpenHelper(Context context) {
        super(context, Constant.DB_NAME, null);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            MLog.e(newVersion + "#################数据库升级了##############################" + oldVersion);
            StandardDatabase mStandardDatabase = new StandardDatabase(db);
            UpdateSQLHelper.upgradeTable(mStandardDatabase);
            isVersion = true;
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }

    private static class Holder {
        private static final OneMapOpenHelper Instance = new OneMapOpenHelper(AppLike.getApp());
    }
}
