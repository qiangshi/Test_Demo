package com.supermap.demo.test.manager;

import android.util.Log;

import com.supermap.demo.test.AppLike;
import com.supermap.demo.test.database.FavoriteDB;
import com.supermap.demo.test.database.FavoriteDBDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2019/4/7. 收藏列表的工具类
 */
public class FavoriteManager {

    public static FavoriteManager getInstance() {
        return Holder.Instance;
    }

    /**
     * 获取用户下的指定收藏数据
     *
     * @return
     */
    public List<FavoriteDB> queryFavoriteList(int isNet, String userId) {
        List<FavoriteDB> favoriteDBList = new ArrayList<>();
        try {
            clearDao();
            favoriteDBList = AppLike.getDaoInstance().getFavoriteDBDao().queryBuilder().where(FavoriteDBDao.Properties.UserId.eq(userId))
                    .where(FavoriteDBDao.Properties.FavoriteType.eq(isNet)).orderDesc(FavoriteDBDao.Properties.SaveTime).list();
            if (favoriteDBList == null) {
                favoriteDBList = new ArrayList<>();
                Log.e("error", "----queryFavoriteList--null--");
            }
            return favoriteDBList;
        } catch (Exception e) {
            return favoriteDBList;
        }

    }


    private void clearDao() {
        AppLike.getDaoInstance().clear();
    }

    /**
     * 保存收藏的数据
     *
     * @param list
     */
    public void insertFavorite(List<FavoriteDB> list) {
        AppLike.getDaoInstance().getFavoriteDBDao().insertInTx(list);
    }

    /**
     * 删除收藏数据
     *
     * @param favoriteId
     */
    public void deleteFavorite(String favoriteId) {
        String userId = UserDBManager.getInstance().getUserId();
        List<FavoriteDB> favoriteDBList = AppLike.getDaoInstance().getFavoriteDBDao().queryBuilder().where(FavoriteDBDao.Properties.UserId.eq(userId), FavoriteDBDao.Properties.FavoriteId.eq(favoriteId)).list();
        if (favoriteDBList != null && favoriteDBList.size() > 0)
            AppLike.getDaoInstance().getFavoriteDBDao().delete(favoriteDBList.get(0));
    }

    /**
     * 删除收藏数据
     */
    public void deleteFavorite(List<FavoriteDB> list) {
        AppLike.getDaoInstance().getFavoriteDBDao().deleteInTx(list);
    }


    public void updaFavoriteList(List<FavoriteDB> favoriteDBS) {
        AppLike.getDaoInstance().getFavoriteDBDao().updateInTx(favoriteDBS);
    }

    public void updaFavorite(FavoriteDB favoriteDB) {
        AppLike.getDaoInstance().getFavoriteDBDao().updateInTx(favoriteDB);
    }

    private static class Holder {
        private static final FavoriteManager Instance = new FavoriteManager();
    }
}
