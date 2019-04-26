package com.supermap.demo.test.ui.activity.test;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ZoomControls;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.StatusBar.StatusBarUtil;
import com.supermap.data.Color;
import com.supermap.data.ColorGradientType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Environment;
import com.supermap.data.GeoStyle;
import com.supermap.data.TextStyle;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.Layer;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.supermap.mapping.ThemeLabel;
import com.supermap.mapping.ThemeLabelItem;
import com.supermap.mapping.ThemeRange;
import com.supermap.mapping.ThemeRangeItem;
import com.supermap.mapping.ThemeUnique;
import com.supermap.mapping.ThemeUniqueItem;

public class ThemeActivity extends BaseActivity {

    private MapView m_MapView;
    private MapControl m_mapControl; // 地图显示控件
    private Workspace m_woWorkspace; // 工作空间
    private ZoomControls m_Zoom;
    private Button m_btnCreat;
    private Spinner m_spnStyle;
    private ArrayAdapter<String> adtStyle;
    private static final String[] strStyleName = {"统一风格标签专题图","分段风格标签专题图","分段专题图","单值专题图"};
    private final String sdcard = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();


    @Override
    protected int getLayoutId() {
        Environment.initialization(this);
        return R.layout.activity_theme;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        initTitle();
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
            m_MapView = (MapView)findViewById(R.id.map_view);
            m_mapControl=m_MapView.getMapControl();
            m_mapControl.getMap().setWorkspace(m_woWorkspace);
            m_mapControl.getMap().setMapDPI(dm.densityDpi);

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
        m_info.setServer(sdcard + Constant.SD_SM_DB_WS);
        m_info.setType(WorkspaceType.SMWU);

        return m_woWorkspace.open(m_info);
    }

    // 初始化控件，绑定监听器
    private void initView(){

        m_Zoom = (ZoomControls)findViewById(R.id.zoomControls1);
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

        m_spnStyle = (Spinner)findViewById(R.id.spn_style);
        adtStyle = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strStyleName);
        adtStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_spnStyle.setAdapter(adtStyle);

        m_btnCreat = (Button)findViewById(R.id.btn_creat);
        m_btnCreat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                creat(); // 生成
            }
        });
    }

    // 生成专题图
    public void creat() {
        Dataset dataset = m_woWorkspace.getDatasources().get(0).getDatasets().get("Countries");
        if (dataset != null) {
            m_mapControl.getMap().getLayers().add(dataset,null, true);
        }

        String strStyle = m_spnStyle.getSelectedItem().toString();

        if (strStyle.equals("统一风格标签专题图")) {
            creatUnifiedMap();
        } else if(strStyle.equals("分段风格标签专题图")){
            creatSubsectoinMap();
        } else if (strStyle.equals("分段专题图")) {
            creatThemeRangeMap();
        } else {
            creatThemeUniqueMap();
        }
    }

    private Layer layer;

    // 制作统一风格标签专题图
    public void creatUnifiedMap() {
        ThemeLabel themeLabelMap = new ThemeLabel();
        themeLabelMap.setLabelExpression("Country");
        themeLabelMap.setRangeExpression("Pop_1994");

        // 为标签专题图的标签设置统一样式
        ThemeLabelItem themeLabelItem1 = new ThemeLabelItem();
        themeLabelItem1.setVisible(true);
        TextStyle textStyle1 = new TextStyle();
        textStyle1.setForeColor(new Color(255, 10, 10));
        textStyle1.setFontName("宋体");
        themeLabelItem1.setStyle(textStyle1);

        // 添加标签专题图子项到标签专题图对象中
        removeTheme();
        themeLabelMap.addToHead(themeLabelItem1);


        Dataset dataset = m_woWorkspace.getDatasources().get(0).getDatasets().get("Countries");
        if (dataset != null) {
            layer = m_mapControl.getMap().getLayers().add(dataset,themeLabelMap, true);
        }

        m_mapControl.getMap().refresh();
    }

    // 制作分段风格标签专题图
    public void creatSubsectoinMap() {
        ThemeLabel themeLabelMap = new ThemeLabel();
        themeLabelMap.setLabelExpression("Country");
        themeLabelMap.setRangeExpression("Pop_1994");

        // 为标签专题图的标签设置分段样式
        // 设置人口大于一亿国家的标签
        ThemeLabelItem themeLabelItem1 = new ThemeLabelItem();
        themeLabelItem1.setCaption("大于一亿");
        themeLabelItem1.setEnd(120000000);
        themeLabelItem1.setStart(100000000);
        themeLabelItem1.setVisible(true);
        TextStyle textStyle1 = new TextStyle();
        textStyle1.setForeColor(new Color(255, 100, 100));
        textStyle1.setFontName("宋体");
        themeLabelItem1.setStyle(textStyle1);

        // 设置人口大于一千万国家的标签
        ThemeLabelItem themeLabelItem2 = new ThemeLabelItem();
        themeLabelItem2.setCaption("大于一千万");
        themeLabelItem2.setEnd(100000000);
        themeLabelItem2.setStart(10000000);
        themeLabelItem2.setVisible(true);
        TextStyle textStyle2 = new TextStyle();
        textStyle2.setForeColor(new Color(255, 25, 189));
        textStyle2.setFontName("宋体");
        themeLabelItem2.setStyle(textStyle2);

        // 设置人口大于一百万国家的标签
        ThemeLabelItem themeLabelItem3 = new ThemeLabelItem();
        themeLabelItem3.setCaption("大于一百万");
        themeLabelItem3.setEnd(10000000);
        themeLabelItem3.setStart(1000000);
        themeLabelItem3.setVisible(true);
        TextStyle textStyle3 = new TextStyle();
        textStyle3.setForeColor(new Color(0, 255, 0));
        textStyle3.setFontName("宋体");
        themeLabelItem3.setStyle(textStyle3);

        // 设置人口小于一百万国家的标签
        ThemeLabelItem themeLabelItem4 = new ThemeLabelItem();
        themeLabelItem4.setCaption("小于一百万");
        themeLabelItem4.setEnd(1000000);
        themeLabelItem4.setStart(0);
        themeLabelItem4.setVisible(true);
        TextStyle textStyle4 = new TextStyle();
        textStyle4.setForeColor(new Color(0, 0, 255));
        textStyle4.setFontName("宋体");
        themeLabelItem4.setStyle(textStyle4);

        // 添加标签专题图子项到标签专题图对象中
        removeTheme();
        themeLabelMap.addToHead(themeLabelItem1);
        themeLabelMap.addToHead(themeLabelItem2);
        themeLabelMap.addToHead(themeLabelItem3);
        themeLabelMap.addToHead(themeLabelItem4);

        Dataset dataset = m_woWorkspace.getDatasources().get(0).getDatasets().get("Countries");
        if (dataset != null) {
            layer = m_mapControl.getMap().getLayers().add(dataset,themeLabelMap, true);
        }

        m_mapControl.getMap().refresh();
    }

    // 制作分段专题图
    public void creatThemeRangeMap() {
        ThemeRange themeRangeMap = new ThemeRange();
        themeRangeMap.setRangeExpression("Pop_1994");

        // 填充样式设置
        GeoStyle geoStyle = new GeoStyle();
        geoStyle.setLineColor(new Color(255,255,255));
        geoStyle.setLineWidth(0.3);

        // 人口小于一百万的分段专题图子项的设置
        ThemeRangeItem themeRangeItem1 = new ThemeRangeItem();
        themeRangeItem1.setCaption("小于一百万");
        themeRangeItem1.setEnd(1000000);
        themeRangeItem1.setStart(0);
        themeRangeItem1.setVisible(true);
        geoStyle.setFillForeColor(new Color(209, 182, 210));
        themeRangeItem1.setStyle(geoStyle);

        // 人口大于一百万的分段专题图子项的设置
        ThemeRangeItem themeRangeItem2 = new ThemeRangeItem();
        themeRangeItem2.setCaption("大于一百万");
        themeRangeItem2.setEnd(10000000);
        themeRangeItem2.setStart(1000000);
        themeRangeItem2.setVisible(true);
        geoStyle.setFillForeColor(new Color(205, 167, 183));
        themeRangeItem2.setStyle(geoStyle);

        // 人口大于一千万的分段专题图子项的设置
        ThemeRangeItem themeRangeItem3 = new ThemeRangeItem();
        themeRangeItem3.setCaption("大于一千万");
        themeRangeItem3.setEnd(100000000);
        themeRangeItem3.setStart(10000000);
        themeRangeItem3.setVisible(true);
        geoStyle.setFillForeColor(new Color(183, 128, 151));
        themeRangeItem3.setStyle(geoStyle);

        // 人口大于一亿的分段专题图子项的设置
        ThemeRangeItem themeRangeItem4 = new ThemeRangeItem();
        themeRangeItem4.setCaption("大于一亿");
        themeRangeItem4.setEnd(1000000000);
        themeRangeItem4.setStart(100000000);
        themeRangeItem4.setVisible(true);
        geoStyle.setFillForeColor(new Color(164, 97, 136));
        themeRangeItem4.setStyle(geoStyle);

        // 人口大于十二亿的分段专题图子项的设置
        ThemeRangeItem themeRangeItem5 = new ThemeRangeItem();
        themeRangeItem5.setCaption("大于十亿");
        themeRangeItem5.setEnd(Double.MAX_VALUE);
        themeRangeItem5.setStart(1000000000);
        themeRangeItem5.setVisible(true);
        geoStyle.setFillForeColor(new Color(94, 53, 77));
        themeRangeItem5.setStyle(geoStyle);

        // 添加专题图子项到分段专题图对象中
        removeTheme();
        themeRangeMap.addToHead(themeRangeItem1);
        themeRangeMap.addToTail(themeRangeItem2);
        themeRangeMap.addToTail(themeRangeItem3);
        themeRangeMap.addToTail(themeRangeItem4);
        themeRangeMap.addToTail(themeRangeItem5);

        // 显示
        Dataset dataset = m_woWorkspace.getDatasources().get(0).getDatasets().get("Countries");
        if(dataset != null){
            layer = m_mapControl.getMap().getLayers().add(dataset,themeRangeMap, true);
        }
        m_mapControl.getMap().refresh();
    }

    // 构造单值专题图
    public void creatThemeUniqueMap() {
        ThemeRange themeRangeMap = new ThemeRange();
        themeRangeMap.setRangeExpression("Pop_1994");
        ThemeUnique theme = new ThemeUnique();

        // 将得到的专题图添加到地图
        Dataset dataset = m_woWorkspace.getDatasources().get(0).getDatasets().get("Countries");
        removeTheme();
        theme = ThemeUnique.makeDefault((DatasetVector)dataset, "SmID=1 OR SmID=247", ColorGradientType.YELLOWRED);

        // 设置各个子项显示风格
        int nCount = theme.getCount();
        for (int i = 0; i < nCount; i++) {
            if(i==0){
                ThemeUniqueItem Item = theme.getItem(i);
                Item.getStyle().setLineColor(new Color(255, 0, 0));
            }
            if(i==1){
                ThemeUniqueItem Item = theme.getItem(i);
                Item.getStyle().setLineColor(new Color(0, 0, 255));
            }
        }


        layer = m_mapControl.getMap().getLayers().add(dataset,theme, true);
        m_mapControl.getMap().refresh();
    }

    private void removeTheme() {
        if (m_mapControl.getMap().getLayers().getCount() > 1 && layer != null) {
            m_mapControl.getMap().getLayers().removeLayer(layer,true);
        }
    }
}
