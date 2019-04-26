package com.supermap.demo.test.mvp.presenter;

import com.supermap.demo.test.mvp.presenter.basePresenter.BasePresenter;
import com.supermap.demo.test.mvp.view.DetailView;

/**
 * @ClassName: DetailPresenter
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 21:47
 */
public class DetailPresenter extends BasePresenter<DetailView> {
    public DetailPresenter(DetailView view) {
        super(view);
    }
}
