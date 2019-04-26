package com.supermap.demo.test.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.supermap.demo.test.R;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.map.dataservice.ApprovalService;
import com.supermap.demo.test.map.dataservice.FullSearchService;
import com.supermap.demo.test.map.dataservice.PlanService;
import com.supermap.data.Color;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.Point2D;
import com.supermap.mapping.Map;
import com.supermap.mapping.TrackingLayer;
import com.supermap.mapping.dyn.DynamicAlignment;
import com.supermap.mapping.dyn.DynamicPoint;
import com.supermap.mapping.dyn.DynamicStyle;
import com.supermap.mapping.dyn.DynamicView;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 主地图工具
 * @Date: 2019/4/20
 */
public class MainMapTool {

    private final String LOCATE_TRACKING_POLYGON_NAME  = "locateTrackingPolygon";

    private static MainMapTool mainMapTool;

    private Map map;

    private Context mapContext;

    private DynamicView locateDynamicView;

    /**
     * 构造函数
     * @param mainMapContext 主界面地图所在的android.content.Context
     * @param mainMap 主界面地图对象
     */
    private MainMapTool(Context mainMapContext, Map mainMap) {

        this.mapContext = mainMapContext;
        this.map = mainMap;

        locateDynamicView = new DynamicView(mainMapContext, mainMap);
        mainMap.getMapView().addDynamicView(locateDynamicView);

    }

    public static void init(Context mainMapContext, Map mainMap) {

        if (mainMapTool != null) {
            return;
        }

        mainMapTool = new MainMapTool(mainMapContext, mainMap);
    }

    public static MainMapTool getInstance() {
        return mainMapTool;
    }

    public void refreshDynamicView() {

        locateDynamicView.refresh();

    }

    /**
     * 地图点选
     * @param pointX 点选点的x坐标
     * @param pointY 点选点的y坐标
     * @param currentMapScale 当前地图比例尺
     * @return 若没有查到，返回null
     */
    public FullSearchItem selectByPoint(double pointX, double pointY, double currentMapScale) {

        FullSearchService fullSearchService = new FullSearchService();
        FullSearchItem fullSearchItem = fullSearchService.selectByPoint(pointX, pointY, currentMapScale);

        if (fullSearchItem != null) {
            markPoint(fullSearchItem.getCoordX(), fullSearchItem.getCoordY());
        }

        return fullSearchItem;
    }

    /**
     * 标示点
     * @param pointX 点x坐标
     * @param pointY 点y坐标
     */
    public void markPoint(double pointX, double pointY) {

        clearLocateMarker();

        DynamicPoint dynPoint = createLocateDynPoint(pointX, pointY);
        locateDynamicView.addElement(dynPoint);

        map.refresh();
    }

    /**
     * poi定位
     * @param poiX poi X坐标
     * @param poiY poi y坐标
     */
    public void locatePoi(double poiX, double poiY) {

        clearLocateMarker();

        map.setCenter(new Point2D(poiX, poiY));
        map.setScale(0.0005);

        DynamicPoint dynPoint = createLocateDynPoint(poiX, poiY);
        locateDynamicView.addElement(dynPoint);

        map.refresh();
    }

    /**
     * 业务数据定位
     * @param bid 业务数据bid
     * @param businessX 业务数据x坐标
     * @param businessY 业务数据y坐标
     * @param category 业务数据分类
     */
    public void locateBusiness(int bid, double businessX, double businessY, String category) {

        GeoRegion businessGeoRegion = null;

        if (category.equals(DataCategoryConstant.PLAN_CODE)) {

            PlanService planService = new PlanService();
            businessGeoRegion = planService.getPlanGeoRegion(bid);

        } else if (category.equals(DataCategoryConstant.APPROVAL_CONTRACT_CODE)
                    || category.equals(DataCategoryConstant.APPROVAL_ALLOT_CODE)) {

            ApprovalService approvalService = new ApprovalService();
            businessGeoRegion = approvalService.getApprovalGeoRegion(bid);

        }

        if (businessGeoRegion == null) {
            locatePoi(businessX, businessY);
            return;
        }

        clearLocateMarker();

        map.setViewBounds(businessGeoRegion.getBounds());

        String currentScale = String.valueOf(map.getScale());

        if (map.getVisibleScales() != null) {

            boolean hasScale = false;

            for (int i = 0; i < map.getVisibleScales().length; i++) {
                if (String.valueOf(map.getVisibleScales()[i]).equals(currentScale)) {
                    if (i != 0) {
                        map.setScale(map.getVisibleScales()[i - 1]);
                    }

                    hasScale = true;
                    break;
                }
            }

            if (hasScale == false) {
                map.zoom(0.5);
            }


        } else {
            map.zoom(0.5);
        }

        DynamicPoint dynPoint = createLocateDynPoint(businessX, businessY);
        locateDynamicView.addElement(dynPoint);

        createLocateTrackingPolygon(businessGeoRegion);

        map.refresh();
    }

    public void clearLocateMarker() {
        locateDynamicView.clear();
        clearLocateTrackingPolygon();
    }

    private void clearLocateTrackingPolygon() {

        TrackingLayer trackingLayer = map.getTrackingLayer();
        int geoId = trackingLayer.indexOf(LOCATE_TRACKING_POLYGON_NAME);

        if (geoId == -1) {
            return;
        }

        trackingLayer.remove(geoId);

    }

    private void createLocateTrackingPolygon(GeoRegion polygon) {

        TrackingLayer trackingLayer = map.getTrackingLayer();

        GeoStyle polygonStyle = new GeoStyle();
        polygonStyle.setLineColor(new Color(100,149,237));
        polygonStyle.setLineWidth(0.5);
        polygonStyle.setFillOpaqueRate(0);
        polygon.setStyle(polygonStyle);

        trackingLayer.add(polygon, LOCATE_TRACKING_POLYGON_NAME);

    }

    private DynamicPoint createLocateDynPoint(double pointX, double pointY) {

        DynamicStyle pointStyle = new DynamicStyle();
        Bitmap pointBitmap = BitmapFactory.decodeResource(mapContext.getResources(), R.drawable.locate_point);
        pointStyle.setBackground(pointBitmap);

        DynamicPoint dynPoint = new DynamicPoint();
        dynPoint.setPoint(new Point2D(pointX, pointY));
        dynPoint.setStyle(pointStyle);
        dynPoint.setAlignment(DynamicAlignment.BOTTOM);

        return dynPoint;

    }

}
