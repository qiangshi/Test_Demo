package com.supermap.demo.test.manager;

import com.supermap.demo.test.AppLike;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.database.HistoryDBDao;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/11 15:39
 */
public class HistoryManager {

    public static HistoryManager getInstance(){
        return Holder.instance;
    }

    private static class  Holder{
        private static final HistoryManager instance = new HistoryManager();
    }


    /**
     * 获取用户下的指定历史数据
     *
     * @return
     */
    public List<HistoryDB> queryHistoryList() {
        String userId = UserDBManager.getInstance().getUserId();
        List<HistoryDB> historyDBList = new ArrayList<>();
        try {
            clearDao();
            historyDBList = AppLike.getDaoInstance().getHistoryDBDao().queryBuilder().where(HistoryDBDao.Properties.UserId.eq(userId))
                    .orderDesc(HistoryDBDao.Properties.SearchTime).list();
            if (historyDBList == null) {
                historyDBList = new ArrayList<>();
            }
            return historyDBList;
        } catch (Exception e) {
            return historyDBList;
        }

    }


    public HistoryDB queryHistoryBySearchName(String searchName,int historyType){
        String userId = UserDBManager.getInstance().getUserId();
        List<HistoryDB> historyDBs = new ArrayList<>();
        clearDao();
        historyDBs = AppLike.getDaoInstance().getHistoryDBDao().queryBuilder().where(HistoryDBDao.Properties.UserId.eq(userId))
                .where(HistoryDBDao.Properties.Name.eq(searchName))
                .where(HistoryDBDao.Properties.HistoryType.eq(historyType)).list();
        if(historyDBs != null && historyDBs.size()> 0){
            return historyDBs.get(0);
        }
        return null;
    }


    private void clearDao() {
        AppLike.getDaoInstance().clear();
    }

    /**
     * 插入历史列表的数据
     *
     * @param list
     */
    public void insertHistorys(List<HistoryDB> list) {
        AppLike.getDaoInstance().getHistoryDBDao().insertInTx(list);
    }

    /**
     * 插入单个历史数据的数据
     *
     * @param historyDB
     */
    public void insertHistory(HistoryDB historyDB) {
        if(historyDB != null){
            HistoryDB bean;
            bean = queryHistoryBySearchName(historyDB.getName(),historyDB.getHistoryType());
            if(bean == null){
                AppLike.getDaoInstance().getHistoryDBDao().insertInTx(historyDB);
            }else {
                bean.setSearchTime(historyDB.getSearchTime());
                upDateHistory(bean);
            }
        }
    }

    public void clearHistoryData(){
        AppLike.getDaoInstance().getHistoryDBDao().deleteAll();
    }

    /**
     * 删除收藏数据
     *
     * @param historyDB
     */
    public void deleteHistory(HistoryDB historyDB) {
        AppLike.getDaoInstance().getHistoryDBDao().deleteInTx(historyDB);
    }

    /**
     * 删除收藏数据
     */
    public void deleteHistory(List<HistoryDB> list) {
        AppLike.getDaoInstance().getHistoryDBDao().deleteInTx(list);
    }


    public void updaHistoryList(List<HistoryDB> list) {
        AppLike.getDaoInstance().getHistoryDBDao().updateInTx(list);
    }

    public void upDateHistory(HistoryDB historyDB) {
        AppLike.getDaoInstance().getHistoryDBDao().updateInTx(historyDB);
    }

}
