package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.bean.ApprovalInfo;
import com.supermap.demo.test.map.bean.PlanInfo;
import com.supermap.demo.test.map.dataservice.recordsetconvert.PlanInfoConvert;
import com.supermap.demo.test.supermap.utils.DatasetVectorQueryUtils;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.GeoRegion;
import com.supermap.data.Recordset;

import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 规划数据服务
 * @Date: 2019/4/22
 */
public class PlanService extends BaseDataService<PlanInfo> {

    private final String PLAN_REGION_DATASET_NAME = "g_plan";

    private final String PLAN_POINT_DATASET_NAME = "g_plan_point";

    public PlanService() {

    }

    /**
     * 获取规划的范围图形
     * @param bid 规划bid
     * @return 如果没有取到图形返回null
     */
    public GeoRegion getPlanGeoRegion(int bid) {

        return getBusinessGeoRegion(PLAN_REGION_DATASET_NAME, bid);
    }

    private DatasetVector getPlanPointDataset() {

        return (DatasetVector)getBusinessDatasource().getDatasets().get(PLAN_POINT_DATASET_NAME);

    }

    /**
     * 获取规划信息
     * @param bid 规划bid
     * @return 如果没有取到数据返回null
     */
    public PlanInfo getPlanInfo(int bid) {

        PlanInfo planInfo = getPlanMainInfo(bid);

        if (planInfo == null) {
            return null;
        }

        if (planInfo.getApprovalBid() != null) {

            ApprovalService approvalService = new ApprovalService();
            ApprovalInfo approvalInfo = approvalService.getApprovalMainInfo(planInfo.getApprovalBid());

            planInfo.setApprovalInfo(approvalInfo);

        }

        return planInfo;
    }

    /**
     * 获取规划主信息
     * @param bid 规划bid
     * @return 如果没有取到数据返回null
     */
    public PlanInfo getPlanMainInfo(int bid) {

        PlanInfo planInfo = null;

        DatasetVector planPointDataset = getPlanPointDataset();

        String attributeFilter = "BID = " + bid;

        Recordset planRs = DatasetVectorQueryUtils.queryByAttributeFilter(planPointDataset, attributeFilter, CursorType.STATIC,
                false, null);

        List<PlanInfo> planList = convertRecordsetToList(planRs, -1, new PlanInfoConvert());

        planRs.close();
        planRs.dispose();
        planPointDataset.close();

        if (planList.size() > 0) {
            planInfo = planList.get(0);
        }

        return planInfo;
    }
}
