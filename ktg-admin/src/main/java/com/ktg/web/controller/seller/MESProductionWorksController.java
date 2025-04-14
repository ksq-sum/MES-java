package com.ktg.web.controller.seller;

import com.ktg.web.domain.MESProductionOrders;
import com.ktg.web.domain.MESProductionWorks;
import com.ktg.web.service.MESProductionOrdersService;
import com.ktg.web.service.MESProductionWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productionWorks")
public class MESProductionWorksController {
    @Autowired
    private MESProductionWorksService mesProductionWorksService;

    @PostMapping("createProductionWork")
    public ResponseEntity<String> createProductionOrder(@RequestBody MESProductionWorks mesProductionWorks) {
        return mesProductionWorksService.saveMESProductionWorks(mesProductionWorks);
    }

    @PutMapping("/updateorderWork")
    public int updateorderWork(@RequestBody List<Integer> ids) {
        return mesProductionWorksService.updateorderWork(ids);
    }
}
