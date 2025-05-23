package com.ktg.web.service;

import com.ktg.web.domain.SalesOrderSku;
//import com.ktg.web.repository.SalesOrderActiveItemRepositoy;
import com.ktg.web.repository.SalesOrderActiveItemRepository;
import com.ktg.web.repository.SalesOrderSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderSkuService {
    @Autowired
    private SalesOrderSkuRepository salesOrderSkuRepository;
    @Autowired
    private SalesOrderActiveItemRepository salesOrderActiveItemRepository;

    public SalesOrderSku saveSalesOrder(SalesOrderSku salesOrdersku) {
        // 保存 SalesOrder 实体
        return salesOrderSkuRepository.save(salesOrdersku);
    }

    public List<SalesOrderSku> getAllSalesOrders() {
        return salesOrderSkuRepository.findAll();
    }

    public List<Object[]> getSalesOrdersByGlobalOrderNo(String globalOrderNo) {
        return salesOrderActiveItemRepository.findByGlobalOrderNoWithActiveItems(globalOrderNo);
    }
}
