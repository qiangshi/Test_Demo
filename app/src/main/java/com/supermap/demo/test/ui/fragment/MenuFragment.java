package com.supermap.demo.test.ui.fragment;

import com.supermap.demo.test.R;
import com.supermap.demo.test.mvp.presenter.basePresenter.IPresenter;
import com.supermap.demo.test.ui.adapter.MapMenuAdapter;
import com.supermap.demo.test.ui.adapter.MenuSpecialAdapter;
import com.supermap.demo.test.ui.adapter.MenuThemeAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by zenghaiqiang on 2019/4/10.
 * 描述：首页右边菜单
 */
public class MenuFragment extends BaseFragment{


    @BindView(R.id.rv_map)
    RecyclerView rvMap;
    @BindView(R.id.rv_special)
    RecyclerView rvSpecial;
    @BindView(R.id.rv_theme)
    RecyclerView rvTheme;

    public MenuFragment() {
    }


    @Override
    protected void initEventAndData() {
        initRecyclerView();
    }

    /**
     * 初始化recycler
     */
    private void initRecyclerView() {
        rvMap.setLayoutManager(new GridLayoutManager(getActivity(),3));
        MapMenuAdapter mapMenuAdapter = new MapMenuAdapter(getActivity());
        rvMap.setAdapter(mapMenuAdapter);
        rvSpecial.setLayoutManager(new GridLayoutManager(getActivity(),2));
        MenuSpecialAdapter menuSpecialAdapter = new MenuSpecialAdapter(getActivity());
        rvSpecial.setAdapter(menuSpecialAdapter);
        rvTheme.setLayoutManager(new LinearLayoutManager(getActivity()));
        MenuThemeAdapter menuThemeAdapter = new MenuThemeAdapter(getActivity());
        rvTheme.setAdapter(menuThemeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

}
