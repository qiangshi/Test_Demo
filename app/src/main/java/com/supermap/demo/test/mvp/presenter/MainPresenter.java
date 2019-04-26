package com.supermap.demo.test.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.helper.BDAbsLocationListener;
import com.supermap.demo.test.manager.HistoryManager;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.map.bean.DataPagingModel;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.map.dataservice.FullSearchService;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.mvp.view.MainView;
import com.supermap.demo.test.ui.adapter.HistoryAdapter;
import com.supermap.demo.test.utils.MLog;
import com.supermap.mapping.Map;

import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

/**
 * @ClassName: MainPresenter
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 16:31
 */
public class MainPresenter extends BasePresenter<MainView> {
    private int pageType;//页面类型   0 ：主界面   1 ： 从周边回来的搜索结果页  2 : 从搜索界面回来的结果界面
    private double centerX,centerY;
    private double radius = 1000;
    private String year ;
    private boolean isBack;
    private float latitude,longitude;

    public MainPresenter(MainView view) {
        super(view);
    }

    /**
     * 根据关键字搜索
     * @param searchName
     * @param currPage
     */
    public void doSearchBySearchName(String searchName, int currPage) {
        if (!TextUtils.isEmpty(searchName)) {
            HistoryDB historyDB = new HistoryDB(UserDBManager.getInstance().getUserId(), System.currentTimeMillis(), HistoryAdapter.TYPE_NORMAL, searchName);
            HistoryManager.getInstance().insertHistory(historyDB);
            FullSearchService searchService = new FullSearchService();
            DataPagingModel<FullSearchItem> pagingModel = searchService.searchKey(searchName, Constant.PAGE_SIZE, currPage);
            if (pagingModel != null && pagingModel.getDataList() != null && pagingModel.getDataList().size() > 0) {
                mView.getFullDataSuccess(pagingModel.getDataList(),pagingModel.isPageEnd(), pagingModel.getPage());
            } else {
                if(currPage <= 1){
                    mView.getFullDataFail();
                    if(!isBack) mView.toastMessage(R.string.no_data);
                }else {
                    mView.toastMessage(R.string.data_no_more);
                }
            }
        } else {
                mView.toastMessage(R.string.please_input_key);
        }
    }


    /**
     * 根据坐标 半径获取审批周边查询  年份
     */
    public void doSearchByRadius(double centerX, double centerY, double radius,String year, int currPage) {
        MLog.e(centerX+"====="+centerY+"====zhq====>searchName<"+"====year"+year+"===currPage"+currPage+"===="+radius);
        if (centerX != 0 && centerY != 0) {
            FullSearchService searchService = new FullSearchService();
            DataPagingModel<FullSearchItem> pagingModel = searchService.searchApprovalByRadius(3052.67138672,-10871.26916504,radius,year, Constant.PAGE_SIZE, currPage);
            if (pagingModel != null && pagingModel.getDataList() != null && pagingModel.getDataList().size() > 0) {
                mView.getFullDataSuccess(pagingModel.getDataList(),pagingModel.isPageEnd(), pagingModel.getPage());
            } else {
                if(currPage <= 1){
                    mView.getFullDataFail();
                    mView.toastMessage(R.string.no_data);
                }else {
                    mView.toastMessage(R.string.data_no_more);
                }
            }
        } else {
            mView.toastMessage(R.string.please_input_contain);
        }
    }


    /**
     * 根据坐标 半径获取控规周边查询  带年份  year为null 不进行筛选
     */
    private void doSearchByPlan(double centerX, double centerY, double radius,String year, int currPage) {
        MLog.e(centerX+"====="+centerY+"====zhq====>searchName<"+"====year"+year+"===currPage"+currPage+"===="+radius);
        if (centerX != 0 && centerY != 0) {
            FullSearchService searchService = new FullSearchService();
            DataPagingModel<FullSearchItem> pagingModel = searchService.searchPlanByRadius(3052.67138672,-10871.26916504,radius,year, Constant.PAGE_SIZE, currPage);
            if (pagingModel != null && pagingModel.getDataList() != null && pagingModel.getDataList().size() > 0) {
                mView.getFullDataSuccess(pagingModel.getDataList(),pagingModel.isPageEnd(), pagingModel.getPage());
            } else {
                if(currPage <= 1){
                    mView.getFullDataFail();
                    mView.toastMessage(R.string.no_data);
                }else {
                    mView.toastMessage(R.string.data_no_more);
                }
            }
        } else {
            mView.toastMessage(R.string.please_input_contain);
        }
    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, EditText editText, Map mMap) {
        if (resultCode == RESULT_OK) {
            String searchName = data.getStringExtra(Constant.SEARCH_NAME);
            int searchType = data.getIntExtra(Constant.SEARCH_TYPE,Constant.SEARCH_TYPE_SEARCHKEY);
            isBack = data.getBooleanExtra(Constant.IS_BACK,false);
            switch (requestCode) {
                case Constant.REQUEST_CODE_PERIPHERY://从周边搜索界面返回
                    radius = data.getDoubleExtra(Constant.SEARCH_RADIUS,1000);
                    centerX = mMap.getCenter().getX();  centerY = mMap.getCenter().getY();
                    year = data.getStringExtra(Constant.SEARCH_YEAR);
                    pageType = data.getIntExtra(Constant.PAGE_TYPE,Constant.PAGE_TYPE_PERIPHERY);
                    mView.onActivityResult(searchName,pageType,radius,searchType,year,null);
                    if(searchType != Constant.SEARCH_TYPE_POI && searchType != Constant.SEARCH_TYPE_BUSINESS && searchType != Constant.SEARCH_TYPE_MAIN){
                        editText.setText(searchName);
                        editText.setFocusable(false);
                        doSearch(searchName,searchType,1);
                    }
                    break;
                case Constant.REQUEST_CODE_SEARCH://从搜索界面返回
                    radius = data.getDoubleExtra(Constant.SEARCH_RADIUS,1000);
                    centerX = mMap.getCenter().getX();  centerY = mMap.getCenter().getY();
                    year = "";
                    pageType = data.getIntExtra(Constant.PAGE_TYPE,Constant.PAGE_TYPE_SEARCH);
                    HistoryDB historyDB = (HistoryDB) data.getSerializableExtra(Constant.SEARCH_HISTORYDB);
                    mView.onActivityResult(searchName,pageType,radius,searchType,year,historyDB);
                    if(searchType != Constant.SEARCH_TYPE_POI && searchType != Constant.SEARCH_TYPE_BUSINESS && searchType != Constant.SEARCH_TYPE_MAIN){
                        editText.setText(searchName);
                        editText.setFocusable(false);
                        doSearch(searchName,searchType,1);
                    }
                    break;
            }

        }
    }


    /**
     * 根据类型做相应的搜索操作
     * @param searchName  搜索词
     * @param searchType  搜索类型
     */
    public void doSearch(String searchName,int searchType,int currPage){
        MLog.e(currPage+"====zhq====>searchName<"+searchName+"====searchType"+searchType);
        switch (searchType){
            case Constant.SEARCH_TYPE_SEARCHKEY://关键词搜索
                if (!TextUtils.isEmpty(searchName)) {
                    doSearchBySearchName(searchName,currPage);
                } else {
                    mView.onActivityResult(searchName,pageType,radius,searchType,year,null);
                }
                break;
            case Constant.SEARCH_TYPE_APPROVAL://审批搜索
                doSearchByRadius(centerX,centerY,radius,year,currPage);
                break;
            case Constant.SEARCH_TYPE_PLAN://控规搜索
                doSearchByPlan(centerX,centerY,radius,year,currPage);
                break;
        }
    }

    public void setCurrLocation(Context context){
        initBdConfigure(context);
        // TODO: 2019/4/22 经纬度转换成地图的坐标 然后刷新地图
    }


    //初始化百度配置信息
    public void initBdConfigure(Context context) {
        LocationClient locationClient = new LocationClient(context); //百度地圖聲明
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("gcj02");
        option.setScanSpan(0);
        option.setOpenGps(false);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(true);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(bdListener);//注册百度监听函数
        locationClient.start();
    }

    /**
     * 百度地图定位监听器
     */
    private BDAbsLocationListener bdListener = new BDAbsLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = (float) bdLocation.getLatitude();    //获取纬度信息
            longitude = (float) bdLocation.getLongitude();
            MLog.e("====zhq====>111<"+latitude+"===>"+longitude);
            super.onReceiveLocation(bdLocation);
        }
    };
}
