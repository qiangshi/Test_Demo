package com.supermap.demo.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.meituan.android.walle.WalleChannelReader;
import com.supermap.demo.test.database.DaoMaster;
import com.supermap.demo.test.database.DaoSession;
import com.supermap.demo.test.helper.OneMapOpenHelper;
import com.supermap.demo.test.helper.UpdateSQLHelper;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.MLog;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.common.DefaultRootUriHandler;
import com.sankuai.waimai.router.core.RootUriHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.simple.spiderman.SpiderMan;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.util.ArrayList;
import java.util.List;

import androidx.multidex.MultiDex;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/4 10:01
 */
public class AppLike extends DefaultApplicationLike {

    private List<BaseActivity> activities;
    private static App app;
    private static AppLike self;
    private static DaoSession daoSession;

    private float latitude;
    private float longitude;
    private String curPosition;

    public static DaoSession getDaoInstance() {
        return daoSession;
    }

    public void setDaoInstance(DaoSession session) {
        daoSession = session;
    }

    public AppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    public static App getApp() {
        return app;
    }

    public static AppLike getSelf() {
        return self;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initBugly();
        initGreenDao();
        initWMRouter();
        activities = new ArrayList<>();
    }

    //初始化greenDao
    private void initGreenDao() {
        DaoMaster daoMaster = new DaoMaster(OneMapOpenHelper.getInstance().getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        setDaoInstance(daoSession);
        if (OneMapOpenHelper.isVersion) {
            daoSession.getUserDBDao().insertInTx(UpdateSQLHelper.listOldUserDb);
            daoSession.getFavoriteDBDao().insertInTx(UpdateSQLHelper.listOldFavoriteDB);
            UpdateSQLHelper.listOldUserDb.clear();
        }
    }

    public List<BaseActivity> getActivities() {
        return activities;
    }

    private void init() {
        app = (App) getApplication();
        self = this;
        SpiderMan.init(app);
        MLog.init(true,"zeng");
    }

    private void initBugly() {
        String channel = WalleChannelReader.getChannel(getApplication());
        Bugly.setAppChannel(app, channel);
        Bugly.init(app, "5cd6e054f5", BuildConfig.DEBUG);//初始化bugly 第三个参数是否是调试模式
    }

    private void initWMRouter() {
        RootUriHandler rootHandler = new DefaultRootUriHandler(app);
        Router.init(rootHandler);
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.color_252631);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    // 退出应用
    public void quitApp() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}
