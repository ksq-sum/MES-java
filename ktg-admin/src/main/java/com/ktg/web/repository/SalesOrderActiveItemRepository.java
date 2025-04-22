package com.ktg.web.repository;

import com.ktg.web.domain.SalesOrderSku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SalesOrderActiveItemRepository extends CrudRepository<SalesOrderSku, Long> {

    @Query("SELECT s, a.craft,a.depart FROM SalesOrderSku s " +
            "left JOIN ActiveItems a ON s.sku = a.sku " +
            "WHERE s.globalOrderNo = :globalOrderNo")
    List<Object[]> findByGlobalOrderNoWithActiveItems(@Param("globalOrderNo") String globalOrderNo);
}
