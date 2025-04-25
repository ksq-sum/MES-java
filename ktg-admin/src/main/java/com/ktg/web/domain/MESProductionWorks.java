package com.ktg.web.domain;

import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@ToString
@Entity
@Table(name = "MES_PRODUCTION_WORKS")
public class MESProductionWorks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "work_plan_code", length = 50)
    private String workPlanCode;

    @Column(name = "product_plan_code", nullable = false, length = 100)
    private String productPlanCode;

    @Column(name = "global_order_no", length = 50)
    private String globalOrderNo;

    @Column(name = "sku", length = 50)
    private String sku;

    @Column(name = "msku", length = 50)
    private String msku;

    @Column(name = "local_name", length = 300)
    private String localName;

    @Column(name = "craft", length = 50)
    private String craft;

    @Column(name = "depart", length = 50)
    private String depart;

    @Column(name = "product_image", length = 500)
    private String productImage;

    @Column(name = "rendering_image", length = 500)
    private String renderingImage;

    @Column(name = "count")
    private Integer count;

    @Column(name = "order_pay_time", length = 50)
    private String orderPayTime;

    @Column(name = "plan_end_time", length = 50)
    private String planEndTime;

    @Column(name = "real_end_time", length = 50)
    private String realEndTime;

    @Column(name = "platform_statu")
    private Integer platformStatu;

    @Column(name = "if_out_time")
    private Integer ifOutTime;

    @Column(name = "create_time", length = 50)
    private String createTime;

    @Column(name = "create_p", length = 50)
    private String createP = "system"; // 默认值为 'system'

    @Column(name = "update_time", length = 50)
    private String updateTime;

    @Column(name = "delete_flag", columnDefinition = "int default 0")
    private Integer deleteFlag = 0; // 默认值为 0

    @Column(name = "remack", nullable = false)
    private String remack;
    public String getRemack() {
        return remack;
    }

    public void setRemack(String remack) {
        this.remack = remack;
    }

    // Getters 和 Setters
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

    public String getProductPlanCode() {
        return productPlanCode;
    }

    public void setProductPlanCode(String productPlanCode) {
        this.productPlanCode = productPlanCode;
    }

    public String getGlobalOrderNo() {
        return globalOrderNo;
    }

    public void setGlobalOrderNo(String globalOrderNo) {
        this.globalOrderNo = globalOrderNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getRenderingImage() {
        return renderingImage;
    }

    public void setRenderingImage(String renderingImage) {
        this.renderingImage = renderingImage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
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

    public Integer getPlatformStatu() {
        return platformStatu;
    }

    public void setPlatformStatu(Integer platformStatu) {
        this.platformStatu = platformStatu;
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

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }
}

