package com.ktg.web.repository;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.domain.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MESProductionOrdersRepository extends JpaRepository<MESProductionOrders, Integer> {
    // 这里可以定义一些自定义查询方法
    @Query("select o from MESProductionOrders o where :workPlanCode is null or o.workPlanCode =:workPlanCode")
    Page<MESProductionOrders> findAllOrder(Pageable pageable, @Param("workPlanCode") String workPlanCode);

    //销售订单查询生产计划
    @Query("select o.workPlanCode from MESProductionWorks o where o.globalOrderNo=:workPlanCode")
    List<String> findCode(@Param("workPlanCode") String workPlanCode);

    //备注修改
    @Modifying
    @Transactional
    @Query("update MESProductionOrders o set o.remack = :remack where o.workPlanCode=:id")
    int updateremack(@Param("id") String id, @Param("remack") String remack);
}
