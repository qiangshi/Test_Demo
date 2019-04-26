package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.DatasourceManager;
import com.supermap.demo.test.map.dataservice.recordsetconvert.IRecordsetConvert;
import com.supermap.demo.test.supermap.utils.DatasetVectorQueryUtils;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.GeoRegion;
import com.supermap.data.Recordset;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 数据服务基类
 * @Date: 2019/4/14
 */
public class BaseDataService<DataBean> {

    protected Datasource getBusinessDatasource() {

        return DatasourceManager.getBusinessDatasource();

    }

    protected String buildPagingClause (int pageNum, int page, int moreNum) {

        return " limit " + String.valueOf(pageNum + moreNum) + " offset " + String.valueOf(pageNum * (page - 1));

    }

    protected List<DataBean> convertRecordsetToList(Recordset recordset, int convertTop, IRecordsetConvert<DataBean> recordsetConvert) {

        List<DataBean> resultList = new ArrayList<DataBean>();
        int count = 1;

        recordset.moveFirst();

        while (!recordset.isEOF()) {

            if (convertTop > 0 && count > convertTop) {
                break;
            }

            DataBean dataBean = recordsetConvert.convertRsItemToDataBean(recordset);

            resultList.add(dataBean);

            recordset.moveNext();

            count++;
        }

        return resultList;
    }

    /**
     * 获取业务数据的范围图形
     * @param datasetName 业务数据的数据集名称
     * @param bid 业务数据bid
     * @return 如果没有取到图形返回null
     */
    protected GeoRegion getBusinessGeoRegion(String datasetName, int bid) {

        GeoRegion result = null;

        DatasetVector datasetVector = (DatasetVector)getBusinessDatasource().getDatasets().get(datasetName);

        String attributeFilter = "BID = " + String.valueOf(bid);

        Recordset rs = DatasetVectorQueryUtils.queryByAttributeFilter(datasetVector, attributeFilter,
                                                                      CursorType.STATIC, true, null);

        rs.moveFirst();

        if (!rs.isEOF()) {
            result = (GeoRegion)rs.getGeometry();
        }

        rs.close();
        rs.dispose();
        datasetVector.close();

        return result;
    }

}
