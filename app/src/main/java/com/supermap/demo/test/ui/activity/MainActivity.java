package com.supermap.demo.test.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.helper.MapHelper;
import com.supermap.demo.test.map.DatasourceManager;
import com.supermap.demo.test.map.MainMapLayerControl;
import com.supermap.demo.test.map.MainMapTool;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.mvp.presenter.MainPresenter;
import com.supermap.demo.test.mvp.view.MainView;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.supermap.utils.SMIMobileInitializer;
import com.supermap.demo.test.supermap.utils.WorkspaceUtils;
import com.supermap.demo.test.ui.activity.test.GeometryInfoActivity;
import com.supermap.demo.test.ui.adapter.FullSearchAdapter;
import com.supermap.demo.test.ui.fragment.MenuFragment;
import com.supermap.demo.test.ui.fragment.tool.BusinessFragment;
import com.supermap.demo.test.ui.fragment.tool.LocationFragment;
import com.supermap.demo.test.utils.MLog;
import com.supermap.demo.test.utils.StatusBar.StatusBarUtil;
import com.supermap.demo.test.view.SearchBarView;
import com.sankuai.waimai.router.annotation.RouterUri;
import com.sankuai.waimai.router.common.DefaultUriRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.Map;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_MAIN})
public class MainActivity extends BaseActivity<MainPresenter> implements MainView, DrawerLayout.DrawerListener, OnLoadMoreListener, FullSearchAdapter.OnItemClickListener {

    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    @BindView(R.id.search_bar_title)
    SearchBarView searchBarTitle;
    @BindView(R.id.ll_right_menu)
    LinearLayout llRightMenu;
    @BindView(R.id.rv_land)
    RecyclerView rvLand;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout reFreshLayout;
    @BindView(R.id.v_title)
    View vTitle;
    @BindView(R.id.title)
    View title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rel_bottom)
    RelativeLayout relBottom;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_used)
    TextView tvUsed;
    @BindView(R.id.tv_start_end)
    TextView tvStartEnd;
    @BindView(R.id.img_approval)
    ImageView imgApproval;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.rel_poi)
    RelativeLayout includePoi;
    @BindView(R.id.rel_business)
    RelativeLayout includeBusiness;
    @BindView(R.id.rel_title)
    RelativeLayout relTitle;

    private MapControl mMapControl;
    private Map mMap;
    private Workspace mWorkspace;
    private MapHelper mapHelper;
    private boolean isExit = false;
    private int currPage = 1;//搜索结果的当前页
    private boolean isPageEnd;
    private String searchName;
    private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private double radius = 1000;
    private String year;
    private int lastSearchType;
    private boolean isPoiSelect = false;//是否是点选
    private boolean isBusinessSelect = false;
    private boolean isLoadMoreType = false;
    private int searchType = 0; // 0 ：  主界面    1 ： 搜索   2 ： 审批搜索   3 ： 控规搜索    4  ： 业务信息  5 : POI信息
    private FullSearchAdapter fullSearchAdapter;
    private List<FullSearchItem> fullSearchItems = new ArrayList<>();
    private int pageType;//页面类型   0 ：主界面   1 ： 从周边回来的搜索结果页  2 : 从搜索界面回来的结果界面
    private boolean isHasLast = false;//是否有上一层
    private HistoryDB history = new HistoryDB();
    //fragment
    private MenuFragment menuFragment;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
                case 1:
                    if(isPoiSelect || isBusinessSelect){

                    }else if (pageType == 0) {
                        initMain();
                    } else if (searchType == Constant.SEARCH_TYPE_BUSINESS) {//业务信息
                        if (history != null) initBusiness(history.getBid(), history.getLabel());
                    } else if (searchType == Constant.SEARCH_TYPE_POI) {
                        if (history != null) initPOI(history.getName(), history.getMix());
                    } else if(searchType == Constant.SEARCH_TYPE_MAIN ) {
                        initMain();
                    } else {
                        initSearchResult();
                    }
                    break;
            }

        }
    };

    @Override
    protected int getLayoutId() {
        initSuperMap();
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initDataAndEvent() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        initView();
        initMap();
        initFragment();
    }



    private void initFragment() {
        menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_bar_right_menu, menuFragment).commit();
    }

    private void initView() {
        initTitle(R.drawable.icon_set);
        mapHelper = MapHelper.getInstance(this);
        mSearchBar.setClearVisiable(false);
        initMain();
        mPresenter.initBdConfigure(this.getApplicationContext());
        rvLand.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fullSearchAdapter = new FullSearchAdapter(MainActivity.this, new ArrayList<>());
        fullSearchAdapter.setOnItemClickListener(this);
        rvLand.setAdapter(fullSearchAdapter);
        dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        dlMain.addDrawerListener(this);
        reFreshLayout.setEnableRefresh(false);
        reFreshLayout.setOnLoadMoreListener(this);
    }

    /*****************************************监听的方法************************************************/

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(2000);
        int type = isLoadMoreType ? lastSearchType : searchType;
        if (!isPageEnd) {
            mPresenter.doSearch(searchName, type, ++currPage);
        } else {
            toastMessage(R.string.data_no_more);
        }
    }

    @Override
    public void getFullDataSuccess(List<FullSearchItem> list, boolean isPageEnd, int currPage) {
        reFreshLayout.finishLoadMore();
        this.currPage = currPage;
        this.isPageEnd = isPageEnd;
        if (currPage > 1) {
            fullSearchItems.addAll(list);
        } else {
            rvLand.smoothScrollToPosition(0);
            fullSearchItems.clear();
            fullSearchItems.addAll(list);
        }
        fullSearchAdapter.setData(fullSearchItems);
    }

    @Override
    public void getFullDataFail() {
        pageType = 0;
        fullSearchAdapter.clearData();
        initMain();
    }

    @Override
    public void onActivityResult(String searchName, int pageType, double radius, int searchType,String year, HistoryDB historyDB) {
        currPage = 1; isHasLast = false;
        this.pageType = pageType;
        this.searchName = searchName;
        this.radius = radius;
        this.searchType = searchType;
        this.year = year;
        this.history = historyDB;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onItemClick(FullSearchItem bean) {
        isHasLast = true;
        isLoadMoreType = true;
        lastSearchType = searchType;
        if (bean.isPoiItem()) {//poi数据
            MainMapTool.getInstance().locatePoi(bean.getCoordX(), bean.getCoordY());
            initPOI(bean.getName(), bean.getMix());
        } else {//业务数据
            MainMapTool.getInstance().locateBusiness(bean.getBid(),bean.getCoordX(), bean.getCoordY(), bean.getCategory());
            initBusiness(bean.getBid(),bean.getLabel());
        }
    }

    /*****************************************监听的方法************************************************/

    private void initMap() {
        mMapControl = mapView.getMapControl();
        mMap = mMapControl.getMap();
        try {
            String wsFilePath = rootPath + Constant.SD_SM_DB_WS;
            mWorkspace = WorkspaceUtils.openFileWorkspace(wsFilePath, WorkspaceType.SMWU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 手势监听器
        mMapControl.setGestureDetector(new GestureDetector(this, new MapGestureListener()));
        mMapControl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMapControl.onMultiTouch(event);
                return false;
            }
        });
        mMap.setWorkspace(mWorkspace);
        mMap.open(Constant.SM_BUSINESS_MAP_NAME);
        mMap.refresh();
        DatasourceManager.initBusinessDatasource(mWorkspace);
        MainMapLayerControl.init(mMap);
        MainMapTool.init(this, mMap);
    }

    // 手势监听器
    class MapGestureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapConfirmed(MotionEvent e) {

            Point2D mapPoint2D = mMap.pixelToMap(new Point((int)e.getX(), (int)e.getY()));
            FullSearchItem bean = MainMapTool.getInstance().selectByPoint(mapPoint2D.getX(), mapPoint2D.getY(), mMap.getScale());
            MLog.e("====zhq====>1111<"+JSONObject.toJSONString(bean));
            if(bean != null){
                isLoadMoreType = true;
                lastSearchType = searchType;
                if (bean.isPoiItem()) {//poi数据
                    isPoiSelect = true;
                    initPOI(bean.getName(), bean.getMix());
                } else {//业务数据
                    isBusinessSelect = true;
                    initBusiness(bean.getBid(),bean.getLabel());
                }
            }
            return true;
        }

    }

    private void initSuperMap() {
        SMIMobileInitializer.initializeEnv(rootPath + Constant.SD_SM_LICENSE_DIR,
                rootPath + Constant.SD_SM_TEMP_DIR,
                rootPath + Constant.SD_SM_WEBCACHE_DIR,
                rootPath + Constant.SD_SM_FONTS_DIR,
                this);
    }

    @OnClick({R.id.ll_location, R.id.ll_copy_cir, R.id.ll_cpy, R.id.ll_picture,
            R.id.ll_favorite, R.id.ll_in, R.id.ll_out, R.id.v_title, R.id.rel_periphery,
            R.id.btn_title_right, R.id.btn_back, R.id.search_bar_title,R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                currPage = 1;
                mPresenter.setCurrLocation(this.getApplicationContext());
                toastMessage(R.string.development_ing);
                break;
            case R.id.ll_copy_cir://圆形选择
                toastMessage(R.string.development_ing);
//                startActivity(new Intent(this, GeometryInfoActivity.class));
                break;
            case R.id.ll_cpy://多边形选择
                toastMessage(R.string.development_ing);
                break;
            case R.id.ll_picture://图层
                dlMain.openDrawer(Gravity.RIGHT);
                break;
            case R.id.ll_in://放大
                mMap.zoom(2);
                mMap.refresh();
                MainMapTool.getInstance().refreshDynamicView();
                break;
            case R.id.ll_out://缩小
                mMap.zoom(0.5);
                mMap.refresh();
                MainMapTool.getInstance().refreshDynamicView();
                break;
            case R.id.v_title:
                new DefaultUriRequest(this, RouterCons.CREATE_SEARCH)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.IS_BACK, false)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .activityRequestCode(Constant.REQUEST_CODE_SEARCH)
                        .start();
                break;
            case R.id.btn_title_right://搜索结果页中的搜索  点击返回主界面
                if (pageType != 0 || isPoiSelect) {
                    isPoiSelect = false;
                    pageType = 0;
                    initMain();
                }
                break;
            case R.id.img_back:
            case R.id.btn_back://搜索结果页中的返回
                if(isPoiSelect){
                    isPoiSelect = false;
                    mapHelper.hideLocationFragment();
                    MainMapTool.getInstance().clearLocateMarker();
                    if(lastSearchType == Constant.SEARCH_TYPE_SEARCHKEY || lastSearchType == Constant.SEARCH_TYPE_APPROVAL
                            || lastSearchType == Constant.SEARCH_TYPE_PLAN){
                        initSearchResult();
                    }else {
                        initMain();
                    }
                    break;
                }
                if(isBusinessSelect){
                    isBusinessSelect = false;
                    mapHelper.hideBusinessFragment();
                    MainMapTool.getInstance().clearLocateMarker();
                    if(lastSearchType == Constant.SEARCH_TYPE_SEARCHKEY || lastSearchType == Constant.SEARCH_TYPE_APPROVAL
                      || lastSearchType == Constant.SEARCH_TYPE_PLAN){
                        initSearchResult();
                    }else {
                        initMain();
                    }
                    break;
                }
                if(isHasLast) {
                    isHasLast = false;
                    initSearchResult();
                    break;
                }
                if (pageType == 1) {
                    pageType = 0;
                    new DefaultUriRequest(this, RouterCons.CREATE_PERIPHERY)
                            .putExtra(Constant.PAGE_TYPE,pageType)
                            .putExtra(Constant.SEARCH_TYPE, searchType)
                            .putExtra(Constant.IS_BACK, true)
                            .putExtra(Constant.SEARCH_YEAR,year)
                            .putExtra(Constant.SEARCH_RADIUS,radius)
                            .activityRequestCode(Constant.REQUEST_CODE_PERIPHERY)
                            .start();
                } else if (pageType == 2) {
                    pageType = 0;
                    new DefaultUriRequest(this, RouterCons.CREATE_SEARCH)
                            .putExtra(Constant.SEARCH_TYPE, searchType)
                            .putExtra(Constant.PAGE_TYPE,pageType)
                            .putExtra(Constant.SEARCH_NAME,searchName)
                            .putExtra(Constant.IS_BACK, true)
                            .activityRequestCode(Constant.REQUEST_CODE_SEARCH)
                            .start();
                } else {//用户设置
                    new DefaultUriRequest(this, RouterCons.CREATE_USER)
                            .start();
                }
                break;
            case R.id.search_bar_title:
                break;

            case R.id.ll_favorite://收藏
                startActivity(new Intent(this, GeometryInfoActivity.class));
                break;
            case R.id.rel_periphery://周边
                new DefaultUriRequest(this, RouterCons.CREATE_PERIPHERY)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.IS_BACK, false)
                        .activityRequestCode(Constant.REQUEST_CODE_PERIPHERY)
                        .start();
                break;
        }
    }




    /**
     * 初始化搜索结果界面
     */
    public void initSearchResult() {
        includePoi.setVisibility(View.GONE);
        includeBusiness.setVisibility(View.GONE);
        relBottom.setVisibility(View.GONE);
        mBackBtn.setImageResource(R.drawable.icon_back);
        llRightMenu.setVisibility(View.GONE);
        reFreshLayout.setVisibility(View.VISIBLE);
        mRightBtn.setImageResource(R.drawable.icon_close_big);
        imgBack.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        vTitle.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化主界面
     */
    public void initMain() {
        searchType = 0;
        imgBack.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        vTitle.setVisibility(View.VISIBLE);
        llRightMenu.setVisibility(View.VISIBLE);
        includePoi.setVisibility(View.GONE);
        includeBusiness.setVisibility(View.GONE);
        relBottom.setVisibility(View.VISIBLE);
        searchBarTitle.getSearchEditText().setFocusable(false);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchBarTitle.getSearchEditText().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mBackBtn.setImageResource(R.drawable.icon_set);
        searchBarTitle.getSearchEditText().setText("");
        reFreshLayout.setVisibility(View.GONE);
        mRightBtn.setImageResource(R.drawable.icon_search);
    }

    /**
     * 初始化业务界面
     */
    public void initBusiness(int bid, String label) {
        searchType = Constant.SEARCH_TYPE_BUSINESS;
        includePoi.setVisibility(View.GONE);
        includeBusiness.setVisibility(View.VISIBLE);
        mapHelper.showBusinessFragment(R.id.rel_business, label, bid, new BusinessFragment.OnItemClickListener() {
            @Override
            public void onFavoriteClick() {
                toastMessage(R.string.favorite);
            }

            @Override
            public void onPeripheryClick() {
                new DefaultUriRequest(MainActivity.this, RouterCons.CREATE_PERIPHERY)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .putExtra(Constant.IS_BACK, false)
                        .activityRequestCode(Constant.REQUEST_CODE_PERIPHERY)
                        .start();
            }

            @Override
            public void onDetailClick(FullSearchItem bean) {
                new DefaultUriRequest(MainActivity.this, RouterCons.CREATE_DETAIL)
                        .start();
            }
        });
        title.setVisibility(View.GONE);
        vTitle.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        mBackBtn.setImageResource(R.drawable.icon_back);
        reFreshLayout.setVisibility(View.GONE);
        llRightMenu.setVisibility(View.GONE);
        mRightBtn.setImageResource(R.drawable.icon_close_big);
    }

    /**
     * 初始化POI界面
     */
    public void initPOI(String name, String mix) {
        searchType = Constant.SEARCH_TYPE_POI;
        includePoi.setVisibility(View.VISIBLE);
        includeBusiness.setVisibility(View.GONE);
        mapHelper.showLocationFragment(R.id.rel_poi, name, mix, new LocationFragment.OnItemClickListener() {
            @Override
            public void onFavoriteClick() {
                toastMessage(R.string.favorite);
            }

            @Override
            public void onPeripheryClick() {
                new DefaultUriRequest(MainActivity.this, RouterCons.CREATE_PERIPHERY)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .putExtra(Constant.IS_BACK, false)
                        .activityRequestCode(Constant.REQUEST_CODE_PERIPHERY)
                        .start();
            }
        });
        title.setVisibility(View.VISIBLE);
        vTitle.setVisibility(View.VISIBLE);
        imgBack.setVisibility(View.GONE);
        mBackBtn.setImageResource(R.drawable.icon_back);
        reFreshLayout.setVisibility(View.GONE);
        mRightBtn.setImageResource(R.drawable.icon_close_big);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data, mSearchBar.getSearchEditText(), mMap);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isPoiSelect){
                isPoiSelect = false;
                mapHelper.hideLocationFragment();
                MainMapTool.getInstance().clearLocateMarker();
                if(lastSearchType == Constant.SEARCH_TYPE_SEARCHKEY || lastSearchType == Constant.SEARCH_TYPE_APPROVAL
                        || lastSearchType == Constant.SEARCH_TYPE_PLAN){
                    initSearchResult();
                }else {
                    initMain();
                }
                return false;
            }
            if(isBusinessSelect){
                isBusinessSelect = false;
                mapHelper.hideBusinessFragment();
                MainMapTool.getInstance().clearLocateMarker();
                if(lastSearchType == Constant.SEARCH_TYPE_SEARCHKEY || lastSearchType == Constant.SEARCH_TYPE_APPROVAL
                        || lastSearchType == Constant.SEARCH_TYPE_PLAN){
                    initSearchResult();
                }else {
                    initMain();
                }
                return false;
            }
            if(isHasLast) {
                isHasLast = false;
                initSearchResult();
                return false;
            }
            if (pageType == 0) {
                exit();
                return false;
            } else if (pageType == 1) {
                pageType = 0;
                new DefaultUriRequest(this, RouterCons.CREATE_PERIPHERY)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.SEARCH_YEAR,year)
                        .putExtra(Constant.SEARCH_RADIUS,radius)
                        .putExtra(Constant.IS_BACK, true)
                        .activityRequestCode(Constant.REQUEST_CODE_PERIPHERY)
                        .start();
                return false;
            } else if (pageType == 2) {
                pageType = 0;
                new DefaultUriRequest(this, RouterCons.CREATE_SEARCH)
                        .putExtra(Constant.SEARCH_TYPE, searchType)
                        .putExtra(Constant.PAGE_TYPE,pageType)
                        .putExtra(Constant.SEARCH_NAME,searchName)
                        .putExtra(Constant.IS_BACK, true)
                        .activityRequestCode(Constant.REQUEST_CODE_SEARCH)
                        .start();
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            toastMessage(R.string.again_pass_logout_app);
            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        mMap.close();
        mMap.dispose();
        mMap = null;
        mMapControl.dispose();
        mMapControl = null;
        WorkspaceUtils.releaseWorkspace(mWorkspace);
        mWorkspace = null;
        super.onDestroy();
    }

}
