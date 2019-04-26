package com.supermap.demo.test.mvp.presenter.login;

import android.text.TextUtils;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.mvp.view.login.LoginView;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.MD5Util;
import com.supermap.demo.test.utils.SharePreferenceUtil;
import com.sankuai.waimai.router.common.DefaultUriRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenghaiqiang on 2019/1/16.
 */

public class LoginPresenter extends BasePresenter<LoginView> {


    private UserDB userDB;

    public LoginPresenter(LoginView view) {
        super(view);
    }


    /**
     * 手机验证码登录
     *
     * @param context
     * @param mobilePhone
     * @param passWord
     */
    public void requestLogin(BaseActivity context, String mobilePhone, String passWord) {

        if (TextUtils.isEmpty(mobilePhone)) {
            //请输入用户账号
            mView.toastMessage(R.string.please_in_user_name);
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            mView.toastMessage(R.string.msg_password_code_error);
            return;
        }
        if (checkLogin(mobilePhone, MD5Util.getMD5Str(passWord))) {
            SharePreferenceUtil.putBoolean(Constant.IS_LOGIN, true);
            SharePreferenceUtil.putString(Constant.USER_ID, userDB.userId);
            new DefaultUriRequest(context, RouterCons.CREATE_MAIN)
                    .start();
            context.finish();
        } else {
            mView.toastMessage(R.string.code_or_password_error);
        }

    }

    /**
     * 检出用户信息
     */
    private boolean checkLogin(String mobilePhone, String passWord) {
        List<UserDB> userDBS = new ArrayList<>();
        userDBS = UserDBManager.getInstance().queryPasswordByUserName(mobilePhone);
        for (UserDB user : userDBS) {
            if (passWord.equals(user.passWord)) {
                userDB = user;
                return true;
            }
        }
        return false;
    }


}
