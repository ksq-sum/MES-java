package com.ktg.web.service;
import com.ktg.web.domain.MESCRAFT;
import com.ktg.web.repository.MESCRAFTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MESCRAFTService {
    @Autowired
    private MESCRAFTRepository repository;

    //查询该产线是否存在
    public MESCRAFT getcraft(String id) {
        return repository.getMESCRAFT(id);
    }

    //添加产线
    public MESCRAFT insertmescraft(MESCRAFT mescraft) {
        return repository.save(mescraft);
    }
}
