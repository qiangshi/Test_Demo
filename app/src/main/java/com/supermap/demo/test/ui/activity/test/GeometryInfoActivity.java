package com.supermap.demo.test.ui.activity.test;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.PrjCoordSysType;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.supermap.utils.SMIMobileInitializer;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.ui.activity.MainActivity;
import com.supermap.demo.test.utils.MLog;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.supermap.navi.NaviInfo;
import com.supermap.navi.NaviListener;
import com.supermap.navi.Navigation;
import com.supermap.plugin.SpeakPlugin;
import com.supermap.plugin.Speaker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GeometryInfoActivity extends BaseActivity implements View.OnTouchListener {

    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.zoomControls1)
    ZoomControls m_Zoom;
    @BindView(R.id.pan)
    Button btnPan;
    @BindView(R.id.guide)
    Button btnGuide;
    @BindView(R.id.analyst)
    Button btnRoute;
    @BindView(R.id.setting)
    Button btnSetting;
    @BindView(R.id.layout_btns)
    RelativeLayout layout;


    private MapControl mMapControl; // 地图显示控件
    private Workspace m_woWorkspace; // 工作空间
    private Navigation mNavi = null;


    //操作过程中的状态改变
    private boolean bGuideEnable = false;
    private boolean bEndPointEnable = false;
    private boolean bAnalystEnable = false;
    private boolean bLongPressEnable = false;
    //当进行路径分析后则不能修改起点终点
    private boolean bSettingEnable = true;

    //是否重置起始点
    private boolean bResetPoint = false;
    private final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath().toString();


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
        initView();
        openMap();
    }

    // 打开地图
    private boolean openMap() {

        // 获取当前设备的显示屏幕的相关参数
        final Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        if (openWorkspace()) {
            // 将地图显示空间和 工作空间关联
            mMapControl.getMap().setWorkspace(m_woWorkspace);
            mMapControl.getMap().setMapDPI(dm.densityDpi);
            mMapControl.getMap().setFullScreenDrawModel(true);
            mMapControl.setOnTouchListener(this);

            //设置手势委托
            mMapControl.setGestureDetector(new GestureDetector(this, new MapGestureListener()));
            //设置导航数据
            mNavi.connectNaviData(sdcard + Constant.SD_SM_DB_GUIDE);

            SpeakPlugin.getInstance().setSpeaker(Speaker.CONGLE);
            mNavi.addNaviInfoListener(new NaviListener() {

                @Override
                public void onStopNavi() {
                    // TODO Auto-generated method stub
                    layout.setVisibility(View.VISIBLE);     // 导航停止后，显示按钮界面
                    btnGuide.setText("开始引导");
                }

                @Override
                public void onStartNavi() {
                    // TODO Auto-generated method stub
                    layout.setVisibility(View.GONE);        // 导航开始前，先隐藏按钮界面
                }

                @Override
                public void onNaviInfoUpdate(NaviInfo arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAarrivedDestination() {
                    // TODO Auto-generated method stub
                    layout.setVisibility(View.VISIBLE);     // 到达目的地后，显示按钮界面
                    btnGuide.setText("开始引导");
                }

                @Override
                public void onAdjustFailure() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onPlayNaviMessage(String arg0) {
                    // TODO Auto-generated method stub
                }
            });

            // 打开工作空间中地图的第2幅地图
            String mapName = m_woWorkspace.getMaps().get(0);
            boolean isOpenMap = mMapControl.getMap().open(mapName);
            if (isOpenMap) {
                // 刷新地图，涉及地图的任何操作都需要调用该接口进行刷新
                mMapControl.getMap().refresh();
            }
            return true;
        }
        return false;
    }

    private boolean openWorkspace() {
        m_woWorkspace = new Workspace();
        WorkspaceConnectionInfo m_info = new WorkspaceConnectionInfo();
        m_info.setServer(sdcard + Constant.SD_SM_DB_NINGBOGANG);
        m_info.setType(WorkspaceType.SMWU);

        return m_woWorkspace.open(m_info);
    }

    // 初始化控件，绑定监听器
    private void initView() {
        mMapControl = mMapView.getMapControl();
        mNavi = mMapControl.getNavigation();
        m_Zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mMapControl.getMap().zoom(0.5);
                mMapControl.getMap().refresh();
            }
        });
        m_Zoom.setOnZoomInClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mMapControl.getMap().zoom(2);
                mMapControl.getMap().refresh();
            }
        });

    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mNavi.enablePanOnGuide(true);
        }
        return mMapControl.onMultiTouch(event);
    }


    @OnClick({R.id.pan, R.id.guide, R.id.analyst, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pan:
                mNavi.enablePanOnGuide(false);
                break;
            case R.id.guide:
                if (!bGuideEnable) {
                    SpeakPlugin.getInstance().playSound("请先进行路径分析!");
                    Toast.makeText(this, "请先进行路径分析!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //如果是已经导航，再次点击则停止导航
                if (mNavi.isGuiding()) {
                    mNavi.cleanPath();
                    mNavi.stopGuide();
                    bSettingEnable = true;
                    bGuideEnable = false;
                    bEndPointEnable = false;
                    mMapView.removeAllCallOut();
                    btnGuide.setText("开始引导");
                    return;
                }
                layout.setVisibility(View.GONE);        // 导航开始前，先隐藏按钮界面
                //1代表模拟导航
                mNavi.startGuide(1);
                mNavi.enablePanOnGuide(false);
                btnGuide.setText("停止引导");
//                bGuideEnable = false;
                break;
            case R.id.analyst:
                if (!bAnalystEnable) {
                    Toast.makeText(this, "请先设置起点和终点!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //如果分析过了则再次点击是清除分析结果
                if (bGuideEnable) {
                    bGuideEnable = false;
                    mNavi.cleanPath();
                    bSettingEnable = true;
                    return;
                }
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setMessage("路径分析中...");
                dialog.show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //0代表最快的模式
                        mNavi.routeAnalyst(0);
                        bGuideEnable = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                btnRoute.setText("清除路径");
                                bSettingEnable = false;
                                mMapControl.getMap().refresh();
                            }
                        });
                    }
                }).start();
                break;
            case R.id.setting:
                if (!bSettingEnable) {
                    Toast.makeText(this, "不能修改起点和终点!", Toast.LENGTH_SHORT).show();
                    bLongPressEnable = true;
                    return;
                }
                if (bEndPointEnable) {
                    Toast.makeText(this, "长按设置终点!", Toast.LENGTH_SHORT).show();
                    bLongPressEnable = true;
                    return;
                }
                if (bResetPoint) {
                    mMapView.removeAllCallOut();//清空起始点
                    bAnalystEnable = false;
                    bResetPoint = false;
                }
                Toast.makeText(this, "长按设置起点!", Toast.LENGTH_SHORT).show();
                bLongPressEnable = true;
                break;
        }
    }

    // 手势监听器
    class MapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            MLog.e("====zhq====>1010101010<");
            if (!bLongPressEnable) {
                return;
            }
            int x = (int) e.getX();
            int y = (int) e.getY();
            Point2D pt = mMapControl.getMap().pixelToMap(new Point(x, y));
            CallOut callout = new CallOut(GeometryInfoActivity.this);
            callout.setStyle(CalloutAlignment.LEFT_BOTTOM);
            callout.setCustomize(true);
            callout.setLocation(pt.getX(), pt.getY());
            //当投影不是经纬坐标系时，则对起始点进行投影转换
//            if (mMapControl.getMap().getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE) {
//                Point2Ds points = new Point2Ds();
//                points.add(pt);
//                PrjCoordSys desPrjCoorSys = new PrjCoordSys();
//                desPrjCoorSys.setType(PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE);
//                CoordSysTranslator.convert(points, mMapControl.getMap().getPrjCoordSys(), desPrjCoorSys, new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
//                pt = points.getItem(0);
//            }
            MLog.e("====zhq====>00<"+x+"===="+y);
            MLog.e("====zhq====>11<"+pt.getX()+"===="+pt.getY());
            ImageView image = new ImageView(GeometryInfoActivity.this);
            //添加第一个点
            if (!bEndPointEnable) {
                image.setBackgroundResource(R.drawable.startpoint);
                callout.setContentView(image);
                mMapView.addCallout(callout);
                mNavi.setStartPoint(pt.getX(), pt.getY());
                bEndPointEnable = true;
                bLongPressEnable = false;
                btnSetting.setText("设置终点");
                btnSetting.invalidate();
                return;
            }
            image.setBackgroundResource(R.drawable.despoint);
            callout.setContentView(image);
            mMapView.addCallout(callout);
            mNavi.setDestinationPoint(pt.getX(), pt.getY());
            bAnalystEnable = true;
            btnSetting.setText("重置起点");
            bResetPoint = true;
            bEndPointEnable = false;
            bLongPressEnable = false;
        }
    }
}
