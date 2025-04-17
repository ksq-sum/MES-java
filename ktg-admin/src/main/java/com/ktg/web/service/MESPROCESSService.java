package com.ktg.web.service;
import com.ktg.web.domain.MESPROCESS;
import com.ktg.web.repository.MESPROCESSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MESPROCESSService {
    @Autowired
    private MESPROCESSRepository mesprocessRepository;

    //查询该工序是否存在
    public MESPROCESS getprocess(String id) {
        return mesprocessRepository.getMESPROCESSBy(id);
    }

    //添加工序
    public MESPROCESS insertprocess(MESPROCESS mesprocess) {
        return mesprocessRepository.save(mesprocess);
    }
}
