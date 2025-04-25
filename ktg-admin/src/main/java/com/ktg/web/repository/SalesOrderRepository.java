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

    //分页根据一部分条件进行查询
    @Query("select s from SalesOrder s where (:platformStatus is null or s.platformStatus = :platformStatus) " +
            "and ((s.requestStatus=:a or s.requestStatus=:b ) or (:a='' or :a is null))" +
            "and (s.globalOrderNo=:globalOrderNo or :globalOrderNo ='' or :globalOrderNo is null)" +
            "and (s.platformOrderName=:platformOrderName or :platformOrderName ='' or :platformOrderName is null)" +
            "and (s.ifOutTime=:ifOutTime or :ifOutTime ='' or :ifOutTime is null)")
    Page<SalesOrder> findAllBy(Pageable pageable,@Param("platformStatus") Integer platformStatus,@Param("a") Integer a,@Param("b") Integer b,
                               @Param("globalOrderNo") String globalOrderNo,@Param("platformOrderName") String platformOrderName,@Param("ifOutTime") Integer ifOutTime);

    //批量修改
    @Modifying
    @Transactional
    @Query("update SalesOrder o set o.platformStatus = 1 where o.globalOrderNo in :ids")
    int updateTongGuo(@Param("ids") List<String> ids);
}
