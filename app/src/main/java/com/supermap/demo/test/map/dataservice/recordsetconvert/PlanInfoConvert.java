package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.demo.test.map.bean.PlanInfo;
import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 规划信息记录集转换
 * @Date: 2019/4/23
 */
public class PlanInfoConvert implements IRecordsetConvert<PlanInfo> {

    @Override
    public PlanInfo convertRsItemToDataBean(Recordset recordset) {

        PlanInfo planInfo = new PlanInfo();

        planInfo.setBid((Integer)recordset.getFieldValue("bid"));
        planInfo.setName((String)recordset.getFieldValue("name"));
        planInfo.setState((String)recordset.getFieldValue("state"));
        planInfo.setPlotName((String)recordset.getFieldValue("plot_name"));
        planInfo.setDkarea((Double)recordset.getFieldValue("dkarea"));
        planInfo.setVolRatio((Double)recordset.getFieldValue("vol_ratio"));
        planInfo.setBldgHlmt((Double)recordset.getFieldValue("bldg_hlmt"));
        planInfo.setApprovalBid((Integer)recordset.getFieldValue("approval_bid"));

        planInfo.setApprovalInfo(null);

        return planInfo;
    }

}
