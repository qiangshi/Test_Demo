package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.demo.test.map.bean.ApprovalContractInfo;
import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批合同信息记录集转换
 * @Date: 2019/4/23
 */
public class ApprovalContractInfoConvert implements IRecordsetConvert<ApprovalContractInfo> {

    @Override
    public ApprovalContractInfo convertRsItemToDataBean(Recordset recordset) {

        ApprovalContractInfo approvalContractInfo = new ApprovalContractInfo();

        approvalContractInfo.setBid((Integer)recordset.getFieldValue("BID"));
        approvalContractInfo.setAuditedNum((String)recordset.getFieldValue("AUDITED_NUM"));
        approvalContractInfo.setContractDate((String)recordset.getFieldValue("CONTRACT_DATE"));
        approvalContractInfo.setContractFee((Integer)recordset.getFieldValue("CONTRACT_FEE"));
        approvalContractInfo.setTotalArea((Double)recordset.getFieldValue("TOTAL_AREA"));

        return approvalContractInfo;

    }
}
