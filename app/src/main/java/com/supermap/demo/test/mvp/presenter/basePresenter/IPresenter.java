package com.supermap.demo.test.mvp.presenter.basePresenter;


import com.supermap.demo.test.mvp.view.IView;

/**
 * Created by pantianxiong on 2018/4/23.
 * 描述：
 */

public interface IPresenter<V extends IView> {
    void detachView();
}
