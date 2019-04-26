package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 规划信息
 * @Date: 2019/4/22
 */
public class PlanInfo {

    //业务id
    private int bid;

    //地块编号
    private String name;

    //利用情况
    private String state;

    //规划性质
    private String plotName;

    //地块面积
    private Double dkarea;

    //容积率
    private Double volRatio;

    //限高
    private Double bldgHlmt;

    //审批业务id
    private Integer approvalBid;

    //关联的审批信息
    private ApprovalInfo approvalInfo;

    public PlanInfo() {

    };

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public Double getDkarea() {
        return dkarea;
    }

    public void setDkarea(Double dkarea) {
        this.dkarea = dkarea;
    }

    public Double getVolRatio() {
        return volRatio;
    }

    public void setVolRatio(Double volRatio) {
        this.volRatio = volRatio;
    }

    public Double getBldgHlmt() {
        return bldgHlmt;
    }

    public void setBldgHlmt(Double bldgHlmt) {
        this.bldgHlmt = bldgHlmt;
    }

    public Integer getApprovalBid() {
        return approvalBid;
    }

    public void setApprovalBid(Integer approvalBid) {
        this.approvalBid = approvalBid;
    }

    public ApprovalInfo getApprovalInfo() {
        return approvalInfo;
    }

    public void setApprovalInfo(ApprovalInfo approvalInfo) {
        this.approvalInfo = approvalInfo;
    }

    @Override
    public String toString() {
        return "PlanInfo{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", plotName='" + plotName + '\'' +
                ", dkarea=" + dkarea +
                ", volRatio=" + volRatio +
                ", bldgHlmt=" + bldgHlmt +
                ", approvalBid=" + approvalBid +
                ", approvalInfo=" + approvalInfo +
                '}';
    }

}
