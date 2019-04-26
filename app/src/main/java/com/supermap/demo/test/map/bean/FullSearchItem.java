package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 全库查询结果项
 * @Date: 2019/4/14
 */
public class FullSearchItem {

    //数据id
    private int id;

    //名称
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

    //年份
    private String year;

    public FullSearchItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public boolean isPoiItem() {
        return isPoiItem;
    }

    public void setPoiItem(boolean poiItem) {
        isPoiItem = poiItem;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "FullSearchItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                ", mix='" + mix + '\'' +
                ", bid=" + bid +
                ", isPoiItem=" + isPoiItem +
                ", label='" + label + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

}
