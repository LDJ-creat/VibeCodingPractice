package com.chuanzhi.health.model;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 */
public class Checkgroup implements Serializable {
    private Integer gid; // 主键
    private String code; // 编码
    private String name; // 名称
    private String helpCode; // 助记
    private String sex; // 适用性别
    private String remark; // 备注
    private String attention; // 注意事项
    private List<Integer> checkitemIds; // 关联的检查项ID列表

    // Alias for id
    public Integer getId() {
        return gid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<Integer> getCheckitemIds() {
        return checkitemIds;
    }

    public void setCheckitemIds(List<Integer> checkitemIds) {
        this.checkitemIds = checkitemIds;
    }
}