package com.supermap.demo.test.ui.activity.search;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.MapMenuBean;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.manager.HistoryManager;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.map.MainMapTool;
import com.supermap.demo.test.map.bean.DataPagingModel;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.map.dataservice.FullSearchService;
import com.supermap.demo.test.mvp.presenter.search.SearchPresenter;
import com.supermap.demo.test.mvp.view.search.SearchView;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.ui.adapter.HistoryAdapter;
import com.supermap.demo.test.ui.adapter.SearchLandTypeAdapter;
import com.supermap.demo.test.utils.KeyBoardUtils;
import com.supermap.demo.test.utils.StatusBar.StatusBarUtil;
import com.supermap.demo.test.view.SearchBarView;
import com.sankuai.waimai.router.annotation.RouterUri;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_SEARCH})
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView, HistoryAdapter.OnItemClickListener, SearchLandTypeAdapter.OnItemClickListener {

    @BindView(R.id.rv_land_type)
    RecyclerView rvLandType;//土地类型recyclerView
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;//历史记录RecyclerView
    @BindView(R.id.rel_empty)
    RelativeLayout relEmpty;

    private boolean isEmpty = false,isBack;
    private HistoryDB historyDB;
    private int searchType;
    private int pageType;
    private HistoryAdapter historyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void initDataAndEvent() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        initTitle();
        initEditText();
        initRecycler();
        searchType = getIntent().getIntExtra(Constant.SEARCH_TYPE,0);
        isBack = getIntent().getBooleanExtra(Constant.IS_BACK,false);
        pageType = getIntent().getIntExtra(Constant.PAGE_TYPE,Constant.PAGE_TYPE_MAIN);
        String searchName = getIntent().getStringExtra(Constant.SEARCH_NAME);
        if(!TextUtils.isEmpty(searchName)){
            mSearchBar.getSearchEditText().setText(searchName);
            mSearchBar.getSearchEditText().setSelection(searchName.length());
        }
        KeyBoardUtils.closeKeybord(mSearchBar.getSearchEditText(), this);
    }

    private void initEditText() {
        mSearchBar.setOnEditTextChangeListener(new SearchBarView.EditTextCallback() {
            @Override
            public void onEditTextChange(String str) {//文字改变监听

            }

            @Override
            public void onClickSoftWare(String str) {//搜索监听
                doSearch();
            }

            @Override
            public void onClearText() {

            }
        });
    }

    /**
     * 初始化recycler
     */
    private void initRecycler() {
        rvLandType.setLayoutManager(new GridLayoutManager(this, 4));
        SearchLandTypeAdapter searchLandTypeAdapter = new SearchLandTypeAdapter(this);
        searchLandTypeAdapter.setOnItemClickListener(this);
        rvLandType.setAdapter(searchLandTypeAdapter);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        List<HistoryDB> historyDBList = HistoryManager.getInstance().queryHistoryList();
        historyAdapter = new HistoryAdapter(this, historyDBList);
        historyAdapter.setOnItemClickListener(this);
        rvHistory.setAdapter(historyAdapter);
    }

    @OnClick({R.id.btn_back, R.id.btn_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if(isBack) searchType = Constant.SEARCH_TYPE_SEARCHKEY;
                if (isEmpty) {
                    isEmpty = false;
                    mSearchBar.getSearchEditText().setText("");
                    relEmpty.setVisibility(View.GONE);
                    rvHistory.setVisibility(View.VISIBLE);
                    hideEmpty();
                } else {
                    topToMain("", searchType,true);
                }
                break;
            case R.id.btn_title_right:
                doSearch();
                break;
        }

    }


    private void doSearch() {
        if (!TextUtils.isEmpty(mSearchBar.getInputText())) {
            FullSearchService fullSearchService = new FullSearchService();
            DataPagingModel<FullSearchItem> pagingModel = fullSearchService.searchKey(mSearchBar.getInputText(), Constant.PAGE_SIZE, 0);
            if (pagingModel != null && pagingModel.getDataList() != null && pagingModel.getDataList().size() > 0) {
                HistoryDB historyDB = new HistoryDB(UserDBManager.getInstance().getUserId(), System.currentTimeMillis(), HistoryAdapter.TYPE_NORMAL, mSearchBar.getInputText());
                HistoryManager.getInstance().insertHistory(historyDB);
                KeyBoardUtils.closeKeybord(mSearchBar.getSearchEditText(), this);
                pageType = Constant.PAGE_TYPE_SEARCH;
                topToMain(mSearchBar.getInputText(), Constant.SEARCH_TYPE_SEARCHKEY,false);
            } else {
                rvHistory.setVisibility(View.GONE);
                relEmpty.setVisibility(View.VISIBLE);
                isEmpty = true;
                showEmpty(R.id.rel_empty);
            }
        } else {
            toastMessage(R.string.please_input_key);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isBack) searchType = Constant.SEARCH_TYPE_SEARCHKEY;
            if (isEmpty) {
                isEmpty = false;
                mSearchBar.getSearchEditText().setText("");
                relEmpty.setVisibility(View.GONE);
                rvHistory.setVisibility(View.VISIBLE);
                hideEmpty();
                return false;
            } else {
                topToMain("", searchType,true);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onItemClick(HistoryDB bean) {
        bean.setSearchTime(System.currentTimeMillis());
        historyDB = bean;
        HistoryManager.getInstance().upDateHistory(bean);
        pageType = Constant.PAGE_TYPE_SEARCH;
        if (bean.getHistoryType() == HistoryAdapter.TYPE_NORMAL) {
            topToMain(bean.getName(), Constant.SEARCH_TYPE_SEARCHKEY,false);
        }else if(bean.getHistoryType() == HistoryAdapter.TYPE_APPROVAL) {//业务数据
            MainMapTool.getInstance().locateBusiness(bean.getBid(),bean.getCoordX(), bean.getCoordY(), bean.getCategory());
            topToMain("",Constant.SEARCH_TYPE_BUSINESS,false);
        }else {//poi数据
            MainMapTool.getInstance().locatePoi(bean.getCoordX(), bean.getCoordY());
            topToMain("",Constant.SEARCH_TYPE_POI,false);
        }
    }

    @Override
    public void onTailClick() {//点击清空历史
        showNormalFragment("提示", "您确定要清空历史吗？", "取消", "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tv_cancel:
                        break;
                    case R.id.tv_commit:
                        historyAdapter.clearData();
                        HistoryManager.getInstance().clearHistoryData();
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClick(MapMenuBean bean, int pos) {
        if(pos == 0 || pos == 2){
            int type = 2;
            if (pos == 0) {
                type = Constant.SEARCH_TYPE_PLAN;
            } else if (pos == 2) {
                type = Constant.SEARCH_TYPE_APPROVAL;
            }
            pageType = Constant.PAGE_TYPE_SEARCH;
            topToMain("", type,false);
        }
    }


    public void topToMain(String searchName, int searchType,boolean back) {
        Intent intent = new Intent();
        intent.putExtra(Constant.SEARCH_NAME, searchName);
        intent.putExtra(Constant.SEARCH_TYPE, searchType);
        intent.putExtra(Constant.PAGE_TYPE,pageType);
        intent.putExtra(Constant.IS_BACK,back);
        if(searchType == Constant.SEARCH_TYPE_BUSINESS || searchType == Constant.SEARCH_TYPE_POI){
            intent.putExtra(Constant.SEARCH_HISTORYDB,historyDB);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
