package com.ktg.web.repository;
import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MESProductionWorksRepository extends JpaRepository<MESProductionWorks, Integer> {
    List<MESProductionWorks> findByWorkPlanCode(String workPlanCode);

    //批量修改生产工单状态
    @Modifying
    @Transactional
    @Query("update MESProductionWorks o set o.platformStatu = 2 where o.id in :ids")
    int updateorderWork(@Param("ids") List<Integer> ids);


}
