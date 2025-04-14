package com.ktg.web.controller.seller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.domain.SalesOrderSku;
import com.ktg.web.service.SalesOrderSkuService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales-orders-sku")
public class SalesOrderSkuController {
    @Autowired
    private SalesOrderSkuService salesOrderSkuService;

    @PostMapping
    public ResponseEntity<SalesOrderSku> createSalesOrder(@RequestBody SalesOrderSku salesOrderSku) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 将对象转换为 JSON 字符串
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(salesOrderSku);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("jsonStringx:"+jsonString);
        SalesOrderSku createdSalesOrderSku = salesOrderSkuService.saveSalesOrder(salesOrderSku);
        return ResponseEntity.ok(createdSalesOrderSku);
    }

    //查询MES_SALES_ORDERS表的数据
    @PostMapping("getSaleSkuOrders")
    public ResponseEntity<List<Object[]>> getAllSalesOrders() {
        String globalOrderNo = "103542217919395742";
        List<Object[]> salesOrderSkus = salesOrderSkuService.getSalesOrdersByGlobalOrderNo(globalOrderNo);
        return ResponseEntity.ok(salesOrderSkus);
    }

    //多选通过销售订单
    @PutMapping("updateTongGuo")
    public int updateTongGuo(@RequestBody List<String> ids) {
        System.out.println(ids);
        return salesOrderSkuService.updateTongGuo(ids);
    }

    //查询全部销售订单
    @GetMapping("selectAll")
    public List<SalesOrderSku> selectAll(){
        return salesOrderSkuService.selectAll();
    }
}
