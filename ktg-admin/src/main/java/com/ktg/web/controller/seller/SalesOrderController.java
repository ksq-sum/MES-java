package com.ktg.web.controller.seller;

import com.ktg.web.domain.SalesOrder;
import com.ktg.web.domain.SalesOrderSku;
import com.ktg.web.service.SalesOrderService;
import com.ktg.web.service.SalesOrderSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;  // 引入PUT请求
import org.springframework.web.bind.annotation.RequestParam; // 引入RequestParam

import java.util.*;

@RestController
@RequestMapping("/sales-orders")
public class SalesOrderController {
    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private SalesOrderSkuService salesOrderSkuService;

    @PostMapping
    public ResponseEntity<SalesOrder> createSalesOrder(@RequestBody SalesOrder salesOrder) {
        System.out.println("salesOrder:"+salesOrder);
        SalesOrder createdSalesOrder = salesOrderService.saveSalesOrder(salesOrder);
        return ResponseEntity.ok(createdSalesOrder);
    }

    //查询MES_SALES_ORDERS表的数据
    @PostMapping("getSaleOrders")
    public ResponseEntity<List<Map<String, Object>>> getAllSalesOrders() {
        List<SalesOrder> salesOrders = salesOrderService.getAllSalesOrders();
        List<Map<String, Object>> orderDictionaries = new ArrayList<>();
        //
        for (SalesOrder order : salesOrders) {
            // 创建一个映射来存储订单的字段
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("globalOrderNo", order.getGlobalOrderNo());
            orderMap.put("platformOrderName", order.getPlatformOrderName());
            orderMap.put("requestStatus", order.getRequestStatus());
            orderMap.put("remark", order.getRemark());
            orderMap.put("planEndTime", order.getPlanEndTime());
            orderMap.put("realEndTime", order.getRealEndTime());
            orderMap.put("platformStatus", order.getPlatformStatus());
            orderMap.put("ifOutTime", order.getIfOutTime());
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("createPerson", order.getCreatePerson());
            orderMap.put("updateTime", order.getUpdateTime());
            orderMap.put("deleteFlag", order.getDeleteFlag());
            orderMap.put("globalPaymentTime",order.getGlobalPaymentTime());

            // 在这里处理每个 SalesOrder 对象，例如打印
            String globalOrderNo = order.getGlobalOrderNo();
            List<Object[]> salesOrderSkus = salesOrderSkuService.getSalesOrdersByGlobalOrderNo(globalOrderNo);
            List<Map<String, Object>> skuDictionaries = new ArrayList<>();
//            System.out.println(salesOrderSkus);
            for (Object[] skuArray : salesOrderSkus) {
                Map<String, Object> skuMap = new HashMap<>();
//                System.out.println("sku: " + Arrays.toString(sku));
                SalesOrderSku sku = (SalesOrderSku)skuArray[0];
                skuMap.put("craft",skuArray[1]);
                skuMap.put("depart",skuArray[2]);
                skuMap.put("sku", sku.getSku());
                skuMap.put("msku",sku.getMsku());
                skuMap.put("count",sku.getCount());
                skuMap.put("product_image",sku.getProductImage());
                skuMap.put("rendering_image",sku.getRenderingImage());
                skuMap.put("local_product_name",sku.getLocalProductName());
                skuMap.put("globalOrderNo",globalOrderNo);
                // 添加其他字段
                skuDictionaries.add(skuMap);
            }
            orderMap.put("children", skuDictionaries);

            // 将转换后的字典添加到列表中
            orderDictionaries.add(orderMap);
//            System.out.println(order);
            // 在这里设置每个订单的 children 字段
//            order.setChildren(salesOrderSkus);
        }
        return ResponseEntity.ok(orderDictionaries);
    }

    //更新MES_SALES_ORDERS的platform_statu的值
    @PutMapping("/updatePlatformStatus")
    public Map<String, String> updatePlatformStatus(@RequestParam String globalOrderNo, @RequestParam int platformStatus) {
        // 调用服务层方法更新操作
        boolean isUpdated = salesOrderService.updatePlatformStatus(globalOrderNo, platformStatus);

        if (isUpdated) {
            Map<String, String> response = new HashMap<>();
            response.put("action", "success");
            return response;// 返回200 OK
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("action", "failed");
            return response; // 返回404 Not Found
        }
    }

}
