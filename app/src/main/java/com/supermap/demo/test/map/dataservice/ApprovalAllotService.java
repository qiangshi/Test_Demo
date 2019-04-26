package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.bean.ApprovalAllotInfo;
import com.supermap.demo.test.map.dataservice.recordsetconvert.ApprovalAllotInfoConvert;
import com.supermap.demo.test.supermap.utils.DatasetVectorQueryUtils;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Recordset;

import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批划拨数据服务
 * @Date: 2019/4/23
 */
public class ApprovalAllotService extends BaseDataService<ApprovalAllotInfo> {

    private final String APPROVAL_ALLOT_DATASET_NAME = "m_approval_allot";

    public ApprovalAllotService() {

    }

    private DatasetVector getApprovalAllotDataset() {

        return (DatasetVector)getBusinessDatasource().getDatasets().get(APPROVAL_ALLOT_DATASET_NAME);

    }

    /**
     * 获取审批划拨信息
     * @param bid 审批划拨bid
     * @return 如果没有取到数据返回null
     */
    public ApprovalAllotInfo getApprovalAllotInfo(int bid) {

        ApprovalAllotInfo approvalAllotInfo = null;

        DatasetVector approvalAllotDataset = getApprovalAllotDataset();

        String attributeFilter = "BID = " + bid;

        Recordset approvalAllotRs = DatasetVectorQueryUtils.queryByAttributeFilter(approvalAllotDataset, attributeFilter, CursorType.STATIC,
                                                                        false, null);

        List<ApprovalAllotInfo> approvalAllotList = convertRecordsetToList(approvalAllotRs, -1, new ApprovalAllotInfoConvert());

        approvalAllotRs.close();
        approvalAllotRs.dispose();
        approvalAllotDataset.close();

        if (approvalAllotList.size() > 0) {
            approvalAllotInfo = approvalAllotList.get(0);
        }

        return approvalAllotInfo;
    }

}
