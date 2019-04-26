package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.demo.test.map.DataCategoryConstant;
import com.supermap.demo.test.map.bean.ApprovalInfo;
import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批信息记录集转换
 * @Date: 2019/4/23
 */
public class ApprovalInfoConvert implements IRecordsetConvert<ApprovalInfo> {

    @Override
    public ApprovalInfo convertRsItemToDataBean(Recordset recordset) {

        ApprovalInfo approvalInfo = new ApprovalInfo();

        approvalInfo.setBid((Integer)recordset.getFieldValue("BID"));
        approvalInfo.setName((String)recordset.getFieldValue("NAME"));
        approvalInfo.setCoaBid((Integer)recordset.getFieldValue("COA_BID"));
        approvalInfo.setLandsupplyBid((Integer)recordset.getFieldValue("LANDSUPPLY_BID"));
        approvalInfo.setReserveBid((Integer)recordset.getFieldValue("RESERVE_BID"));
        approvalInfo.setContractBid((Integer)recordset.getFieldValue("CONTRACT_BID"));
        approvalInfo.setAllotBid((Integer)recordset.getFieldValue("ALLOT_BID"));
        approvalInfo.setPlanBid((Integer)recordset.getFieldValue("PLAN_BID"));

        approvalInfo.setCategory(null);
        approvalInfo.setPlanInfo(null);
        approvalInfo.setContractInfo(null);
        approvalInfo.setAllotInfo(null);

        approvalInfo.setHasCoa(false);
        approvalInfo.setHasLandsupply(false);
        approvalInfo.setHasReserve(false);
        approvalInfo.setHasContract(false);
        approvalInfo.setHasAllot(false);

        if (approvalInfo.getCoaBid() != null && approvalInfo.getCoaBid() > 0) {
            approvalInfo.setHasCoa(true);
        }

        if (approvalInfo.getLandsupplyBid() != null && approvalInfo.getLandsupplyBid() > 0) {
            approvalInfo.setHasLandsupply(true);
        }

        if (approvalInfo.getReserveBid() != null && approvalInfo.getReserveBid() > 0) {
            approvalInfo.setHasReserve(true);
        }

        if (approvalInfo.getContractBid() != null && approvalInfo.getContractBid() > 0) {
            approvalInfo.setHasContract(true);
        }

        if (approvalInfo.getAllotBid() != null && approvalInfo.getAllotBid() > 0) {
            approvalInfo.setHasAllot(true);
        }

        if (approvalInfo.isHasContract() || approvalInfo.isHasAllot()) {
            if (approvalInfo.isHasContract()) {
                approvalInfo.setCategory(DataCategoryConstant.APPROVAL_CONTRACT_CODE);
            } else {
                approvalInfo.setCategory(DataCategoryConstant.APPROVAL_ALLOT_CODE);
            }
        }

        return approvalInfo;
    }
}
