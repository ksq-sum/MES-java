package com.ktg.web.service;

import com.ktg.web.domain.SalesOrderSku;
//import com.ktg.web.repository.SalesOrderActiveItemRepositoy;
import com.ktg.web.repository.SalesOrderActiveItemRepository;
import com.ktg.web.repository.SalesOrderRepository;
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
    @Autowired
    private SalesOrderRepository salesorderrepository;

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
    //调用dao
    public int updateTongGuo(List<String> ids) {
        return salesorderrepository.updateTongGuo(ids);
    }

    public List<String> returnNullSkus() {
        return salesOrderSkuRepository.returnNullSkus();
    }

    //部分直接查询全部
    public List<SalesOrderSku> selectAll(){
        return salesOrderSkuRepository.selectAll();
    }
}
