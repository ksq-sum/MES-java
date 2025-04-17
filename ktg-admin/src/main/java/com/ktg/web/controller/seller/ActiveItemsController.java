package com.ktg.web.controller.seller;

import com.ktg.web.domain.ActiveItems;
import com.ktg.web.service.ActiveItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/addSku")
    public ResponseEntity<List<ActiveItems>> addSku(@RequestBody List<Map<String, String>> skuItems) {
        List<ActiveItems> addedItems = new ArrayList<>();

        for (Map<String, String> item : skuItems) {
            String sku = item.get("sku");
            String msku = item.get("msku");
            String localName = item.get("localProductName");
            ActiveItems activeItem = activeItemsService.addActiveItem(sku, msku, localName);
            addedItems.add(activeItem);
        }

        return ResponseEntity.ok(addedItems);
    }

}
