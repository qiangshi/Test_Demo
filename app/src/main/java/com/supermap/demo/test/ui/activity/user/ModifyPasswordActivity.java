package com.supermap.demo.test.ui.activity.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.supermap.demo.test.R;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.DataCheckUtils;
import com.supermap.demo.test.utils.MD5Util;
import com.sankuai.waimai.router.annotation.RouterUri;

import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_MODIFY_PASSWORD})
public class ModifyPasswordActivity extends BaseActivity {


    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm)
    EditText etConfirm;

    private UserDB userDB;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        userDB = UserDBManager.getInstance().getUserInfo();
    }


    @OnClick({R.id.btn_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_confirm:
                if(TextUtils.isEmpty(etOldPassword.getText().toString().trim())){
                    toastMessage(R.string.please_input_old_password);
                    return;
                }
                if(TextUtils.isEmpty(etNewPassword.getText().toString().trim())){
                    toastMessage(R.string.please_input_new_password);
                    return;
                }else {
                    if(!DataCheckUtils.checkPassword(etNewPassword.getText().toString().trim())){
                        toastMessage(R.string.password_hint);
                        return;
                    }
                }
                if(TextUtils.isEmpty(etConfirm.getText().toString().trim())){
                    toastMessage(R.string.please_confirm_new_password);
                    return;
                }
                if(!etNewPassword.getText().toString().equals(etConfirm.getText().toString())){
                    toastMessage(R.string.password_error);
                    return;
                }
                if(!userDB.getPassWord().equals(MD5Util.getMD5Str(etOldPassword.getText().toString()))){
                    toastMessage(R.string.old_password_error);
                    return;
                }
                UserDBManager.getInstance().updateUserPassword(MD5Util.getMD5Str(etNewPassword.getText().toString()));
                toastMessage(R.string.modify_success);
                finish();
                break;
        }
    }
}
