package com.ktg.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "SALES_ORDERS_SKU")
public class SalesOrderSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    @Column(name = "id") // 指定列名
    private Integer id;// 序号

    @Column(name = "global_order_no", nullable = false) // 指定列名和非空约束
    private String globalOrderNo;     // mes销售订单编号

    @Column(name = "sku") // 指定列名和非空约束
    private String sku;               // sku

    @Column(name = "msku") // 指定列名
    private String msku;              // msuk

    @Column(name = "count") // 指定列名和非空约束
    private int count;                // 数量

    @Column(name = "product_image") // 指定列名
    private String productImage;      // 生产图

    @Column(name = "rendering_image") // 指定列名
    private String renderingImage;    // 演示图

    @Column(name = "local_product_name") // 指定列名
    private String localProductName;  // erp物料

    @Column(name = "product_plan_code") // 指定列名
    private String productPlanCode;   // 生产工单编号

//    @Column(name = "raw_material") // 指定列名
//    private String rawMaterial;       // 原材料

    @Column(name = "craft") // 指定列名
    private String craft;             // 工艺

    //@Column(name = "person") // 指定列名
   // private String person;            // 人员

    @Column(name = "if_finish") // 指定列名和非空约束
    private Integer ifFinish;             // 是否完成

    @Column(name = "create_time") // 指定列名
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 指定日期格式
    private String createTime; // 创建时间

    @Column(name = "create_p") // 指定列名
    private String createP;           // 创建人

    @Column(name = "update_time") // 指定列名
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 指定日期格式
    private String updateTime; // 更新时间

    @Column(name = "delete_flag") // 指定列名和非空约束
    private Integer deleteFlag;           // 是否删除

    // 默认构造器
    public SalesOrderSku() {}

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

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getLocalProductName() {
        return localProductName;
    }

    public void setLocalProductName(String localProductName) {
        this.localProductName = localProductName;
    }

    public String getProductPlanCode() {
        return productPlanCode;
    }

    public void setProductPlanCode(String productPlanCode) {
        this.productPlanCode = productPlanCode;
    }

//    public String getRawMaterial() {
//        return rawMaterial;
//    }
//
//    public void setRawMaterial(String rawMaterial) {
//        this.rawMaterial = rawMaterial;
//    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

//    public String getPerson() {
//        return person;
//    }
//
//    public void setPerson(String person) {
//        this.person = person;
//    }

    public Integer getIfFinish() {
        return ifFinish;
    }

    public void setIfFinish(Integer ifFinish) {
        this.ifFinish = ifFinish;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
