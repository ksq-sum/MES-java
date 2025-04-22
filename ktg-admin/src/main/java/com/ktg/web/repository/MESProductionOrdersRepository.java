package com.ktg.web.repository;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.domain.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MESProductionOrdersRepository extends JpaRepository<MESProductionOrders, Integer> {
    // 这里可以定义一些自定义查询方法
    @Query("select o from MESProductionOrders o where (o.workPlanCode is null or o.workPlanCode = '') or (:workPlanCode is null or o.workPlanCode = :workPlanCode)")
    Page<MESProductionOrders> findAllOrder(Pageable pageable, @Param("workPlanCode") String workPlanCode);
}
