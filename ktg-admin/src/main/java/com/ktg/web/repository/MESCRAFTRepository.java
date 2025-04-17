package com.ktg.web.repository;

import com.ktg.web.domain.MESCRAFT;
import com.ktg.web.domain.MESPROCESS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MESCRAFTRepository extends JpaRepository<MESCRAFT, String> {
    @Query("select c from MESCRAFT c where c.craft=:craft")
    MESCRAFT getMESCRAFT(@Param("craft") String craft);

}
