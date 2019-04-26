package com.supermap.demo.test.ui.activity.test;


import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.supermap.utils.SMIMobileInitializer;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.MLog;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Environment;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.Action;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.Layer;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;

public class GeometryInfoActivity extends BaseActivity implements View.OnTouchListener {

    private MapView m_mapView;
    private MapControl m_mapControl; // 地图显示控件
    private Workspace m_woWorkspace; // 工作空间

    private ZoomControls m_Zoom;
    private ImageButton m_btnSelect;

    private TextView m_txtCountry;
    private TextView m_txtCapital;
    private TextView m_txtPop;
    private TextView m_txtContinent;

    private View m_DetailLayout;
    private PopupWindow pwDetailInfo;

    private String mStrCountry;
    private String mStrCapital;
    private String mStrPop;
    private String mStrContinent;

    private Button m_btnQuery;
    private Spinner m_spnSelectContinent;
    private ArrayAdapter<String> adtSelectContinent;
    private static final String[] strContinentName = {"亚洲","欧洲","非洲","南美洲","北美洲","南极洲","大洋洲"};
    private final String sdcard = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();


    @Override
    protected int getLayoutId() {
        SMIMobileInitializer.initializeEnv(sdcard + Constant.SD_SM_LICENSE_DIR,
                sdcard + Constant.SD_SM_TEMP_DIR,
                sdcard + Constant.SD_SM_WEBCACHE_DIR,
                sdcard + Constant.SD_SM_FONTS_DIR,
                this);
        return R.layout.activity_geometry_info;
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
    private boolean openMap(){

        // 获取当前设备的显示屏幕的相关参数
        final Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        if(openWorkspace()){
            // 将地图显示空间和 工作空间关联
            m_mapView = (MapView)findViewById(R.id.mapview);
            m_mapControl = m_mapView.getMapControl();
            // 手势监听器
            m_mapControl.setGestureDetector(new GestureDetector(this,new MapGestureListener()));

            m_mapControl.getMap().setWorkspace(m_woWorkspace);
            m_mapControl.getMap().setMapDPI(dm.densityDpi);
            m_mapControl.setOnTouchListener(this);

            // 打开工作空间中地图的第2幅地图
            String mapName = m_woWorkspace.getMaps().get(0);
            boolean isOpenMap = m_mapControl.getMap().open(mapName);
            if(isOpenMap){
                // 刷新地图，涉及地图的任何操作都需要调用该接口进行刷新
                m_mapControl.getMap().refresh();
            }
            return true;
        }
        return false;
    }

    private boolean openWorkspace(){
        m_woWorkspace = new Workspace();

        WorkspaceConnectionInfo m_info = new WorkspaceConnectionInfo();
        m_info.setServer(sdcard + Constant.SD_SM_DB_NINGBOGANG);
        m_info.setType(WorkspaceType.SMWU);

        return m_woWorkspace.open(m_info);
    }

    // 初始化控件，绑定监听器
    private void initView(){

        m_Zoom = (ZoomControls)findViewById(R.id.zoomControls1);
        m_Zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                m_mapControl.getMap().zoom(0.5);
                m_mapControl.getMap().refresh();
            }
        });
        m_Zoom.setOnZoomInClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                m_mapControl.getMap().zoom(2);
                m_mapControl.getMap().refresh();
            }
        });

        m_btnSelect = (ImageButton)findViewById(R.id.btn_selectGeo);
        m_btnSelect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                m_mapControl.setAction(Action.SELECT);
            }
        });

        m_spnSelectContinent = (Spinner)findViewById(R.id.spn_select_continent);
        adtSelectContinent = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strContinentName);
        adtSelectContinent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_spnSelectContinent.setAdapter(adtSelectContinent);

        m_btnQuery = (Button)findViewById(R.id.btn_search);
        m_btnQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Query(); // 查询
            }
        });

        LayoutInflater lfCallOut = getLayoutInflater();
        m_DetailLayout = lfCallOut.inflate(R.layout.detailinfo, null);

        pwDetailInfo = new PopupWindow(m_DetailLayout,380, WindowManager.LayoutParams.WRAP_CONTENT);

        m_txtCountry = (TextView)m_DetailLayout.findViewById(R.id.txt_country);
        m_txtCapital = (TextView)m_DetailLayout.findViewById(R.id.txt_capital);
        m_txtPop = (TextView)m_DetailLayout.findViewById(R.id.txt_pop);
        m_txtContinent = (TextView)m_DetailLayout.findViewById(R.id.txt_Continent);
    }

    public boolean onTouch(View v, MotionEvent event) {
        m_mapControl.onMultiTouch(event);
        return true;
    }

    // 属性查询
    private void Query(){
        String strContinent = m_spnSelectContinent.getSelectedItem().toString();
        String strFilter = "CONTINENT = '" + strContinent + "'";

        // 获得第10个图层
        Layer layer = m_mapControl.getMap().getLayers().get(9);
        DatasetVector datasetvector = (DatasetVector)layer.getDataset();

        // 设置查询参数
        QueryParameter parameter = new QueryParameter();
        parameter.setAttributeFilter(strFilter);
        parameter.setCursorType(CursorType.STATIC);

        // 查询，返回查询结果记录集
        Recordset recordset = datasetvector.query(parameter);

        if (recordset.getRecordCount()<1) {
            Toast.makeText(GeometryInfoActivity.this, "未搜索到对象", Toast.LENGTH_SHORT).show();
            m_mapControl.getMap().refresh();
            return;
        }

        Point2D ptInner;
        recordset.moveFirst();
        Geometry geometry = recordset.getGeometry();

        m_mapView.removeAllCallOut(); // 移除所有Callout

        while (!recordset.isEOF()) {
            geometry = recordset.getGeometry();
            ptInner = geometry.getInnerPoint();

            LayoutInflater lfCallOut = getLayoutInflater();
            View calloutLayout = lfCallOut.inflate(R.layout.callout2, null);

            Button btnSelected = (Button)calloutLayout.findViewById(R.id.btnSelected);
            btnSelected.setText(geometry.getID() + "");
            btnSelected.setTag(geometry.getID());
            btnSelected.setOnClickListener(new detailClickListener());

            CallOut callout = new CallOut(this);
            callout.setContentView(calloutLayout);				// 设置显示内容
            callout.setCustomize(true);							// 设置自定义背景图片
            callout.setLocation(ptInner.getX(), ptInner.getY());// 设置显示位置
            MLog.e("====zhq====>xxxx<"+ptInner.getX()+"====>YYYY<"+ptInner.getY());
            m_mapView.addCallout(callout);

            recordset.moveNext();
        }

        m_mapView.showCallOut();								// 显示标注
        m_mapControl.getMap().setCenter(geometry.getInnerPoint());
        m_mapControl.getMap().refresh();

        // 释放资源
        recordset.dispose();
        geometry.dispose();
    }

    // ID查询
    private void QuerybyID(String id){
        String strFilter = "SMID = '" + id + "'";

        // 获得第10个图层
        Layer layer = m_mapControl.getMap().getLayers().get(9);
        DatasetVector datasetvector = (DatasetVector)layer.getDataset();

        // 设置查询参数
        QueryParameter parameter = new QueryParameter();
        parameter.setAttributeFilter(strFilter);
        parameter.setCursorType(CursorType.STATIC);

        // 查询，返回查询结果记录集
        Recordset recordset = datasetvector.query(parameter);

        if (recordset.getRecordCount()<1) {
            return;
        }

        recordset.moveFirst();

        mStrCountry = recordset.getFieldValue("COUNTRY").toString();
        mStrCapital = recordset.getFieldValue("CAPITAL").toString();
        mStrContinent = recordset.getFieldValue("CONTINENT").toString();
        mStrPop = recordset.getFieldValue("POP_1994").toString();

        // 释放资源
        recordset.dispose();
    }

    private ImageButton btn_Close;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class detailClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (btn_Close != null)
                btn_Close.performClick();

            String strID = v.getTag().toString();
            System.out.println(strID);
            QuerybyID(strID);


            m_txtCountry.setText(mStrCountry);
            m_txtCapital.setText(mStrCapital);
            m_txtPop.setText(mStrPop);
            m_txtContinent.setText(mStrContinent);

//			pwDetailInfo.showAtLocation(m_mapControl, Gravity.NO_GRAVITY, 8, 86);
            pwDetailInfo.showAsDropDown(v, 60, -60);
            btn_Close = (ImageButton)m_DetailLayout.findViewById(R.id.btn_close);

            btn_Close.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    pwDetailInfo.dismiss();
                }
            });
        }
    }

    // 手势监听器
    class MapGestureListener extends GestureDetector.SimpleOnGestureListener {

        public MapGestureListener() {
            super();
            MLog.e("====zhq====>0000000<");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            MLog.e("====zhq====>111111111<");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            MLog.e("====zhq====>222222222<");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            MLog.e("====zhq====>333333333<");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            MLog.e("====zhq====>444444444<");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            MLog.e("====zhq====>555555555<");
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            MLog.e("====zhq====>666666666<");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            MLog.e("====zhq====>777777777<");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            MLog.e("====zhq====>888888888<");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            MLog.e("====zhq====>9999999<");
            return super.onContextClick(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            MLog.e("====zhq====>1010101010<");
            // TODO Auto-generated method stub
            Recordset rt = null;
            // 获得第10个图层
            Layer ly = m_mapControl.getMap().getLayers().get(9);
            rt  = ly.getSelection().toRecordset();

            if (rt != null) {
                if (rt.getRecordCount()<1) {
                    return;
                }
                mStrCountry = rt.getFieldValue("COUNTRY").toString();
                mStrCapital = rt.getFieldValue("CAPITAL").toString();
                mStrContinent = rt.getFieldValue("CONTINENT").toString();
                mStrPop = rt.getFieldValue("POP_1994").toString();

                Geometry geometry = rt.getGeometry();
                Point2D ptInner = geometry.getInnerPoint();

                LayoutInflater lfCallOut = getLayoutInflater();
                View calloutLayout = lfCallOut.inflate(R.layout.callout, null);

                TextView txtBubbleTitle = (TextView)calloutLayout.findViewById(R.id.edtBubbleTitle);
                TextView txtBubbleText = (TextView)calloutLayout.findViewById(R.id.edtBubbleText);
                txtBubbleTitle.setText(mStrCountry);
                txtBubbleText.setText(mStrCapital);

                CallOut callout = new CallOut(GeometryInfoActivity.this);
                callout.setContentView(calloutLayout);				// 设置显示内容
                callout.setStyle(CalloutAlignment.BOTTOM);			// 设置对齐方式
                callout.setLocation(ptInner.getX(), ptInner.getY());// 设置显示位置

                //callout.setBackground(android.graphics.Color.argb(255, 120, 230, 255),
                //			android.graphics.Color.argb(255, 200, 246, 255));// 自定义颜色
                m_mapView.removeAllCallOut();
                m_mapView.addCallout(callout);
                m_mapView.showCallOut();							// 显示标注
            }
            super.onLongPress(e);
        }
    }
}
