package com.ktg.web.service;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.repository.MESProductionOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MESProductionOrdersService {
    @Autowired
    private MESProductionOrdersRepository mesProductionOrdersRepository;

    public ResponseEntity<String> saveMESProductionOrders(MESProductionOrders mesProductionOrder) {
        // 保存 SalesOrder 实体
        mesProductionOrdersRepository.save(mesProductionOrder);
        return new ResponseEntity<>("生产订单已成功创建", HttpStatus.CREATED);
    }

    public List<MESProductionOrders> getAllMESProductionOrders() {
        return mesProductionOrdersRepository.findAll();
    }
}
