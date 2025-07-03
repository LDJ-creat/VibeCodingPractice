package com.chuanzhi.health.model;

import java.util.Date;

/**
 * 体检结果模型
 */
public class ExamResult {
    private Integer id;
    private Integer appointmentId;
    private Integer checkitemId;
    private String resultValue; // 检查结果值
    private String resultUnit; // 结果单位
    private Date examDate; // 检查日期

    // 以下字段用于在视图中展示，不直接映射到数据库
    private String checkitemName;
    private String examValue; // 检查结果值，可能与resultValue重复，但为了清晰区分UI展示和原始数据
    private String referenceValue; // 参考值
    private Integer isAbnormal; // 是否异常，1表示异常，0表示正常
    private String  resultDesc; // 结果描述

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getCheckitemId() {
        return checkitemId;
    }

    public void setCheckitemId(Integer checkitemId) {
        this.checkitemId = checkitemId;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }


    public String getCheckitemName() {
        return checkitemName;
    }

    public void setCheckitemName(String checkitemName) {
        this.checkitemName = checkitemName;
    }

    public String getExamValue() {
        return examValue;
    }

    public void setExamValue(String examValue) {
        this.examValue = examValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
    public String getResultDesc() {
        return resultDesc;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", appointmentId=" + appointmentId +
                ", checkitemId=" + checkitemId +
                ", resultValue='" + resultValue + '\'' +
                ", resultUnit='" + resultUnit + '\'' +
                ", examDate=" + examDate +
                ", checkitemName='" + checkitemName + '\'' +
                ", examValue='" + examValue + '\'' +
                ", referenceValue='" + referenceValue + '\'' +
                ", isAbnormal=" + isAbnormal + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}