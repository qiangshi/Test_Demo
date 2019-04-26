package com.supermap.demo.test.map.bean;

/**
 * Company: Shanghai NanKang Technology Co., Ltd.<br>
 *
 * @author sun
 * @Description: 审批信息
 * @Date: 2019/4/22
 */
public class ApprovalInfo {

    //业务id
    private int bid;

    //数据分类代码
    private String category;

    //名称
    private String name;

    //农专用业务id
    private Integer coaBid;

    //供地业务id
    private Integer landsupplyBid;

    //储备业务id
    private Integer reserveBid;

    //合同业务id
    private Integer contractBid;

    //划拨业务id
    private Integer allotBid;

    //关联的规划业务id
    private Integer planBid;

    //关联的规划信息
    private PlanInfo planInfo;

    //关联的审批合同信息
    private ApprovalContractInfo contractInfo;

    //关联的审批划拨信息
    private ApprovalAllotInfo allotInfo;

    //是否有农转用审批信息
    private boolean hasCoa;

    //是否有供地审批信息
    private boolean hasLandsupply;

    //是否有储备审批信息
    private boolean hasReserve;

    //是否有合同审批信息
    private boolean hasContract;

    //是否有划拨审批信息
    private boolean hasAllot;

    public ApprovalInfo() {

    }

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

    public Integer getCoaBid() {
        return coaBid;
    }

    public void setCoaBid(Integer coaBid) {
        this.coaBid = coaBid;
    }

    public Integer getLandsupplyBid() {
        return landsupplyBid;
    }

    public void setLandsupplyBid(Integer landsupplyBid) {
        this.landsupplyBid = landsupplyBid;
    }

    public Integer getReserveBid() {
        return reserveBid;
    }

    public void setReserveBid(Integer reserveBid) {
        this.reserveBid = reserveBid;
    }

    public Integer getContractBid() {
        return contractBid;
    }

    public void setContractBid(Integer contractBid) {
        this.contractBid = contractBid;
    }

    public Integer getAllotBid() {
        return allotBid;
    }

    public void setAllotBid(Integer allotBid) {
        this.allotBid = allotBid;
    }

    public Integer getPlanBid() {
        return planBid;
    }

    public void setPlanBid(Integer planBid) {
        this.planBid = planBid;
    }

    public PlanInfo getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(PlanInfo planInfo) {
        this.planInfo = planInfo;
    }

    public ApprovalContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ApprovalContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

    public ApprovalAllotInfo getAllotInfo() {
        return allotInfo;
    }

    public void setAllotInfo(ApprovalAllotInfo allotInfo) {
        this.allotInfo = allotInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isHasCoa() {
        return hasCoa;
    }

    public void setHasCoa(boolean hasCoa) {
        this.hasCoa = hasCoa;
    }

    public boolean isHasLandsupply() {
        return hasLandsupply;
    }

    public void setHasLandsupply(boolean hasLandsupply) {
        this.hasLandsupply = hasLandsupply;
    }

    public boolean isHasReserve() {
        return hasReserve;
    }

    public void setHasReserve(boolean hasReserve) {
        this.hasReserve = hasReserve;
    }

    public boolean isHasContract() {
        return hasContract;
    }

    public void setHasContract(boolean hasContract) {
        this.hasContract = hasContract;
    }

    public boolean isHasAllot() {
        return hasAllot;
    }

    public void setHasAllot(boolean hasAllot) {
        this.hasAllot = hasAllot;
    }

    @Override
    public String toString() {
        return "ApprovalInfo{" +
                "bid=" + bid +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", coaBid=" + coaBid +
                ", landsupplyBid=" + landsupplyBid +
                ", reserveBid=" + reserveBid +
                ", contractBid=" + contractBid +
                ", allotBid=" + allotBid +
                ", planBid=" + planBid +
                ", planInfo=" + planInfo +
                ", contractInfo=" + contractInfo +
                ", allotInfo=" + allotInfo +
                ", hasCoa=" + hasCoa +
                ", hasLandsupply=" + hasLandsupply +
                ", hasReserve=" + hasReserve +
                ", hasContract=" + hasContract +
                ", hasAllot=" + hasAllot +
                '}';
    }
}
