package com.ktg.web.repository;

import com.ktg.web.domain.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
    Optional<SalesOrder> findByGlobalOrderNo(String globalOrderNo);

    @Query("select s from SalesOrder s where (s.platformStatus=:platformStatus or :platformStatus ='' or :platformStatus is null) " +
            "and ((s.requestStatus=:a or s.requestStatus=:b ) or (:a='' or :a is null))")
    Page<SalesOrder> findAllBy(Pageable pageable,@Param("platformStatus") Integer platformStatus,@Param("a") Integer a,@Param("b") Integer b);

    //批量修改
    @Modifying
    @Transactional
    @Query("update SalesOrder o set o.platformStatus = 1 where o.globalOrderNo in :ids")
    int updateTongGuo(@Param("ids") List<String> ids);
}
