package com.supermap.demo.test.ui.activity.search;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.HistoryDB;
import com.supermap.demo.test.manager.HistoryManager;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.ui.adapter.HistoryAdapter;
import com.supermap.demo.test.utils.StatusBar.StatusBarUtil;
import com.supermap.demo.test.view.SearchBarView;
import com.sankuai.waimai.router.annotation.RouterUri;
import com.supermap.data.Environment;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_SEARCH_RESULT})
public class SearchResultActivity extends BaseActivity {


    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rv_land)
    RecyclerView rvLand;
    private MapControl mMapControl;
    private Workspace mWorkspace;

    private String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected int getLayoutId() {
        Environment.initialization(this);
        return R.layout.activity_search_result;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        initTitle();
        initView();
        openMap();
    }

    private void initView(){
        mSearchBar.setOnEditTextChangeListener(new SearchBarView.EditTextCallback() {
            @Override
            public void onEditTextChange(String str) {

            }

            @Override
            public void onClickSoftWare(String str) {
                doSearch();
            }

            @Override
            public void onClearText() {

            }
        });
        rvLand.setLayoutManager(new LinearLayoutManager(this));
        List<HistoryDB> list = HistoryManager.getInstance().queryHistoryList();
        HistoryAdapter adapter = new HistoryAdapter(this,list);
        rvLand.setAdapter(adapter);
    }

    /**
     * 打开地图
     */
    private void openMap() {
        if (openWorkspace()) {
            //将地图显示控件和工作空间关联
            mMapControl = mapView.getMapControl();
            mMapControl.getMap().setWorkspace(mWorkspace);
            //打开工作空间中的第二幅地图
            String mapName = mWorkspace.getMaps().get(1);
            mMapControl.getMap().open(mapName);
            mMapControl.getMap().refresh();

        }
    }

    private boolean openWorkspace() {
        mWorkspace = new Workspace();
        WorkspaceConnectionInfo info = new WorkspaceConnectionInfo();
        info.setServer(rootPath + Constant.SD_SM_DB_WS);
        info.setType(WorkspaceType.SMWU);
        return mWorkspace.open(info);
    }



    @OnClick({R.id.btn_back, R.id.btn_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_title_right:
                doSearch();
                break;
        }
    }

    private void doSearch() {
        if(!TextUtils.isEmpty(mSearchBar.getInputText())){
            Toast.makeText(SearchResultActivity.this,"搜索"+ mSearchBar.getInputText(), Toast.LENGTH_SHORT).show();
        }else {
            toastMessage(R.string.please_input_key);
        }
    }
}
