package com.ktg.web.service;

import com.ktg.web.domain.SalesOrder;
import com.ktg.web.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    public SalesOrder saveSalesOrder(SalesOrder salesOrder) {
        // 保存 SalesOrder 实体
        return salesOrderRepository.save(salesOrder);
    }

    public Page<SalesOrder> getAllSalesOrders(int page, int size, String sortField, String sortDirection,Integer platformStatus,Integer a,Integer b) {
        //创建排序对象
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        // 创建分页请求对象
        Pageable pageable = PageRequest.of(page, size, sort);
        return salesOrderRepository.findAllBy(pageable,platformStatus,a,b);
    }

    public boolean updatePlatformStatus(String globalOrderNo, int platformStatus) {
        // 找到对应的订单
        Optional<SalesOrder> optionalOrder = salesOrderRepository.findByGlobalOrderNo(globalOrderNo);
        if (optionalOrder.isPresent()) {
            SalesOrder salesOrder = optionalOrder.get();
            salesOrder.setPlatformStatus(platformStatus); // 更新状态
            salesOrderRepository.save(salesOrder); // 保存更改
            return true; // 返回成功
        }
        return false; // 未找到订单
    }
}
