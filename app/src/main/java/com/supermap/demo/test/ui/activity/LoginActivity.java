package com.supermap.demo.test.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermap.demo.test.AppLike;
import com.supermap.demo.test.R;
import com.supermap.demo.test.mvp.presenter.login.LoginPresenter;
import com.supermap.demo.test.mvp.view.login.LoginView;
import com.supermap.demo.test.router.RouterCons;
import com.sankuai.waimai.router.annotation.RouterUri;

import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_LOGIN})
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassWord;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_close_pass)
    ImageView imgClosePass;

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initDataAndEvent() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = etPhone.getText().toString().length();
                if (length >= 1) {//如果长度大于等于1,显示删除图片
                    imgClose.setVisibility(View.VISIBLE);
                } else {//否则隐藏
                    imgClose.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = etPassWord.getText().toString().length();
                if (length >= 1) {//如果长度大于等于1,显示删除图片
                    imgClosePass.setVisibility(View.VISIBLE);
                } else {//否则隐藏
                    imgClosePass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.tv_login, R.id.img_close,R.id.img_close_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                if (imgClose.getVisibility() == View.VISIBLE) {
                    etPhone.setText("");
                    imgClose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_login:
                mPresenter.requestLogin(this, etPhone.getText().toString(), etPassWord.getText().toString());
                break;
            case R.id.img_close_pass:
                if (imgClosePass.getVisibility() == View.VISIBLE) {
                    etPassWord.setText("");
                    imgClosePass.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppLike.getSelf().quitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
