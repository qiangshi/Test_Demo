package com.supermap.demo.test.map.dataservice.recordsetconvert;

import com.supermap.data.Recordset;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 图形数据记录集转换接口
 * @Date: 2019/4/23
 */
public interface IRecordsetConvert<T> {

    public T convertRsItemToDataBean(Recordset recordset);

}
