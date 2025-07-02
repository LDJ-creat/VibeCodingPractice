package com.chuanzhi.health.model;

import java.util.Date;

public class Checkitem {
    private Integer cid;
    private String ccode;
    private String cname;
    private String referVal;
    private String unit;
    private Date createDate;
    private Date updDate;
    private Date deleteDate;
    private String optionUser;
    private String status;

    // Alias for id
    public Integer getId() {
        return cid;
    }

    // Alias for name
    public String getName() {
        return cname;
    }

    // Alias for code
    public String getCode() {
        return ccode;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getReferVal() {
        return referVal;
    }

    public void setReferVal(String referVal) {
        this.referVal = referVal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getOptionUser() {
        return optionUser;
    }

    public void setOptionUser(String optionUser) {
        this.optionUser = optionUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Checkitem{" +
                "cid=" + cid +
                ", ccode='" + ccode + '\'' +
                ", cname='" + cname + '\'' +
                ", referVal='" + referVal + '\'' +
                ", unit='" + unit + '\'' +
                ", createDate=" + createDate +
                ", updDate=" + updDate +
                ", deleteDate=" + deleteDate +
                ", optionUser='" + optionUser + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}