package com.ktg.mes.dv.mapper;

import java.util.List;
import com.ktg.mes.dv.domain.DvMaintenRecordLine;

/**
 * 设备保养记录行Mapper接口
 * 
 * @author yinjinlu
 * @date 2024-12-26
 */
public interface DvMaintenRecordLineMapper 
{
    /**
     * 查询设备保养记录行
     * 
     * @param lineId 设备保养记录行主键
     * @return 设备保养记录行
     */
    public DvMaintenRecordLine selectDvMaintenRecordLineByLineId(Long lineId);

    /**
     * 查询设备保养记录行列表
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 设备保养记录行集合
     */
    public List<DvMaintenRecordLine> selectDvMaintenRecordLineList(DvMaintenRecordLine dvMaintenRecordLine);

    /**
     * 新增设备保养记录行
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 结果
     */
    public int insertDvMaintenRecordLine(DvMaintenRecordLine dvMaintenRecordLine);

    /**
     * 修改设备保养记录行
     * 
     * @param dvMaintenRecordLine 设备保养记录行
     * @return 结果
     */
    public int updateDvMaintenRecordLine(DvMaintenRecordLine dvMaintenRecordLine);

    /**
     * 删除设备保养记录行
     * 
     * @param lineId 设备保养记录行主键
     * @return 结果
     */
    public int deleteDvMaintenRecordLineByLineId(Long lineId);

    /**
     * 批量删除设备保养记录行
     * 
     * @param lineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDvMaintenRecordLineByLineIds(Long[] lineIds);
}
