package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批农转用信息
 * @Date: 2019/4/22
 */
public class ApprovalCoaInfo {

    //业务id
    private int bid;

    //批文号
    private String approvalNum;

    //项目个数
    private Integer prjNum;

    //批准机关
    private String approvePrj;

    //面积
    private Double totalArea;

    public ApprovalCoaInfo() {

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

    public Integer getPrjNum() {
        return prjNum;
    }

    public void setPrjNum(Integer prjNum) {
        this.prjNum = prjNum;
    }

    public String getApprovePrj() {
        return approvePrj;
    }

    public void setApprovePrj(String approvePrj) {
        this.approvePrj = approvePrj;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "ApprovalCoaInfo{" +
                "bid=" + bid +
                ", approvalNum='" + approvalNum + '\'' +
                ", prjNum=" + prjNum +
                ", approvePrj='" + approvePrj + '\'' +
                ", totalArea=" + totalArea +
                '}';
    }
}
