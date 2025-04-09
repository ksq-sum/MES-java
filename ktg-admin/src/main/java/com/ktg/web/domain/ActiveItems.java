package com.ktg.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVE_ITEMS")
public class ActiveItems {
    @Id
    @Column(name = "sku")
    private String sku; // erp主键

    @Column(name="msku")
    private String msku;

    @Column(name="local_name")
    private String localName;

    @Column(name="craft")
    private String craft;

    @Column(name="depart")
    private String depart;

    @Column(name = "create_time")
    private String createTime; // 创建时间

    @Column(name = "create_p")
    private String createPerson; // 创建人


    @Column(name = "update_time")
    private String updateTime; // 更新时间

    @Column(name = "delete_flag", columnDefinition = "int default 0")
    private Integer deleteFlag; // 是否删除



    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
