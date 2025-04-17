package com.ktg.web.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Table(name = "MES_CRAFT")
public class MESCRAFT {
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    @Column(name = "id")
    private Integer id; // 序号

    @Id
    @Column(name = "craft", length = 50, unique = true)
    private String craft; // 产线 (主键)

    @Column(name = "create_time", length = 50)
    private String createTime; // 创建时间

    @Column(name = "create_p", length = 50 , columnDefinition = "VARCHAR(50) DEFAULT 'system'")
    private String createPerson; // 创建人

    @Column(name = "update_time", length = 50)
    private String updateTime; // 更新时间

    @Column(name = "delete_flag" , columnDefinition = "int default 0")
    private Integer deleteFlag; // 是否删除

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
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