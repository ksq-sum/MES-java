package com.ktg.web.domain;


import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@ToString
@Entity
@Table(name = "MES_SALES_ORDERS")
public class SalesOrder {

    @Id
    @Column(name = "global_order_no")
    private String globalOrderNo; // erp主键

    @Column(name = "platform_order_name", nullable = false)
    private String platformOrderName; // erp店铺订单号

    @Column(name = "request_statu", nullable = false)
    private Integer requestStatus; // erp订单状态

    @Column(name = "remack")
    private String remark; // 订单备注

    @Column(name = "global_payment_time")
    private String globalPaymentTime;

    @Column(name = "plan_end_time")
    private String planEndTime; // 约定结束时间

    @Column(name = "real_end_time")
    private String realEndTime; // 实际结束时间

    @Column(name = "platform_statu")
    private Integer platformStatus; // 订单MES状态

    @Column(name = "if_out_time")
    private Integer ifOutTime; // 是否超期

    @Column(name = "create_time")
    private String createTime; // 创建时间

    @Column(name = "create_p")
    private String createPerson; // 创建人


    @Column(name = "update_time")
    private String updateTime; // 更新时间

    @Column(name = "delete_flag", columnDefinition = "int default 0")
    private Integer deleteFlag; // 是否删除



    public void setGlobalOrderNo(String globalOrderNo) {
        this.globalOrderNo = globalOrderNo;
    }


    // Getters and Setters
    public String getGlobalOrderNo() {
        return globalOrderNo;
    }

    public String getPlatformOrderName() {
        return platformOrderName;
    }

    public void setPlatformOrderName(String platformOrderName) {
        this.platformOrderName = platformOrderName;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Integer getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(Integer platformStatus) {
        this.platformStatus = platformStatus;
    }

    public Integer getIfOutTime() {
        return ifOutTime;
    }

    public void setIfOutTime(Integer ifOutTime) {
        this.ifOutTime = ifOutTime;
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

    public String getGlobalPaymentTime() {
        return globalPaymentTime;
    }

    public void setGlobalPaymentTime(String globalPaymentTime) {
        this.globalPaymentTime = globalPaymentTime;
    }
}


