package com.ktg.web.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Table(name = "MES_PROCESS")  // 假设表名为MES_PROCESS
public class MESPROCESS {
    @Id
    @Column(name = "process", length = 50, nullable = false, unique = true)
    private String process;  // 工序(主键)

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Integer id;  // 序号

    @Column(name = "create_time", length = 50)
    private String createTime;  // 创建时间(yyyy-MM-dd hhmmss)

    @Column(name = "create_p", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'system'")
    private String createPerson = "system";  // 创建人(默认值system)

    @Column(name = "update_time", length = 50)
    private String updateTime;  // 更新时间(yyyy-MM-dd hhmmss)

    @Column(name = "delete_flag", columnDefinition = "INT DEFAULT 0")
    private Integer deleteFlag = 0;  // 是否删除(0正常1逻辑删除)

    // Getter和Setter方法
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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