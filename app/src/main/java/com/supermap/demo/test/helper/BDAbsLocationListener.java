package com.supermap.demo.test.helper;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**
 * Created by zenghaiqiang on 2018/5/28.
 * 描述：百度地圖定位監聽
 */

public class BDAbsLocationListener extends BDAbstractLocationListener {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        double latitude = bdLocation.getLatitude();    //获取纬度信息
        double longitude = bdLocation.getLongitude();    //获取经度信息
        float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
        String coorType = bdLocation.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        int errorCode = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        String addr = bdLocation.getAddrStr();    //获取详细地址信息
        String country = bdLocation.getCountry();    //获取国家
        String province = bdLocation.getProvince();    //获取省份
        String city = bdLocation.getCity();    //获取城市
        String district = bdLocation.getDistrict();    //获取区县
        String street = bdLocation.getStreet();    //获取街道信息
     }
}
