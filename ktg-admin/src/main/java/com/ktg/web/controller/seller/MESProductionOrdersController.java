package com.ktg.web.controller.seller;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.service.MESProductionOrdersService;
import com.ktg.web.service.MESProductionWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/productionOrders")
public class MESProductionOrdersController {
    @Autowired
    private MESProductionOrdersService mesProductionOrdersService;
    @Autowired
    private MESProductionWorksService mesProductionWorksService;

    @PostMapping("createProductionOrder")
    public ResponseEntity<String> createProductionOrder(@RequestBody MESProductionOrders productionOrder) {
        return mesProductionOrdersService.saveMESProductionOrders(productionOrder);
    }

    //生成一个完整的计划订单
    @PostMapping("createOrder")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> createOrder(@RequestBody List<Map<String, Object>> orders) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat create_time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String orderTime = sdf.format(new Date());
            String currentTime = create_time_sdf.format(new Date());

            // 生成一个生产计划
            MESProductionOrders mesProductionOrders = new MESProductionOrders();
            mesProductionOrders.setCreateTime(currentTime);
            mesProductionOrders.setPlanStatu(0);
            mesProductionOrders.setWorkPlanCode("RO_" + orderTime);

            // 保存生产计划，如果失败则抛出异常
            ResponseEntity<String> orderResult = createProductionOrder(mesProductionOrders);
            if (!orderResult.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to create production order: " + orderResult.getBody());
            }

            // 生成"生产计划"下的"生产工单"
            for (Map<String, Object> order : orders) {
                List<Map<String, Object>> children = (List<Map<String, Object>>) order.get("children");
                if (children == null) {
                    throw new RuntimeException("Children list is null for order: " + order.get("globalOrderNo"));
                }

                System.out.println("children:" + children);

                for (Map<String, Object> child : children) {
                    try {
                        MESProductionWorks mesProductionWorks = new MESProductionWorks();
                        mesProductionWorks.setGlobalOrderNo(order.get("globalOrderNo").toString());
                        mesProductionWorks.setWorkPlanCode("RO_" + orderTime);
                        mesProductionWorks.setOrderPayTime(order.get("globalPaymentTime").toString());
                        mesProductionWorks.setPlanEndTime(order.get("planEndTime").toString());

                        mesProductionWorks.setSku(child.get("sku").toString());
                        mesProductionWorks.setMsku(child.get("msku").toString());
                        mesProductionWorks.setCount((Integer) child.get("count"));
                        mesProductionWorks.setCraft(child.get("craft").toString());
                        mesProductionWorks.setDepart(child.get("depart").toString());
                        mesProductionWorks.setProductImage(child.get("product_image").toString());
                        mesProductionWorks.setRenderingImage(child.get("rendering_image").toString());
                        mesProductionWorks.setLocalName(child.get("local_product_name").toString());
                        mesProductionWorks.setProductPlanCode("RW_" + orderTime + "_" + child.get("sku").toString());

                        System.out.println("mesProductionWorks:" + mesProductionWorks);

                        // 保存生产工单，如果失败则抛出异常
                        ResponseEntity<String> orderWork = mesProductionWorksService.saveMESProductionWorks(mesProductionWorks);
                        if (!orderWork.getStatusCode().is2xxSuccessful()) {
                            throw new RuntimeException("Failed to save production work: " + orderWork.getBody());
                        }
                    } catch (Exception e) {
                        // 记录具体错误，并重新抛出异常以触发事务回滚
                        System.err.println("Error processing child item: " + e.getMessage());
                        throw new RuntimeException("Error processing child item with SKU: " +
                                (child.get("sku") != null ? child.get("sku").toString() : "unknown"), e);
                    }
                }
            }

            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            // 手动标记事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("Transaction rolled back due to: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to create order: " + e.getMessage());
        }
    }

    @PostMapping("returnProductionOrder")
    //返回一个完整的计划订单
    public List<Map<String, Object>> returnProductionOrder() {
        List<MESProductionOrders> allMESProductionOrders = mesProductionOrdersService.getAllMESProductionOrders();
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (MESProductionOrders mesProductionOrder : allMESProductionOrders) {
            Map<String, Object> map = new HashMap<>();
            map.put("workPlanCode", mesProductionOrder.getWorkPlanCode());
            map.put("planStatu",mesProductionOrder.getPlanStatu());
            List<MESProductionWorks> allMESProductionWorks = mesProductionWorksService.getAllMESProductionOrders(mesProductionOrder.getWorkPlanCode());
            map.put("children",allMESProductionWorks);
            dataList.add(map);
        }

        return dataList;

    }

}
