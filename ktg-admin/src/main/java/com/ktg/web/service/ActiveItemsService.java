package com.ktg.web.service;

import com.ktg.web.domain.ActiveItems;
import com.ktg.web.domain.SalesOrder;
import com.ktg.web.repository.ActiveItemsRepository;
import com.ktg.web.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveItemsService {
    @Autowired
    private ActiveItemsRepository activeItemsRepository;

    public ActiveItems save(ActiveItems activeItems) {
        return activeItemsRepository.save(activeItems);
    }

    public List<ActiveItems> getAllActiveItems() {
        return activeItemsRepository.findAll();
    }
}
