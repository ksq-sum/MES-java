package com.ktg.web.domain;
import lombok.ToString;

import javax.persistence.*;
@ToString
@Entity
@Table(name = "MES_PRODUCTION_ORDERS")
public class MESProductionOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; // 序号

    @Column(name = "work_plan_code", nullable = false, unique = true, length = 50)
    private String workPlanCode; // 生产计划编号

    @Column(name = "plan_statu", nullable = false)
    private Integer planStatu; // 计划状态（0: 未开始；1: 进行中；2: 已完成）

    @Column(name = "create_time", nullable = false, length = 50)
    private String createTime; // 创建时间

    @Column(name = "create_p", length = 50)
    private String createP = "system"; // 创建人，默认值为 'system'

    @Column(name = "update_time", length = 50)
    private String updateTime; // 更新时间

    @Column(name = "delete_flag", columnDefinition = "INT DEFAULT 0")
    private Integer deleteFlag = 0; // 是否删除（0: 正常；1: 逻辑删除）


    @Column(name = "remack", nullable = false)
    private String remack;
    public String getRemack() {
        return remack;
    }

    public void setRemack(String remack) {
        this.remack = remack;
    }



    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkPlanCode() {
        return workPlanCode;
    }

    public void setWorkPlanCode(String workPlanCode) {
        this.workPlanCode = workPlanCode;
    }

    public Integer getPlanStatu() {
        return planStatu;
    }

    public void setPlanStatu(Integer planStatu) {
        this.planStatu = planStatu;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateP() {
        return createP;
    }

    public void setCreateP(String createP) {
        this.createP = createP;
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