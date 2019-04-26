package com.supermap.demo.test.map;

import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.supermap.utils.WorkspaceUtils;
import com.supermap.data.Datasource;
import com.supermap.data.Workspace;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 数据源管理者
 * @Date: 2019/4/14
 */
public class DatasourceManager {

    // 数据工作空间中的业务数据源
    private static Datasource businessDatasource;

    public static void initBusinessDatasource(Workspace dbWorkspace) {

        businessDatasource = WorkspaceUtils.getDatasourceFromWs(dbWorkspace, Constant.SM_BUSINESS_DS_ALIAS);

    }

    public static Datasource getBusinessDatasource() {
        return businessDatasource;
    }
}
