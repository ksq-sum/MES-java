package com.ktg.mes.pro.mapper;

import com.ktg.mes.pro.domain.ProWorkorder;

import java.util.List;

/**
 * 生产工单Mapper接口
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
public interface ProWorkorderMapper
{
    /**
     * 查询生产工单
     *
     * @param workorderId 生产工单主键
     * @return 生产工单
     */
    public ProWorkorder selectProWorkorderByWorkorderId(Long workorderId);

    /**
     * 查询生产工单
     *
     * @param workorderId 生产工单主键
     * @return 生产工单
     */
    public List<ProWorkorder> selectProWorkorderListByParentId(Long workorderId);

    /**
     * 查询生产工单列表
     *
     * @param proWorkorder 生产工单
     * @return 生产工单集合
     */
    public List<ProWorkorder> selectProWorkorderList(ProWorkorder proWorkorder);

    public ProWorkorder checkWorkorderCodeUnique(ProWorkorder proWorkorder);

    /**
     * 新增生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    public int insertProWorkorder(ProWorkorder proWorkorder);

    /**
     * 修改生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    public int updateProWorkorder(ProWorkorder proWorkorder);

    /**
     * 删除生产工单
     *
     * @param workorderId 生产工单主键
     * @return 结果
     */
    public int deleteProWorkorderByWorkorderId(Long workorderId);

    /**
     * 批量删除生产工单
     *
     * @param workorderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProWorkorderByWorkorderIds(Long[] workorderIds);
}
