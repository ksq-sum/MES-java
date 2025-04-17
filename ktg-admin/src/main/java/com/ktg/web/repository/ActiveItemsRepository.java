package com.ktg.web.repository;

import com.ktg.web.domain.ActiveItems;
import org.springframework.data.jpa.repository.JpaRepository;
//import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface ActiveItemsRepository  extends JpaRepository<ActiveItems, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ActiveItems(sku, msku, localName) VALUES (:sku, :msku, :localName)", nativeQuery = true)
    void insertActiveItem(@Param("sku") String sku, @Param("msku") String msku, @Param("localName") String localName);

    //修改sku产线和工序
    @Modifying
    @Transactional
        @Query("update ActiveItems a set a.craft = :craft,a.depart=:depart where a.sku=:id")
    int updateActiveItems(@Param("id") String id, @Param("craft") String craft,@Param("depart") String depart);
}
