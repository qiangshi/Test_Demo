package com.supermap.demo.test.helper;

import android.database.Cursor;

import com.supermap.demo.test.database.DaoMaster;
import com.supermap.demo.test.database.FavoriteDB;
import com.supermap.demo.test.database.FavoriteDBDao;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.database.UserDBDao;
import com.supermap.demo.test.utils.MLog;

import org.greenrobot.greendao.database.StandardDatabase;

import java.util.ArrayList;
import java.util.List;


public class UpdateSQLHelper {
    private static final String TAG = UpdateSQLHelper.class.getSimpleName();

    public static List<UserDB> listOldUserDb = new ArrayList<>();//用户
    public static List<FavoriteDB> listOldFavoriteDB=new ArrayList<>();//收藏

    public static void upgradeTable(StandardDatabase db) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + UserDBDao.TABLENAME, null);
            while (cursor.moveToNext()) {
                UserDB mUserDB = new UserDB();
                mUserDB.userId = cursor.getString(cursor.getColumnIndex("USER_ID"));
                mUserDB.userName = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                mUserDB.passWord = cursor.getString(cursor.getColumnIndex("PASS_WORD"));
                listOldUserDb.add(mUserDB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            cursor = db.rawQuery("select * from " + FavoriteDBDao.TABLENAME, null);
            while (cursor.moveToNext()) {
                FavoriteDB mFavoriteDB=new FavoriteDB();
                mFavoriteDB.userId= cursor.getString(cursor.getColumnIndex("USER_ID"));
                mFavoriteDB.saveTime= cursor.getLong(cursor.getColumnIndex("SAVE_TIME"));
                mFavoriteDB.favoriteType= cursor.getInt(cursor.getColumnIndex("FAVORITE_TYPE"));
                mFavoriteDB.favoriteId = cursor.getString(cursor.getColumnIndex("FAVORITE_ID"));
                listOldFavoriteDB.add(mFavoriteDB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MLog.e(TAG,  "--------------数据库迁移--listOldUserDb-"+listOldUserDb.size()+"------");
        MLog.e(TAG,  "--------------数据库迁移--listOldFavoriteDB-"+listOldFavoriteDB.size()+"------");

        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, false);
        if (cursor != null) cursor.close();
    }
}
