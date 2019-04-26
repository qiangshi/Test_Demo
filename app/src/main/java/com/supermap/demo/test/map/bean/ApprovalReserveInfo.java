package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批储备信息
 * @Date: 2019/4/23
 */
public class ApprovalReserveInfo {

    //业务id
    private int bid;

    //批文号
    private String approvalNum;

    //用途
    private String planUse;

    //项目名称
    private String prjName;

    //面积
    private Double totalArea;

    public ApprovalReserveInfo() {

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

    public String getPlanUse() {
        return planUse;
    }

    public void setPlanUse(String planUse) {
        this.planUse = planUse;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "ApprovalReserveInfo{" +
                "bid=" + bid +
                ", approvalNum='" + approvalNum + '\'' +
                ", planUse='" + planUse + '\'' +
                ", prjName='" + prjName + '\'' +
                ", totalArea=" + totalArea +
                '}';
    }

}
