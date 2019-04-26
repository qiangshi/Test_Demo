package com.supermap.demo.test.map;

import com.supermap.mapping.Layer;
import com.supermap.mapping.Layers;
import com.supermap.mapping.Map;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 主界面地图图层控制
 * @Date: 2019/4/19
 */
public class MainMapLayerControl {

    private static String TOTAL_LABEL_LAYER = "g_total_label";
    private static String APPROVAL_POINT_LABEL_LAYER = "g_approval_point_label";
    private static String PLAN_POINT_LABEL_LAYER = "g_plan_point_label";
    private static String APPROVAL_POINT_LAYER = "g_approval_point";
    private static String PLAN_POINT_LAYER = "g_plan_point";
    private static String TOTAL_LAYER = "g_total";

    private static MainMapLayerControl mainMapLayerControl;

    private Map map;

    /**
     * 构造函数
     * @param mainMap 主界面地图对象
     */
    private MainMapLayerControl(Map mainMap) {

        this.map = mainMap;

    }

    public static void init(Map mainMap) {

        if (mainMapLayerControl != null) {
            return;
        }

        mainMapLayerControl = new MainMapLayerControl(mainMap);
    }

    public static MainMapLayerControl getInstance() {
        return mainMapLayerControl;
    }

    /**
     * 设置所有专题数据图层的可见性
     * @param visible true表示打开图层；false表示关闭图层
     * @param refreshMap 改变可见性后，是否执行地图刷新操作
     */
    private void setBusinessLayersVisible(boolean visible, boolean refreshMap) {

        Layers layers = map.getLayers();

        Layer approvalPointLabelLayer = layers.getByCaption(APPROVAL_POINT_LABEL_LAYER);
        Layer approvalPointLayer = layers.getByCaption(APPROVAL_POINT_LAYER);

        Layer planPointLabelLayer = layers.getByCaption(PLAN_POINT_LABEL_LAYER);
        Layer planPointLayer = layers.getByCaption(PLAN_POINT_LAYER);

        Layer totalLabelLayer = layers.getByCaption(TOTAL_LABEL_LAYER);
        Layer totalLayer = layers.getByCaption(TOTAL_LAYER);

        approvalPointLabelLayer.setVisible(visible);
        planPointLabelLayer.setVisible(visible);

        approvalPointLayer.setVisible(visible);
        planPointLayer.setVisible(visible);

        totalLabelLayer.setVisible(visible);
        totalLayer.setVisible(visible);

        if(refreshMap) {
            map.refresh();
        }

    }

    /**
     * 打开默认业务数据图层
     * @param refreshMap 打开图层后，是否执行地图刷新操作。注意：不必要的地图刷新会影响性能。
     */
    public void openDefaultBusinessLayers(boolean refreshMap) {

        setBusinessLayersVisible(false, false);

        Layers layers = map.getLayers();
        Layer totalLabelLayer = layers.getByCaption(TOTAL_LABEL_LAYER);
        Layer totalLayer = layers.getByCaption(TOTAL_LAYER);

        totalLabelLayer.setVisible(true);
        totalLayer.setVisible(true);

        if(refreshMap) {
            map.refresh();
        }

    }

    /**
     * 打开审批专题数据图层
     * @param refreshMap 打开图层后，是否执行地图刷新操作。注意：不必要的地图刷新会影响性能。
     */
    public void openApprovalLayers(boolean refreshMap) {

        setBusinessLayersVisible(false, false);

        Layers layers = map.getLayers();
        Layer approvalPointLabelLayer = layers.getByCaption(APPROVAL_POINT_LABEL_LAYER);
        Layer approvalPointLayer = layers.getByCaption(APPROVAL_POINT_LAYER);
        Layer totalLayer = layers.getByCaption(TOTAL_LAYER);

        approvalPointLabelLayer.setVisible(true);
        approvalPointLayer.setVisible(true);
        totalLayer.setVisible(true);

        if(refreshMap) {
            map.refresh();
        }
    }

    /**
     * 打开控规专题数据图层
     * @param refreshMap 打开图层后，是否执行地图刷新操作。注意：不必要的地图刷新会影响性能。
     */
    public void openPlanLayers(boolean refreshMap) {

        setBusinessLayersVisible(false, false);

        Layers layers = map.getLayers();
        Layer planPointLabelLayer = layers.getByCaption(PLAN_POINT_LABEL_LAYER);
        Layer planPointLayer = layers.getByCaption(PLAN_POINT_LAYER);
        Layer totalLayer = layers.getByCaption(TOTAL_LAYER);

        planPointLabelLayer.setVisible(true);
        planPointLayer.setVisible(true);
        totalLayer.setVisible(true);

        if(refreshMap) {
            map.refresh();
        }

    }

}
