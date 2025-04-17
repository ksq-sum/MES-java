package com.ktg.web.repository;

import com.ktg.web.domain.MESPROCESS;
import com.ktg.web.domain.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MESPROCESSRepository extends JpaRepository<MESPROCESS, String> {
    @Query("select c from MESPROCESS c where c.process=:process")
    MESPROCESS getMESPROCESSBy(@Param("process") String process);


}
