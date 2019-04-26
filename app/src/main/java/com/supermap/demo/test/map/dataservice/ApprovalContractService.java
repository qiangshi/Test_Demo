package com.supermap.demo.test.map.dataservice;

import com.supermap.demo.test.map.bean.ApprovalContractInfo;
import com.supermap.demo.test.map.dataservice.recordsetconvert.ApprovalContractInfoConvert;
import com.supermap.demo.test.supermap.utils.DatasetVectorQueryUtils;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Recordset;

import java.util.List;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批合同数据服务
 * @Date: 2019/4/23
 */
public class ApprovalContractService extends BaseDataService<ApprovalContractInfo> {

    private final String APPROVAL_CONTRACT_DATASET_NAME = "m_approval_contract";

    public ApprovalContractService() {

    }

    private DatasetVector getApprovalContractDataset() {

        return (DatasetVector)getBusinessDatasource().getDatasets().get(APPROVAL_CONTRACT_DATASET_NAME);

    }

    /**
     * 获取审批合同信息
     * @param bid 审批合同bid
     * @return 如果没有取到数据返回null
     */
    public ApprovalContractInfo getApprovalContractInfo(int bid) {

        ApprovalContractInfo approvalContractInfo = null;

        DatasetVector approvalContractDataset = getApprovalContractDataset();

        String attributeFilter = "BID = " + bid;

        Recordset approvalContractRs = DatasetVectorQueryUtils.queryByAttributeFilter(approvalContractDataset, attributeFilter, CursorType.STATIC,
                                                                          false, null);

        List<ApprovalContractInfo> approvalContractList = convertRecordsetToList(approvalContractRs, -1, new ApprovalContractInfoConvert());

        approvalContractRs.close();
        approvalContractRs.dispose();
        approvalContractDataset.close();

        if (approvalContractList.size() > 0) {
            approvalContractInfo = approvalContractList.get(0);
        }

        return approvalContractInfo;
    }

}
