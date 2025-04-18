package com.ktg.mes.pro.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.ktg.common.constant.UserConstants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.utils.DateUtils;
import com.ktg.common.utils.StringUtils;
import com.ktg.mes.md.domain.MdWorkstation;
import com.ktg.mes.md.mapper.MdWorkstationMapper;
import com.ktg.mes.pro.domain.*;
import com.ktg.mes.pro.mapper.ProRouteMapper;
import com.ktg.mes.pro.mapper.ProRouteProductMapper;
import com.ktg.mes.pro.mapper.ProWorkorderMapper;
import com.ktg.mes.pro.service.IProProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.pro.mapper.ProRouteProcessMapper;
import com.ktg.mes.pro.service.IProRouteProcessService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工艺组成Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-13
 */
@Service
public class ProRouteProcessServiceImpl implements IProRouteProcessService 
{
    @Autowired
    private ProRouteMapper proRouteMapper;

    @Autowired
    private ProRouteProcessMapper proRouteProcessMapper;


    @Autowired
    private ProRouteProductMapper proRouteProductMapper;

    @Autowired
    private MdWorkstationMapper mdWorkstationMapper;

    @Autowired
    private IProProcessService proProcessService;

    /**
     * 查询工艺组成
     * 
     * @param recordId 工艺组成主键
     * @return 工艺组成
     */
    @Override
    public ProRouteProcess selectProRouteProcessByRecordId(Long recordId)
    {
        return proRouteProcessMapper.selectProRouteProcessByRecordId(recordId);
    }

    /**
     * 查询工艺组成列表
     * 
     * @param proRouteProcess 工艺组成
     * @return 工艺组成
     */
    @Override
    public List<ProRouteProcess> selectProRouteProcessList(ProRouteProcess proRouteProcess)
    {
        return proRouteProcessMapper.selectProRouteProcessList(proRouteProcess);
    }

    @Override
    public String checkOrderNumExists(ProRouteProcess proRouteProcess) {
        ProRouteProcess process = proRouteProcessMapper.checkOrderNumExists(proRouteProcess);
        Long recordId = proRouteProcess.getRecordId()==null?-1L:proRouteProcess.getRecordId();
        if(StringUtils.isNotNull(process) && process.getRecordId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkProcessExists(ProRouteProcess proRouteProcess) {
        ProRouteProcess process = proRouteProcessMapper.checkProcessExists(proRouteProcess);
        Long recordId = proRouteProcess.getRecordId()==null?-1L:proRouteProcess.getRecordId();
        if(StringUtils.isNotNull(process) && process.getRecordId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkUpdateFlagUnique(ProRouteProcess proRouteProcess) {
        ProRouteProcess process = proRouteProcessMapper.checkUpdateFlagUnique(proRouteProcess);
        Long recordId = proRouteProcess.getRecordId()==null?-1L:proRouteProcess.getRecordId();
        if(StringUtils.isNotNull(process) && process.getRecordId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkKeyProcess(ProFeedback feedback) {
        //根据当前生产的产品获取对应的工艺路线
        Long routeId =-1L,processId = -1L;
        ProRouteProduct param = new ProRouteProduct();
        param.setItemId(feedback.getItemId());
        List<ProRouteProduct> products = proRouteProductMapper.selectProRouteProductList(param);
        if(CollectionUtil.isNotEmpty(products)){
            products = products.stream().filter(item -> proRouteMapper.selectProRouteByRouteId(item.getRouteId()).getEnableFlag().equals(UserConstants.YES)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(products)){
                routeId = products.get(0).getRouteId();
            }
        }

        //根据工作站获取工序
        MdWorkstation workstation = mdWorkstationMapper.selectMdWorkstationByWorkstationId(feedback.getWorkstationId());
        processId = workstation.getProcessId();

        //再判断当前的工序在此工艺路线中是否是关键工序
        ProRouteProcess param2 = new ProRouteProcess();
        param2.setRouteId(routeId);
        param2.setProcessId(processId);
        param2.setKeyFlag(UserConstants.YES);
        List<ProRouteProcess> processes = proRouteProcessMapper.selectProRouteProcessList(param2);
        if(CollectionUtil.isNotEmpty(processes)){
            return true;
        }
        return false;
    }

    @Override
    public ProRouteProcess findPreProcess(ProRouteProcess proRouteProcess) {
        return proRouteProcessMapper.findPreProcess(proRouteProcess);
    }

    @Override
    public ProRouteProcess findNextProcess(ProRouteProcess proRouteProcess) {
        return proRouteProcessMapper.findNextProcess(proRouteProcess);
    }

    /**
     * 新增工艺组成
     * 
     * @param proRouteProcess 工艺组成
     * @return 结果
     */
    @Override
    public int insertProRouteProcess(ProRouteProcess proRouteProcess)
    {
        proRouteProcess.setCreateTime(DateUtils.getNowDate());
        return proRouteProcessMapper.insertProRouteProcess(proRouteProcess);
    }

    /**
     * 修改工艺组成
     * 
     * @param proRouteProcess 工艺组成
     * @return 结果
     */
    @Override
    public int updateProRouteProcess(ProRouteProcess proRouteProcess)
    {
        proRouteProcess.setUpdateTime(DateUtils.getNowDate());
        return proRouteProcessMapper.updateProRouteProcess(proRouteProcess);
    }

    /**
     * 批量删除工艺组成
     * 
     * @param recordIds 需要删除的工艺组成主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteProRouteProcessByRecordIds(Long[] recordIds)
    {
        List<ProRouteProcess> ProRouteProcessList = proRouteProcessMapper.selectByRecordId(recordIds[0]);
        Long routeId = ProRouteProcessList.get(0).getRouteId();
        int status = proRouteProcessMapper.deleteProRouteProcessByRecordIds(recordIds);
        List<ProRouteProcess> list = proRouteProcessMapper.selectByRouteId(routeId);
        if (list != null && list.size() > 0) {
            List<ProRouteProcess> collect = list.stream().sorted(Comparator.comparing(ProRouteProcess::getOrderNum))
                    .collect(Collectors.toList());
            // 重新生成工序
            for (int i = 0; i < collect.size(); i++) {
                ProRouteProcess item = collect.get(i);
                if ((i + 1) >= collect.size()) {
                    item.setNextProcessId(0L);
                    item.setNextProcessName("无");
                } else {
                    ProRouteProcess next = collect.get(i + 1);
                    item.setNextProcessId(next.getProcessId());
                    item.setNextProcessCode(next.getProcessCode());
                    item.setNextProcessName(next.getProcessName());
                }
                proRouteProcessMapper.updateProRouteProcess(item);
            }
        }
        return status;
    }

    /**
     * 删除工艺组成信息
     * 
     * @param recordId 工艺组成主键
     * @return 结果
     */
    @Override
    public int deleteProRouteProcessByRecordId(Long recordId)
    {
        return proRouteProcessMapper.deleteProRouteProcessByRecordId(recordId);
    }

    @Override
    public int deleteByRouteId(Long routeId) {
        return proRouteProcessMapper.deleteByRouteId(routeId);
    }

    @Override
    public List<ProRouteProcess> selectByProcessIds(Long[] processIds) {
        return proRouteProcessMapper.selectByProcessIds(processIds);
    }

    @Override
    @Transactional
    public AjaxResult editProRouteProcess(ProRouteProcess proRouteProcess) {
        if(UserConstants.NOT_UNIQUE.equals(checkOrderNumExists(proRouteProcess))){
            return AjaxResult.error("序号已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(checkProcessExists(proRouteProcess))){
            return AjaxResult.error("不能重复添加工序！");
        }
        if(UserConstants.YES.equals(proRouteProcess.getKeyFlag()) && UserConstants.NOT_UNIQUE.equals(checkUpdateFlagUnique(proRouteProcess))){
            return AjaxResult.error("当前工艺路线已经指定过关键工序");
        }
        ProProcess process = proProcessService.selectProProcessByProcessId(proRouteProcess.getProcessId());
        proRouteProcess.setProcessCode(process.getProcessCode());
        proRouteProcess.setProcessName(process.getProcessName());

        // 先更新当前数据
        int status = updateProRouteProcess(proRouteProcess);
        // 查询所有工艺路线数据
        List<ProRouteProcess> list = proRouteProcessMapper.selectByRouteId(proRouteProcess.getRouteId());
        if (list != null && list.size() > 0) {
            List<ProRouteProcess> collect = list.stream().sorted(Comparator.comparing(ProRouteProcess::getOrderNum))
                    .collect(Collectors.toList());
            // 重新生成工序
            for (int i = 0; i < collect.size(); i++) {
                ProRouteProcess item = collect.get(i);
                if ((i + 1) >= collect.size()) {
                    item.setNextProcessId(0L);
                    item.setNextProcessName("无");
                } else {
                    ProRouteProcess next = collect.get(i + 1);
                    item.setNextProcessId(next.getProcessId());
                    item.setNextProcessCode(next.getProcessCode());
                    item.setNextProcessName(next.getProcessName());
                }
                proRouteProcessMapper.updateProRouteProcess(item);
            }
        }
        return AjaxResult.success(status);
    }
}
