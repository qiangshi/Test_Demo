package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.demo.test.map.DataCategoryConstant;
import com.supermap.demo.test.map.bean.FullSearchItem;
import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 全库查询记录集转换
 * @Date: 2019/4/23
 */
public class FullSearchRsConvert implements IRecordsetConvert<FullSearchItem> {

    @Override
    public FullSearchItem convertRsItemToDataBean(Recordset fullSearchRecordset) {

        FullSearchItem fullSearchItem = new FullSearchItem();

        fullSearchItem.setId((Integer)fullSearchRecordset.getFieldValue("ID"));
        fullSearchItem.setName((String)fullSearchRecordset.getFieldValue("NAME"));
        fullSearchItem.setCategory( (String)fullSearchRecordset.getFieldValue("CATEGORY"));
        fullSearchItem.setMix((String)fullSearchRecordset.getFieldValue("MIX"));
        fullSearchItem.setBid((Integer)fullSearchRecordset.getFieldValue("BID"));
        fullSearchItem.setCoordX((Double)fullSearchRecordset.getFieldValue("SmX"));
        fullSearchItem.setCoordY((Double)fullSearchRecordset.getFieldValue("SmY"));
        fullSearchItem.setYear((String)fullSearchRecordset.getFieldValue("YEAR"));

        String itemCategory = fullSearchItem.getCategory();

        if (itemCategory.equals(DataCategoryConstant.APPROVAL_ALLOT_CODE)
                || itemCategory.equals(DataCategoryConstant.APPROVAL_CONTRACT_CODE)
                || itemCategory.equals(DataCategoryConstant.PLAN_CODE)) {

            if (itemCategory.equals(DataCategoryConstant.APPROVAL_ALLOT_CODE)
                    || itemCategory.equals(DataCategoryConstant.APPROVAL_CONTRACT_CODE) ) {
                fullSearchItem.setLabel(DataCategoryConstant.APPROVAL_LABEL);
            }

            if (itemCategory.equals(DataCategoryConstant.PLAN_CODE)) {
                fullSearchItem.setLabel(DataCategoryConstant.PLAN_LABEL);
            }

            fullSearchItem.setPoiItem(false);
        } else {
            fullSearchItem.setLabel("");
            fullSearchItem.setPoiItem(true);
        }

        return fullSearchItem;

    }

}
