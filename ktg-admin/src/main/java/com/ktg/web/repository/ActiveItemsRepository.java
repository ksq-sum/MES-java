package com.ktg.web.repository;

import com.ktg.web.domain.ActiveItems;
import org.springframework.data.jpa.repository.JpaRepository;
//import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


public interface ActiveItemsRepository  extends JpaRepository<ActiveItems, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ActiveItems(sku, msku, localName) VALUES (:sku, :msku, :localName)", nativeQuery = true)
    void insertActiveItem(@Param("sku") String sku, @Param("msku") String msku, @Param("localName") String localName);

}
