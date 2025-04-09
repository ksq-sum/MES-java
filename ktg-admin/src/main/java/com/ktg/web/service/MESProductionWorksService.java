package com.ktg.web.service;

import com.ktg.framework.web.domain.server.Sys;
import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.repository.MESProductionOrdersRepository;
import com.ktg.web.repository.MESProductionWorksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MESProductionWorksService {
    @Autowired
    private MESProductionWorksRepository mesProductionWorksRepository;

    public ResponseEntity<String> saveMESProductionWorks(MESProductionWorks mesProductionWorks) {
        System.out.println("mesProductionWorks:"+mesProductionWorks);
        // 保存 SalesOrder 实体
        mesProductionWorksRepository.save(mesProductionWorks);
        return new ResponseEntity<>("生产订单已成功创建", HttpStatus.CREATED);
    }

    public List<MESProductionWorks> getAllMESProductionOrders(String workPlanCode) {
        List<MESProductionWorks> byWorkPlanCode = mesProductionWorksRepository.findByWorkPlanCode(workPlanCode);
        return byWorkPlanCode;
    }
    //批量修改生产工单状态
    public int updateorderWork(List<Integer> ids) {
        return mesProductionWorksRepository.updateorderWork(ids);
    }

}
