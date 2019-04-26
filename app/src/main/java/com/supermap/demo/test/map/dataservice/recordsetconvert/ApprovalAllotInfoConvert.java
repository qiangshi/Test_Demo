package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.demo.test.map.bean.ApprovalAllotInfo;
import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批划拨信息记录集转换
 * @Date: 2019/4/23
 */
public class ApprovalAllotInfoConvert implements IRecordsetConvert<ApprovalAllotInfo> {

    @Override
    public ApprovalAllotInfo convertRsItemToDataBean(Recordset recordset) {

        ApprovalAllotInfo approvalAllotInfo = new ApprovalAllotInfo();

        approvalAllotInfo.setBid((Integer)recordset.getFieldValue("BID"));
        approvalAllotInfo.setSerialNumber((String)recordset.getFieldValue("SERIAL_NUMBER"));
        approvalAllotInfo.setCorpname((String)recordset.getFieldValue("CORPNAME"));
        approvalAllotInfo.setPublishDate((String)recordset.getFieldValue("PUBLISH_DATE"));
        approvalAllotInfo.setLandUseType((String)recordset.getFieldValue("LAND_USE_TYPE"));

        return approvalAllotInfo;

    }
}
