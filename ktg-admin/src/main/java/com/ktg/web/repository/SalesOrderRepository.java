package com.ktg.web.repository;

import com.ktg.web.domain.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
    Optional<SalesOrder> findByGlobalOrderNo(String globalOrderNo);
}
