package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批供地信息
 * @Date: 2019/4/22
 */
public class ApprovalLandsupplyInfo {

    //业务id
    private int bid;

    //批文号
    private String approvalNum;

    //供应模式
    private String supplyMode;

    //面积
    private Double totalArea;

    //项目名称
    private String prjName;

    public ApprovalLandsupplyInfo() {

    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getApprovalNum() {
        return approvalNum;
    }

    public void setApprovalNum(String approvalNum) {
        this.approvalNum = approvalNum;
    }

    public String getSupplyMode() {
        return supplyMode;
    }

    public void setSupplyMode(String supplyMode) {
        this.supplyMode = supplyMode;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    @Override
    public String toString() {
        return "ApprovalLandsupplyInfo{" +
                "bid=" + bid +
                ", approvalNum='" + approvalNum + '\'' +
                ", supplyMode='" + supplyMode + '\'' +
                ", totalArea=" + totalArea +
                ", prjName='" + prjName + '\'' +
                '}';
    }

}
