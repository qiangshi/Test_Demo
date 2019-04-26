package com.supermap.demo.test.helper;

import android.os.Bundle;

import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.ui.activity.MainActivity;
import com.supermap.demo.test.ui.fragment.tool.BaseToolFragment;
import com.supermap.demo.test.ui.fragment.tool.BusinessFragment;
import com.supermap.demo.test.ui.fragment.tool.LocationFragment;
import com.supermap.demo.test.ui.fragment.tool.PeripheryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MapHelper
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 10:32
 */
public class MapHelper {

    private BaseToolFragment currentFragment;
    private static MainActivity context;

    public static MapHelper getInstance(MainActivity activity) {
        context = activity;
        return Holder.instance;
    }

    private static class Holder {
        private static final MapHelper instance = new MapHelper();
    }

    public List<BaseToolFragment> baseFragments = new ArrayList<>();


    public void addFragment(BaseToolFragment fragment) {
        if (fragment != null) {
            baseFragments.add(fragment);
        }
    }

    public void removeFragment() {
        if (baseFragments != null && baseFragments.size() > 0) {
            baseFragments.remove(baseFragments.size() - 1);
        }
    }

    /**
     * 获取当前最外层的fragment
     */
    public BaseToolFragment getCurrentFragment() {
        currentFragment = baseFragments.get(baseFragments.size() - 1);
        return currentFragment;
    }


    /**
     * 显示Fragment
     */
    public void showFragment(PeripheryFragment fragment) {
        context.getSupportFragmentManager().beginTransaction().add(fragment, "peripheryFragment").commitAllowingStateLoss();
    }

    /**
     * 隐藏Fragment
     */
    public void hideFragment(PeripheryFragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            fragment.dismissAllowingStateLoss();
            fragment = null;
        }
    }


    private LocationFragment locationFragment;

    /**
     * 显示Fragment  POI详情
     */
    public void showLocationFragment(int contentId, String location, String distance, LocationFragment.OnItemClickListener listener) {
        if (locationFragment != null) {
            locationFragment.dismissAllowingStateLoss();
            locationFragment = null;
        }
        locationFragment = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DIALOG_LOCATION, location);
        bundle.putString(Constant.DIALOG_DISTANCE, distance);
        locationFragment.setArguments(bundle);
        locationFragment.setOnClickListener(listener);
        context.getSupportFragmentManager().beginTransaction().add(contentId, locationFragment).commitAllowingStateLoss();
    }

    /**
     * 关闭POI详情
     */
    public void hideLocationFragment() {
        if (locationFragment != null && locationFragment.isAdded())
            context.getSupportFragmentManager().beginTransaction().hide(locationFragment).commitAllowingStateLoss();
    }

    private BusinessFragment businessFragment;
    /**
     * 显示Fragment  业务详情
     */
    public void showBusinessFragment(int contentId, String label, int bid, BusinessFragment.OnItemClickListener listener) {
        if (businessFragment != null) {
            businessFragment.dismissAllowingStateLoss();
            businessFragment = null;
        }
        businessFragment = new BusinessFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DIALOG_LABEL, label);
        bundle.putInt(Constant.DIALOG_BID, bid);
        businessFragment.setArguments(bundle);
        businessFragment.setOnClickListener(listener);
        context.getSupportFragmentManager().beginTransaction().add(contentId, businessFragment).commitAllowingStateLoss();
    }

    /**
     * 关闭POI详情
     */
    public void hideBusinessFragment() {
        if (businessFragment != null && businessFragment.isAdded())
            context.getSupportFragmentManager().beginTransaction().hide(businessFragment).commitAllowingStateLoss();
    }

}
