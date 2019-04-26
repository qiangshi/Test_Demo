package com.supermap.demo.test.ui.activity;


import android.widget.TextView;

import com.supermap.demo.test.R;
import com.supermap.demo.test.bean.DetailBusinessBean;
import com.supermap.demo.test.mvp.presenter.DetailPresenter;
import com.supermap.demo.test.mvp.view.DetailView;
import com.supermap.demo.test.router.RouterCons;
import com.supermap.demo.test.ui.adapter.DetailBusniessAdapter;
import com.sankuai.waimai.router.annotation.RouterUri;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@RouterUri(path = {RouterCons.CREATE_DETAIL})
public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailView {


    @BindView(R.id.tv_edit_code)
    TextView tvEditCode;
    @BindView(R.id.tv_pro_name)
    TextView tvProName;
    @BindView(R.id.tv_approvals_code)
    TextView tvApprovalsCode;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    private List<DetailBusinessBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected DetailPresenter getPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initDataAndEvent() {
        initList();
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        DetailBusniessAdapter adapter = new DetailBusniessAdapter(this,list);
        rvHistory.setAdapter(adapter);
    }

    private void initList() {
        list.add(new DetailBusinessBean("01-01","2018","证书1111","建设用地","6ff45f6798646"));
        list.add(new DetailBusinessBean("02-01","2018","证书2222","建设用地","6ff45f6798646"));
        list.add(new DetailBusinessBean("03-01","2018","证书3333","建设用地","6ff45f6798646"));
        list.add(new DetailBusinessBean("04-01","2018","证书4444","建设用地","6ff45f6798646"));
        list.add(new DetailBusinessBean("05-01","2018","证书5555","建设用地","6ff45f6798646"));
        list.add(new DetailBusinessBean("06-01","2018","证书6666","建设用地","6ff45f6798646"));
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
