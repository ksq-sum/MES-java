package com.ktg.web.controller.seller;

import com.ktg.web.service.MESCRAFTService;
import com.ktg.web.service.SalesOrderSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mes_craft")
public class MESCRAFTController {
    @Autowired
    private MESCRAFTService mesService;
}
