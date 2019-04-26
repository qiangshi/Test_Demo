package com.supermap.demo.test.supermap.utils;

import com.supermap.analyst.BufferAnalystGeometry;
import com.supermap.analyst.BufferAnalystParameter;
import com.supermap.analyst.BufferEndType;
import com.supermap.analyst.BufferRadiusUnit;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.GeoRegion;
import com.supermap.data.Geometry;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.SpatialQueryMode;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 矢量数据集查询工具类
 * @Date: 2019/4/14
 */
public class DatasetVectorQueryUtils {

    /**
     * 对矢量数据集进行属性查询
     * @param datasetVector 被查询的矢量数据集
     * @param attributeFilter 查询条件，相当于 SQL 语句中的 Where 子句。
     * @param cursorType 游标类型。若数据为只读使用CursorType.STATIC，提高性能。
     * @param hasGeometry 是否返回图形数据
     * @param orderBy 设置 SQL 查询排序的字段
     * @return 结果数据集
     */
    public static Recordset queryByAttributeFilter(DatasetVector datasetVector, String attributeFilter, CursorType cursorType, boolean hasGeometry, String[] orderBy) {

        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setAttributeFilter(attributeFilter);
        queryParameter.setCursorType(cursorType);
        queryParameter.setHasGeometry(hasGeometry);

        if (orderBy != null && orderBy.length > 0) {
            queryParameter.setOrderBy(orderBy);
        }

        return datasetVector.query(queryParameter);
    }

    /**
     * 对矢量数据集进行空间查询
     * @param datasetVector 被查询的矢量数据集
     * @param queryGeo 用于空间查询的图形
     * @param attributeFilter 附加的属性查询条件，相当于 SQL 语句中的 Where 子句。
     * @param cursorType 游标类型。若数据为只读使用CursorType.STATIC，提高性能。
     * @param hasGeometry 是否返回图形数据
     * @param orderBy 设置 SQL 查询排序的字段
     * @return 结果数据集
     */
    public static Recordset queryBySpatialFilter(DatasetVector datasetVector, Geometry queryGeo, String attributeFilter, CursorType cursorType, boolean hasGeometry, String[] orderBy) {

        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setCursorType(cursorType);
        queryParameter.setHasGeometry(hasGeometry);
        queryParameter.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
        queryParameter.setSpatialQueryObject(queryGeo);

        if (attributeFilter != null && !attributeFilter.equals("")) {

            queryParameter.setAttributeFilter(attributeFilter);
        }

        if (orderBy != null && orderBy.length > 0) {
            queryParameter.setOrderBy(orderBy);
        }

        return datasetVector.query(queryParameter);
    }

    /**
     * 对矢量数据集进行缓冲空间查询
     * @param datasetVector 被查询的矢量数据集
     * @param bufferGeo 用于缓冲空间查询的图形
     * @param bufferDistance 缓冲范围，单位米
     * @param attributeFilter 附加的属性查询条件，相当于 SQL 语句中的 Where 子句。
     * @param cursorType 游标类型。若数据为只读使用CursorType.STATIC，提高性能。
     * @param hasGeometry 是否返回图形数据
     * @param orderBy 设置 SQL 查询排序的字段
     * @return 结果数据集
     */
    public static Recordset queryByBuffer(DatasetVector datasetVector, Geometry bufferGeo, double bufferDistance, String attributeFilter, CursorType cursorType, boolean hasGeometry, String[] orderBy) {
        PrjCoordSys prj = datasetVector.getPrjCoordSys();

        BufferAnalystParameter bufferAnalystParameter = new BufferAnalystParameter();
        bufferAnalystParameter.setEndType(BufferEndType.ROUND);
        bufferAnalystParameter.setLeftDistance(bufferDistance);
        bufferAnalystParameter.setRightDistance(bufferDistance);
        bufferAnalystParameter.setSemicircleLineSegment(25);
        bufferAnalystParameter.setRadiusUnit(BufferRadiusUnit.Meter);

        GeoRegion bufferAnalystGeo = BufferAnalystGeometry.createBuffer(bufferGeo, bufferAnalystParameter, prj);

        return queryBySpatialFilter(datasetVector, bufferAnalystGeo, attributeFilter, cursorType, hasGeometry, orderBy);

    }
}
