package com.supermap.demo.test.ui.activity.test;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Environment;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.SharePreferenceUtil;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.supermap.services.FeatureSet;
import com.supermap.services.QueryMode;
import com.supermap.services.QueryOption;
import com.supermap.services.QueryService;
import com.supermap.services.ResponseCallback;
import com.supermap.services.ServiceQueryParameter;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    @BindView(R.id.mapview)
    MapView m_mapView;
    @BindView(R.id.zoomControls1)
    ZoomControls m_Zoom;
    @BindView(R.id.btn_setting)
    Button m_btnSetting;
    @BindView(R.id.map_show)
    RelativeLayout mapShow;

    @BindView(R.id.edt_serverName)
    EditText m_edtServerName;

    @BindView(R.id.edt_mapName)
    EditText m_edtMapName;

    @BindView(R.id.edt_layerName)
    EditText m_edtLayerName;

    @BindView(R.id.btn_query)
    Button m_btnQuery;
    @BindView(R.id.edt_sql)
    EditText m_edtSql;
    @BindView(R.id.tb_search)
    RelativeLayout tbSearch;


    private MapControl m_mapControl; // 地图显示控件
    private Workspace m_woWorkspace; // 工作空间

    private PopupWindow m_popup;
    private View mainLayout = null;

    private String m_strServer = "";

    private final String sdcard = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected int getLayoutId() {
        Environment.setLicensePath(sdcard + Constant.SD_SM_LICENSE_DIR);
        Environment.initialization(this);
        return R.layout.activity_service;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        openMap();
        initView();
    }

    // 打开地图
    private boolean openMap() {
        m_woWorkspace = new Workspace();
        // 将地图显示空间和 工作空间关联
        m_mapControl = m_mapView.getMapControl();
        m_mapControl.getMap().setWorkspace(m_woWorkspace);
        DatasourceConnectionInfo dsInfo = new DatasourceConnectionInfo();
        dsInfo.setServer("http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China");
        dsInfo.setEngineType(EngineType.Rest);
        dsInfo.setAlias("ChinaRest");
        Datasource ds = m_woWorkspace.getDatasources().open(dsInfo);
        if (ds != null) {
            m_mapControl.getMap().getLayers().add(ds.getDatasets().get(0), true);
            m_mapControl.getMap().refresh();
            return true;
        }
        Log.e(this.getClass().getName(), "打开数据源失败了");
        return true;
    }

    // 初始化控件，绑定监听器
    private void initView() {

        m_Zoom.setOnZoomOutClickListener(new OnClickListener() {
            public void onClick(View v) {
                m_mapControl.getMap().zoom(0.5);
                m_mapControl.getMap().refresh();
            }
        });
        m_Zoom.setOnZoomInClickListener(new OnClickListener() {
            public void onClick(View v) {
                m_mapControl.getMap().zoom(2);
                m_mapControl.getMap().refresh();
            }
        });
        LayoutInflater lfCallOut = getLayoutInflater();
        mainLayout = lfCallOut.inflate(R.layout.setting, null);
        m_popup = new PopupWindow(mainLayout, 900, 900);
        m_popup.setFocusable(true);
        //确定按钮
        Button btnOk = (Button) mainLayout.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtServer = (EditText) mainLayout.findViewById(R.id.edt_server);
                EditText edtPort = (EditText) mainLayout.findViewById(R.id.edt_port);
                // 保存数据
                SharePreferenceUtil.putString("server", edtServer.getText().toString());
                SharePreferenceUtil.putString("port", edtPort.getText().toString());
                m_popup.dismiss();
            }
        });
        //关闭按钮
        Button btnClose = (Button) mainLayout.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                m_popup.dismiss();
            }
        });
    }

    // 查询
    private void Query() {
        m_mapView.removeAllCallOut();
        final ProgressDialog progress = new ProgressDialog(this);
        QueryService service = new QueryService(m_strServer);
        ServiceQueryParameter parameter = new ServiceQueryParameter();
        parameter.setQueryMapName(m_edtMapName.getText().toString());
        parameter.setQueryServiceName(m_edtServerName.getText().toString());
        parameter.setQueryLayerName(m_edtLayerName.getText().toString());
        //设置查询参数
        parameter.setExpectRecordCount(100);
        parameter.setQueryRecordStart(0);
        parameter.setQueryOption(QueryOption.GEOMETRY);
        parameter.setAttributeFilter(m_edtSql.getText().toString());
        service.setResponseCallback(new ResponseCallback() {
            @Override
            public void requestSuccess() {
                //销毁进度条显示框
                progress.dismiss();

                Toast.makeText(ServiceActivity.this, "查询成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestFailed(String arg0) {
                //销毁进度条显示框
                progress.dismiss();

                Toast.makeText(ServiceActivity.this, "查询失败", Toast.LENGTH_LONG).show();
                System.out.println("错误信息 " + arg0);
            }

            @Override
            public void receiveResponse(FeatureSet arg0) {
                if (arg0 instanceof FeatureSet) {
                    FeatureSet featureSet = (FeatureSet) arg0;

                    int nCount = 0;
                    featureSet.moveFirst();
                    while (!featureSet.isEOF()) {
                        Geometry geo = featureSet.getGeometry();
                        if (geo == null) {
                            featureSet.moveNext();
                            continue;
                        }
                        nCount++;
                        Point2D pt = featureSet.getGeometry().getInnerPoint();
                        LayoutInflater lfCallOut = getLayoutInflater();
                        View calloutLayout = lfCallOut.inflate(R.layout.callout2, null);

                        CallOut callout = new CallOut(ServiceActivity.this);
                        callout.setContentView(calloutLayout);                // 设置显示内容
                        callout.setCustomize(true);                            // 设置自定义背景图片
                        callout.setLocation(pt.getX(), pt.getY());            // 设置显示位置
                        m_mapView.addCallout(callout);
                        featureSet.moveNext();
                    }
                    System.out.println("count is " + nCount);
                    System.out.println("featureSet count is " + featureSet.getFeatureCount());
                }
            }
//

            @Override
            public void dataServiceFinished(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void addFeatureSuccess(int arg0) {

            }
        });
        //显示服务查询进度条，回调里面销毁
        progress.setMessage("服务查询中...");
        progress.show();
        // 查询
        service.query(parameter, QueryMode.SqlQuery);
    }

    @OnClick({R.id.btn_setting, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_setting:
                EditText edtServer = (EditText) mainLayout.findViewById(R.id.edt_server);
                EditText edtPort = (EditText) mainLayout.findViewById(R.id.edt_port);
                edtServer.setText(SharePreferenceUtil.getString("server", "http://support.supermap.com.cn"));
                edtPort.setText(SharePreferenceUtil.getString("port", "8090"));
                m_popup.showAtLocation(findViewById(R.id.mapview), Gravity.CENTER, 0, 0);
                break;
            case R.id.btn_query:
                String strServer = SharePreferenceUtil.getString("server", "http://support.supermap.com.cn");
                String strPort = SharePreferenceUtil.getString("port", "8090");
                m_strServer = strServer + ":" + strPort;
                Query(); // 查询
                break;
        }
    }
}