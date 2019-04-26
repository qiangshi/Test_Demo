package com.supermap.demo.test.ui.activity.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.manager.UserDBManager;
import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.activity.BaseActivity;
import com.supermap.demo.test.utils.SharePreferenceUtil;
import com.sankuai.waimai.router.annotation.RouterUri;
import com.sankuai.waimai.router.common.DefaultUriRequest;

import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_USER})
public class UserActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_user)
    TextView tvUser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initDataAndEvent() {
        UserDB userDB = UserDBManager.getInstance().getUserInfo();
        tvUser.setText(userDB.getUserName());
    }


    @OnClick({R.id.btn_back, R.id.ll_favorite, R.id.ll_modify_password, R.id.ll_about,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_favorite:
                toastMessage(R.string.favorite);
                break;
            case R.id.ll_modify_password:
                new DefaultUriRequest(this,RouterCons.CREATE_MODIFY_PASSWORD)
                        .start();
                break;
            case R.id.ll_about:
                toastMessage(R.string.about);
                break;
            case R.id.tv_logout:
                SharePreferenceUtil.putBoolean(Constant.IS_LOGIN,false);
                new DefaultUriRequest(this, RouterCons.CREATE_LOGIN)
                        .start();
                finish();
                break;
        }
    }
}
