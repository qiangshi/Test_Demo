package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.DataCategoryConstant;
import com.supermap.demo.test.map.bean.DataPagingModel;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.demo.test.map.dataservice.recordsetconvert.FullSearchRsConvert;
import com.supermap.demo.test.supermap.utils.DatasetVectorQueryUtils;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.GeoPoint;
import com.supermap.data.Recordset;

import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 全库查询服务
 * @Date: 2019/4/14
 */
public class FullSearchService extends BaseDataService<FullSearchItem> {

    private final String FULL_SEARCH_DATASET_NAME = "g_search";

    public FullSearchService() {

    }

    private DatasetVector getFullSearchDataset() {

        return (DatasetVector)getBusinessDatasource().getDatasets().get(FULL_SEARCH_DATASET_NAME);

    }


    /**
     * 周边查询
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     * @param radius 周边查询半径，单位米
     * @param attributeFilter 附加的属性查询条件，相当于 SQL 语句中的 Where 子句。
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据
     * @return 查询结果列表
     */
    private DataPagingModel<FullSearchItem> searchByRadius(double centerX, double centerY, double radius, String attributeFilter, int pageNum, int page) {

        DataPagingModel<FullSearchItem> dataPagingModel = new DataPagingModel<FullSearchItem>(pageNum, page);

        DatasetVector fullSearchDataset = getFullSearchDataset();

        String[] orderBy = new String[] {"SmID asc " + buildPagingClause(pageNum, page, 1)};;

        GeoPoint centerPoint = new GeoPoint(centerX, centerY);

        Recordset recordset = DatasetVectorQueryUtils.queryByBuffer(fullSearchDataset, centerPoint, radius, attributeFilter, CursorType.STATIC, false, orderBy);

        if (recordset.getRecordCount() > pageNum) {
            dataPagingModel.setPageEnd(false);
        } else {
            dataPagingModel.setPageEnd(true);
        }

        List<FullSearchItem> resultList = convertRecordsetToList(recordset, pageNum, new FullSearchRsConvert());

        dataPagingModel.setDataList(resultList);

        recordset.close();
        recordset.dispose();
        fullSearchDataset.close();

        return dataPagingModel;
    }

    /**
     * 根据关键字查询
     * @param key 关键字
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据  page从1开始
     * @return 查询结果列表
     */
    public DataPagingModel<FullSearchItem> searchKey(String key, int pageNum, int page) {

        DataPagingModel<FullSearchItem> dataPagingModel = new DataPagingModel<FullSearchItem>(pageNum, page);

        DatasetVector fullSearchDataset = getFullSearchDataset();

        String attributeFilter = "name like '%" + key + "%' "
                                + "or search1 like '%" + key + "%' "
                                + "or search2 like '%" + key + "%' "
                                + "or search3 like '%" + key + "%' "
                                + buildPagingClause(pageNum, page, 1);

        Recordset recordset = DatasetVectorQueryUtils.queryByAttributeFilter(fullSearchDataset, attributeFilter, CursorType.STATIC, false, null);

        if (recordset.getRecordCount() > pageNum) {
            dataPagingModel.setPageEnd(false);
        } else {
            dataPagingModel.setPageEnd(true);
        }

        List<FullSearchItem> resultList = convertRecordsetToList(recordset, pageNum, new FullSearchRsConvert());

        recordset.close();
        recordset.dispose();
        fullSearchDataset.close();

        dataPagingModel.setDataList(resultList);

        return dataPagingModel;
    }

    /**
     * 地图点选全库搜索
     * @param pointX 点选点的x坐标
     * @param pointY 点选点的y坐标
     * @param currentMapScale 当前地图比例尺
     * @return 查询结果。若没有查到，返回null
     */
    public FullSearchItem selectByPoint(double pointX, double pointY, double currentMapScale) {

        double searchRadius;

        if (currentMapScale > 0.00025) {
            searchRadius = 5;
        }
        else if (0.000125 < currentMapScale && currentMapScale <= 0.00025){
            searchRadius = 10;
        } else {
            searchRadius = 15;
        }

        DataPagingModel<FullSearchItem> searchDataPagingModel = searchByRadius(pointX, pointY, searchRadius, null, 1, 1);

        List<FullSearchItem> searchList = searchDataPagingModel.getDataList();

        if (searchList != null && searchList.size() > 0) {
            return searchList.get(0);
        } else {
            return null;
        }

    }

    /**
     * 审批周边查询
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     * @param radius 周边查询半径，单位米
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据
     * @return 查询结果列表
     */
    public DataPagingModel<FullSearchItem> searchApprovalByRadius(double centerX, double centerY, double radius, int pageNum, int page) {

        return searchApprovalByRadius(centerX, centerY, radius, null, pageNum, page);

    }

    /**
     * 审批周边查询（带年份）
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     * @param radius 周边查询半径，单位米
     * @param year 年份
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据
     * @return 查询结果列表
     */
    public DataPagingModel<FullSearchItem> searchApprovalByRadius(double centerX, double centerY, double radius, String year, int pageNum, int page) {

        String attributeFilter = "CATEGORY in ('" + DataCategoryConstant.APPROVAL_CONTRACT_CODE + "'" +
                                            ", '" + DataCategoryConstant.APPROVAL_ALLOT_CODE + "')";

        if (year != null && !year.equals("")) {
            attributeFilter = attributeFilter + " and YEAR='" + year + "'";
        }

        DataPagingModel<FullSearchItem> searchDataPagingModel = searchByRadius(centerX, centerY, radius, attributeFilter, pageNum, page);

        return searchDataPagingModel;

    }

    /**
     * 控规周边查询
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     * @param radius 周边查询半径，单位米
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据
     * @return 查询结果列表
     */
    public DataPagingModel<FullSearchItem> searchPlanByRadius(double centerX, double centerY, double radius, int pageNum, int page) {

        return searchPlanByRadius(centerX, centerY, radius, null, pageNum, page);

    }

    /**
     * 控规周边查询（带年份）
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     * @param radius 周边查询半径，单位米
     * @param year 年份
     * @param pageNum 每页显示的条数
     * @param page 要获取第几页的数据
     * @return 查询结果列表
     */
    public DataPagingModel<FullSearchItem> searchPlanByRadius(double centerX, double centerY, double radius, String year, int pageNum, int page) {

        String attributeFilter = "CATEGORY in ('" + DataCategoryConstant.PLAN_CODE + "')";

        if (year != null && !year.equals("")) {
            attributeFilter = attributeFilter + " and YEAR='" + year + "'";
        }

        DataPagingModel<FullSearchItem> searchDataPagingModel = searchByRadius(centerX, centerY, radius, attributeFilter, pageNum, page);

        return searchDataPagingModel;

    }

}
