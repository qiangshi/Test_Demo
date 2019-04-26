package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批合同信息
 * @Date: 2019/4/23
 */
public class ApprovalContractInfo {

    //业务id
    private int bid;

    //批文号
    private String auditedNum;

    //合同日期
    private String contractDate;

    //合同费用
    private	Integer contractFee;

    //面积
    private Double totalArea;

    public ApprovalContractInfo() {

    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getAuditedNum() {
        return auditedNum;
    }

    public void setAuditedNum(String auditedNum) {
        this.auditedNum = auditedNum;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getContractFee() {
        return contractFee;
    }

    public void setContractFee(Integer contractFee) {
        this.contractFee = contractFee;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "ApprovalContractInfo{" +
                "bid=" + bid +
                ", auditedNum='" + auditedNum + '\'' +
                ", contractDate='" + contractDate + '\'' +
                ", contractFee=" + contractFee +
                ", totalArea=" + totalArea +
                '}';
    }

}
