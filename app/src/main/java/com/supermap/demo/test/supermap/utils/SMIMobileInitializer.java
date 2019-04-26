package com.supermap.demo.test.supermap.utils;

import android.content.Context;

import com.supermap.data.Environment;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 超图iMobile初始化工具
 * @Date: 2019/4/13
 */
public class SMIMobileInitializer {

    /**
     * 超图iMobile初始化工具（包括许可证初始化）。必须在主线程中调用该方法。
     * @param licensePath 许可证目录路径
     * @param temporaryPath 存放临时数据的目录路径
     * @param webCacheDirectory web缓存目录路径
     * @param fontsPath 字体目录路径
     * @param context android上下文环境
     */
    public static void initializeEnv(String licensePath, String temporaryPath, String webCacheDirectory, String fontsPath, Context context) {

        Environment.setLicensePath(licensePath);
        Environment.setTemporaryPath(temporaryPath);
        Environment.setWebCacheDirectory(webCacheDirectory);

        //如果机器中默认不包括需要显示的字体，可以把相关字体文件放在参数所代表的路径中。
        //例如，如果需要显示阿拉伯文字（若机器中原先不包括相关字体文件），可以把需要的字体文件放在参数所代表的路径中。
        Environment.setFontsPath(fontsPath);

        Environment.initialization(context);
    }

}
