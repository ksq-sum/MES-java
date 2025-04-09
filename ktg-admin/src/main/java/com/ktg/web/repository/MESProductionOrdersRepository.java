package com.ktg.web.repository;

import com.ktg.web.domain.MESProductionOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MESProductionOrdersRepository extends JpaRepository<MESProductionOrders, Integer> {
    // 这里可以定义一些自定义查询方法
}
