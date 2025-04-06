package com.ktg.web.repository;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MESProductionWorksRepository extends JpaRepository<MESProductionWorks, Integer> {
    List<MESProductionWorks> findByWorkPlanCode(String workPlanCode);
}
