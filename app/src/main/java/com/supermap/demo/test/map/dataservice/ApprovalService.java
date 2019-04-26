package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.DataCategoryConstant;
import com.supermap.demo.test.map.bean.ApprovalAllotInfo;
import com.supermap.demo.test.map.bean.ApprovalContractInfo;
import com.supermap.demo.test.map.bean.ApprovalInfo;
import com.supermap.demo.test.map.bean.PlanInfo;
import com.supermap.demo.test.map.dataservice.recordsetconvert.ApprovalInfoConvert;
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
 * @Description: 审批数据服务
 * @Date: 2019/4/22
 */
public class ApprovalService extends BaseDataService<ApprovalInfo> {

    private final String APPROVAL_REGION_DATASET_NAME = "g_approval";

    private final String APPROVAL_POINT_DATASET_NAME = "g_approval_point";

    public ApprovalService() {

    }

    /**
     * 获取审批的范围图形
     * @param bid 审批bid
     * @return 如果没有取到图形返回null
     */
    public GeoRegion getApprovalGeoRegion(int bid) {

        return getBusinessGeoRegion(APPROVAL_REGION_DATASET_NAME, bid);
    }

    private DatasetVector getApprovalPointDataset() {

        return (DatasetVector)getBusinessDatasource().getDatasets().get(APPROVAL_POINT_DATASET_NAME);

    }

    /**
     * 获取审批主信息
     * @param bid 审批bid
     * @return 如果没有取到数据返回null
     */
    public ApprovalInfo getApprovalMainInfo(int bid) {

        ApprovalInfo approvalInfo = null;

        DatasetVector approvalPointDataset = getApprovalPointDataset();

        String attributeFilter = "BID = " + bid;

        Recordset approvalPointRs = DatasetVectorQueryUtils.queryByAttributeFilter(approvalPointDataset, attributeFilter, CursorType.STATIC,
                                                                        false, null);

        List<ApprovalInfo> approvalInfoList = convertRecordsetToList(approvalPointRs, -1, new ApprovalInfoConvert());

        approvalPointRs.close();
        approvalPointRs.dispose();
        approvalPointDataset.close();

        if (approvalInfoList.size() > 0) {
            approvalInfo = approvalInfoList.get(0);
        }

        return approvalInfo;
    }


    /**
     * 获取审批信息
     * @param bid 审批bid
     * @return 如果没有取到数据返回null
     */
    public ApprovalInfo getApprovalInfo(int bid) {

        ApprovalInfo approvalInfo = getApprovalMainInfo(bid);

        if (approvalInfo == null) {
            return null;
        }

        String category = approvalInfo.getCategory();

        if (category != null && category.equals(DataCategoryConstant.APPROVAL_CONTRACT_CODE)) {

            ApprovalContractService approvalContractService = new ApprovalContractService();
            ApprovalContractInfo approvalContractInfo = approvalContractService.getApprovalContractInfo(approvalInfo.getContractBid());

            approvalInfo.setContractInfo(approvalContractInfo);

        } else if (category != null && category.equals(DataCategoryConstant.APPROVAL_ALLOT_CODE)) {

            ApprovalAllotService approvalAllotService = new ApprovalAllotService();
            ApprovalAllotInfo approvalAllotInfo = approvalAllotService.getApprovalAllotInfo(approvalInfo.getAllotBid());

            approvalInfo.setAllotInfo(approvalAllotInfo);
        }

        if (approvalInfo.getPlanBid() != null) {

            PlanService planService = new PlanService();
            PlanInfo planInfo = planService.getPlanMainInfo(approvalInfo.getPlanBid());

            approvalInfo.setPlanInfo(planInfo);
        }

        return approvalInfo;
    }

}
