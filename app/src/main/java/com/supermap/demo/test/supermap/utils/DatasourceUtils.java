package com.supermap.demo.test.supermap.utils;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Workspace;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 数据源工具类
 * @Date: 2019/4/13
 */
public class DatasourceUtils {

    /***
     * 打开udb数据源
     * @param workspace 用于打开数据源的工作空间
     * @param udbPath udb文件路径
     * @param dsAlias 数据源别名，用于工作空间中对数据源的唯一标识
     * @param password 数据源密码。没有密码填null或空字符串
     * @return
     */
    public static Datasource openUdbDatasource(Workspace workspace, String udbPath, String dsAlias, String password) {

        DatasourceConnectionInfo datasourceConnection = new DatasourceConnectionInfo();

        datasourceConnection.setEngineType(EngineType.UDB);
        datasourceConnection.setServer(udbPath);
        datasourceConnection.setAlias(dsAlias);
        datasourceConnection.setPassword(password);

        Datasource datasource = workspace.getDatasources().open(datasourceConnection);

        datasourceConnection.dispose();

        return datasource;
    }

    /**
     * 打开栅格瓦片数据源
     * @param workspace 用于打开数据源的工作空间
     * @param sciPath 栅格瓦片sci文件路径
     * @param dsAlias 数据源别名，用于工作空间中对数据源的唯一标识
     * @param password 数据源密码。没有密码填null或空字符串
     * @return
     */
    public static Datasource openGridTilesDatasource(Workspace workspace, String sciPath, String dsAlias, String password) {

        DatasourceConnectionInfo datasourceConnection = new DatasourceConnectionInfo();

        datasourceConnection.setEngineType(EngineType.Rest);
        datasourceConnection.setServer(sciPath);
        datasourceConnection.setAlias(dsAlias);
        datasourceConnection.setPassword(password);

        Datasource datasource = workspace.getDatasources().open(datasourceConnection);

        datasourceConnection.dispose();

        return datasource;
    }

}
