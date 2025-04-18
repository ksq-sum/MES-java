package com.ktg.mes.dv.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ktg.common.annotation.Excel;
import com.ktg.common.core.domain.BaseEntity;

/**
 * 设备保养记录行对象 dv_mainten_record_line
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public class DvMaintenRecordLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    private Long lineId;

    /** 计划ID */
    @Excel(name = "计划ID")
    private Long recordId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long subjectId;

    /** 项目编码 */
    @Excel(name = "项目编码")
    private String subjectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String subjectName;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String subjectType;

    /** 项目内容 */
    @Excel(name = "项目内容")
    private String subjectContent;

    /** 标准 */
    @Excel(name = "标准")
    private String subjectStandard;

    /** 保养结果 */
    @Excel(name = "保养结果")
    private String maintenStatus;

    /** 异常描述 */
    @Excel(name = "异常描述")
    private String maintenResult;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }
    public void setSubjectCode(String subjectCode) 
    {
        this.subjectCode = subjectCode;
    }

    public String getSubjectCode() 
    {
        return subjectCode;
    }
    public void setSubjectName(String subjectName) 
    {
        this.subjectName = subjectName;
    }

    public String getSubjectName() 
    {
        return subjectName;
    }
    public void setSubjectType(String subjectType) 
    {
        this.subjectType = subjectType;
    }

    public String getSubjectType() 
    {
        return subjectType;
    }
    public void setSubjectContent(String subjectContent) 
    {
        this.subjectContent = subjectContent;
    }

    public String getSubjectContent() 
    {
        return subjectContent;
    }
    public void setSubjectStandard(String subjectStandard) 
    {
        this.subjectStandard = subjectStandard;
    }

    public String getSubjectStandard() 
    {
        return subjectStandard;
    }
    public void setMaintenStatus(String maintenStatus) 
    {
        this.maintenStatus = maintenStatus;
    }

    public String getMaintenStatus() 
    {
        return maintenStatus;
    }
    public void setMaintenResult(String maintenResult) 
    {
        this.maintenResult = maintenResult;
    }

    public String getMaintenResult() 
    {
        return maintenResult;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("lineId", getLineId())
            .append("recordId", getRecordId())
            .append("subjectId", getSubjectId())
            .append("subjectCode", getSubjectCode())
            .append("subjectName", getSubjectName())
            .append("subjectType", getSubjectType())
            .append("subjectContent", getSubjectContent())
            .append("subjectStandard", getSubjectStandard())
            .append("maintenStatus", getMaintenStatus())
            .append("maintenResult", getMaintenResult())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
