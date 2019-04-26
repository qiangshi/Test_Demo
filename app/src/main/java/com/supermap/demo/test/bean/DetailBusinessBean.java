package com.supermap.demo.test.bean;

/**
 * @ClassName: DetailBusinessBean
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/14 17:12
 */
public class DetailBusinessBean {
    private String month;
    private String year;
    private String license;
    private String landUse;
    private String approvals;

    public DetailBusinessBean(String month, String year, String license, String landUse, String approvals) {
        this.month = month;
        this.year = year;
        this.license = license;
        this.landUse = landUse;
        this.approvals = approvals;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLandUse() {
        return landUse;
    }

    public void setLandUse(String landUse) {
        this.landUse = landUse;
    }

    public String getApprovals() {
        return approvals;
    }

    public void setApprovals(String approvals) {
        this.approvals = approvals;
    }
}
