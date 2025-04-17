package com.ktg.web.controller.seller;

import com.ktg.web.domain.ActiveItems;
import com.ktg.web.domain.MESCRAFT;
import com.ktg.web.domain.MESPROCESS;
import com.ktg.web.service.ActiveItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ActiveItemsController {
    @Autowired
    private ActiveItemsService activeItemsService;
    @Autowired
    private MESPROCESSController controller = new MESPROCESSController();
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

    //修改sku产线和工序
    @Transactional
    @PostMapping("/updateActiveItems")
    public void updateActiveItems(@RequestBody ActiveItems activeItems) {
        //当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        //实体
        MESPROCESS mesprocess=new MESPROCESS();
        MESCRAFT mescraft=new MESCRAFT();
        //赋值
        mescraft.setCreateTime(formattedTime);
        mescraft.setDeleteFlag(0);
        mescraft.setCraft(activeItems.getCraft());
        mesprocess.setProcess(activeItems.getDepart());
        mesprocess.setCreateTime(formattedTime);
        mesprocess.setDeleteFlag(0);
        //执行产线和工序的查询判断方法和添加
        controller.insertProcess(mesprocess);
        controller.insertcraft(mescraft);
        //修改商品的产线和工序
        activeItemsService.updateActiveItems(activeItems.getSku(), activeItems.getCraft(), activeItems.getDepart());
    }
}
