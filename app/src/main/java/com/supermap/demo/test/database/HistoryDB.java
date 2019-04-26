package com.supermap.demo.test.database;

import com.supermap.demo.test.map.bean.FullSearchItem;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/11 15:03
 */
@Entity
public class HistoryDB implements Serializable {

    private static final long serialVersionUID = 9039014229944717656L;
    @Id(autoincrement = true)
    private Long id;

    private String userId;
    //搜索时间
    private long searchTime;
    //0: 正常  1: OPI  2: 业务类型
    private int historyType;
    //数据ID
    private int dataId;

    //搜索名称
    private String name;

    //数据类别。用于区别数据是银行、学校、规划、审批等
    private String category;

    //x坐标
    private double coordX;

    //y坐标
    private double coordY;

    //数据综合信息
    private String mix;

    //业务id，用于关联数据的详细信息
    private int bid;

    //是否poi数据项
    private boolean isPoiItem;

    //标签
    private String label;

    //普通类型的构造函数
    public HistoryDB(String userId, long searchTime, int historyType, String name) {
        this.userId = userId;
        this.searchTime = searchTime;
        this.historyType = historyType;
        this.name = name;
    }

    //POI类型的构造函数 或业务类型构造函数
    public HistoryDB(String userId, long searchTime, int historyType, FullSearchItem fullSearchItem) {
        this.userId = userId;
        this.searchTime = searchTime;
        this.historyType = historyType;
        this.dataId = fullSearchItem.getId();
        this.name = fullSearchItem.getName();
        this.category = fullSearchItem.getCategory();
        this.coordX = fullSearchItem.getCoordX();
        this.coordY = fullSearchItem.getCoordY();
        this.mix = fullSearchItem.getMix();
        this.bid = fullSearchItem.getBid();
        this.isPoiItem = fullSearchItem.isPoiItem();
        this.label = fullSearchItem.getLabel();
    }

    @Generated(hash = 33916492)
    public HistoryDB(Long id, String userId, long searchTime, int historyType, int dataId, String name,
            String category, double coordX, double coordY, String mix, int bid, boolean isPoiItem,
            String label) {
        this.id = id;
        this.userId = userId;
        this.searchTime = searchTime;
        this.historyType = historyType;
        this.dataId = dataId;
        this.name = name;
        this.category = category;
        this.coordX = coordX;
        this.coordY = coordY;
        this.mix = mix;
        this.bid = bid;
        this.isPoiItem = isPoiItem;
        this.label = label;
    }

    @Generated(hash = 1964680625)
    public HistoryDB() {
    }



    @Override
    public String toString() {
        return "HistoryDB{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", searchTime=" + searchTime +
                ", historyType=" + historyType +
                ", dataId='" + dataId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                ", mix='" + mix + '\'' +
                ", bid=" + bid +
                ", isPoiItem=" + isPoiItem +
                ", label='" + label + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSearchTime() {
        return this.searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public int getHistoryType() {
        return this.historyType;
    }

    public void setHistoryType(int historyType) {
        this.historyType = historyType;
    }

    public int getDataId() {
        return this.dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCoordX() {
        return this.coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return this.coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public String getMix() {
        return this.mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public int getBid() {
        return this.bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public boolean getIsPoiItem() {
        return this.isPoiItem;
    }

    public void setIsPoiItem(boolean isPoiItem) {
        this.isPoiItem = isPoiItem;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
