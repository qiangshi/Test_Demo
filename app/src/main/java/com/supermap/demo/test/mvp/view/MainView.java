package com.supermap.demo.test.mvp.view;

import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.map.bean.FullSearchItem;

import java.util.List;

/**
 * @ClassName: MainView
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 16:31
 */
public interface MainView  extends IView{

    /**
     * 获取关键字搜索数据成功
     * @param list
     */
    void  getFullDataSuccess(List<FullSearchItem> list,boolean isPageEnd,int currPage);

    void getFullDataFail();


    void  onActivityResult(String data, int pageType, double radius, int searchType, String year,HistoryDB bean);
}
