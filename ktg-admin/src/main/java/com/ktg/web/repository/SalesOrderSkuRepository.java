package com.ktg.web.repository;

import com.ktg.web.domain.SalesOrderSku;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesOrderSkuRepository extends JpaRepository<SalesOrderSku, String> {

        List<SalesOrderSku> findByGlobalOrderNo(String globalOrderNo);

        @Query("SELECT s, a.craft FROM SalesOrderSku s " +
                "INNER JOIN ActiveItems a ON s.sku = a.sku " +
                "WHERE s.globalOrderNo = :globalOrderNo")
        List<Object[]> findByGlobalOrderNoWithActiveItems(@Param("globalOrderNo") String globalOrderNo);

        @Query("SELECT s.sku FROM SalesOrderSku s " +
                "LEFT JOIN ActiveItems a ON s.sku = a.sku " +
                "WHERE (a.craft IS NULL) AND (a.depart IS NULL)" +
                "  AND s.sku IS NOT NULL" +
                "  AND s.sku <> ''" +
                "GROUP BY s.sku")
        List<String> returnNullSkus();

        @Query("select s from SalesOrderSku s")
        List<SalesOrderSku> selectAll();


}
