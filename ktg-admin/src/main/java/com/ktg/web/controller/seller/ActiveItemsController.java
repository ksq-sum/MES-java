package com.ktg.web.controller.seller;

import com.ktg.web.domain.ActiveItems;
import com.ktg.web.service.ActiveItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActiveItemsController {
    @Autowired
    private ActiveItemsService activeItemsService;

    //查询ACTIVE_ITEMS表的数据
    @PostMapping("getActiveItems")
    public ResponseEntity<List<ActiveItems>> getActiveItems() {
        List<ActiveItems> allActiveItems = activeItemsService.getAllActiveItems();
        return ResponseEntity.ok(allActiveItems);
    }
}
