package com.ktg.web.service;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.repository.MESProductionOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<MESProductionOrders> getAllMESProductionOrders(int page, int size, String sortField, String sortDirection,String workPlanCode) {
        //创建排序对象
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        // 创建分页请求对象
        Pageable pageable = PageRequest.of(page, size, sort);
        return mesProductionOrdersRepository.findAllOrder(pageable,workPlanCode);
    }

    public List<String> findCode(String workPlanCode) {
        return mesProductionOrdersRepository.findCode(workPlanCode);
    }
}
