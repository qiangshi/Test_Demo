package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批划拨信息
 * @Date: 2019/4/23
 */
public class ApprovalAllotInfo {

    //业务id
    private int bid;

    //划拨决定书
    private String serialNumber;

    //公司名称
    private	String corpname;

    //发布时间
    private String publishDate;

    //用途
    private String landUseType;

    public ApprovalAllotInfo() {

    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLandUseType() {
        return landUseType;
    }

    public void setLandUseType(String landUseType) {
        this.landUseType = landUseType;
    }

    @Override
    public String toString() {
        return "ApprovalAllotInfo{" +
                "bid=" + bid +
                ", serialNumber='" + serialNumber + '\'' +
                ", corpname='" + corpname + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", landUseType='" + landUseType + '\'' +
                '}';
    }

}
