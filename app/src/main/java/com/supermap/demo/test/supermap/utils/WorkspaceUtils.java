package com.supermap.demo.test.supermap.utils;

import com.supermap.data.Datasource;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 工作空间工具类
 * @Date: 2019/4/11
 */
public class WorkspaceUtils {

     /**
     * 打开文件型工作空间
     * @param wsFilePath 工作空间文件路径
     * @param wsType 工作空间类型
     * @param password 工作空间密码。没有密码填null或空字符串
     * @return 工作空间
     * @throws Exception
     */
    public static Workspace openFileWorkspace(String wsFilePath, WorkspaceType wsType, String password) throws Exception {

        WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
        workspaceConnectionInfo.setServer(wsFilePath);
        workspaceConnectionInfo.setType(wsType);
        workspaceConnectionInfo.setPassword(password);

        Workspace workspace = new Workspace();
        boolean isOpen = workspace.open(workspaceConnectionInfo);

        workspaceConnectionInfo.dispose();

        if (isOpen != true) {
            throw new Exception("打开工作空间失败！");
        }

        return workspace;

    }

    /**
     * 释放工作空间
     * @param workspace 工作空间
     */
    public static void releaseWorkspace(Workspace workspace) {
        workspace.close();
        workspace.dispose();
    }

    /**
     * 获取工作空间中的指定数据源
     * @param workspace 工作空间
     * @param dsAlias 要获取的数据源的别名
     * @return 数据源
     */
    public static Datasource getDatasourceFromWs(Workspace workspace, String dsAlias) {

        return workspace.getDatasources().get(dsAlias);
    }
}
