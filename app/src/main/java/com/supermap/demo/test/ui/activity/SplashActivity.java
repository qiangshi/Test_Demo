package com.supermap.demo.test.ui.activity;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.utils.AssetsFileCopyUtils;
import com.supermap.demo.test.utils.MD5Util;
import com.supermap.demo.test.utils.SharePreferenceUtil;
import com.sankuai.waimai.router.common.DefaultUriRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pantianxiong on 2018/5/8.
 * 描述：
 */
public class SplashActivity extends BaseActivity {

    private WeakHandler mHandler;

    private static class WeakHandler extends Handler {

        private WeakReference<SplashActivity> mReference;

        private WeakHandler(SplashActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mReference.get().toMAin();
                    break;
                case 2:
                    mReference.get().toLogin();
                    break;
            }
        }
    }


    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        mHandler = new WeakHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!hasCheckPermission) {
            checkPermission(permissions, getResources().getString(R.string.please_open_read_write_permission), new OnPermissionCheckSuccess() {
                @Override
                public void checkSuccess() {
                    initSplashView();
                }

                @Override
                public void checkFailed() {
                    finish();
                }
            });
        }
    }

    private void initSplashView() {
        boolean isFirst = SharePreferenceUtil.getBoolean(Constant.IS_FIRST, true);
        if (isFirst) {
            initUserList();
            initSuperMapConfigure();
        } else {
            boolean isLogin = SharePreferenceUtil.getBoolean(Constant.IS_LOGIN, false);
            if (isLogin) {
                mHandler.sendEmptyMessageDelayed(1, 1500);
            } else {
                mHandler.sendEmptyMessageDelayed(2, 1500);
            }
        }

    }

    /**
     * 初始化superMap配置信息
     */
    private void initSuperMapConfigure() {
        showLoading();
        AssetsFileCopyUtils.getInstance().copyAssetsToSD(this, Constant.SD_SAVE_DIR, Constant.SD_PROJ_DIR);
        AssetsFileCopyUtils.getInstance().setFileOperateCallback(new AssetsFileCopyUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                SharePreferenceUtil.putBoolean(Constant.IS_FIRST, false);
                hideLoading();
                boolean isLogin = SharePreferenceUtil.getBoolean(Constant.IS_LOGIN, false);
                if (isLogin) {
                    mHandler.sendEmptyMessageDelayed(1, 1500);
                } else {
                    mHandler.sendEmptyMessageDelayed(2, 1500);
                }
            }

            @Override
            public void onFailed(String error) {
                hideLoading();
                finish();
            }
        });
    }

    /**
     * 初始化用户列表
     */
    private void initUserList() {
        List<UserDB> userDBList = new ArrayList<>();
        userDBList.add(new UserDB("0", "pd1", MD5Util.getMD5Str("123456")));
        userDBList.add(new UserDB("1", "pd2", MD5Util.getMD5Str("123456")));
        userDBList.add(new UserDB("2", "pd3", MD5Util.getMD5Str("123456")));
        userDBList.add(new UserDB("3", "pd4", MD5Util.getMD5Str("123456")));
        userDBList.add(new UserDB("4", "pd5", MD5Util.getMD5Str("123456")));
        UserDBManager.getInstance().inserUserListInfo(userDBList);
    }

    /**
     * 跳转到 MainActivity
     */
    private void toMAin() {
        new DefaultUriRequest(this, RouterCons.CREATE_MAIN)
                .start();
        finish();
    }

    private void toLogin() {
        new DefaultUriRequest(this, RouterCons.CREATE_LOGIN)
                .start();
        finish();
    }


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeMessages(1);
            mHandler.removeMessages(2);
        }
        super.onDestroy();
    }
}
