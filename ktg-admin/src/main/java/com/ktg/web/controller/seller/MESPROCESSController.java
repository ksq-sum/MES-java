package com.ktg.web.controller.seller;

import com.ktg.web.domain.MESCRAFT;
import com.ktg.web.domain.MESPROCESS;
import com.ktg.web.service.MESCRAFTService;
import com.ktg.web.service.MESPROCESSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mes_process")
public class MESPROCESSController {
    @Autowired
    private MESPROCESSService mesProcessService;
    @Autowired
    private MESCRAFTService mescraftService;


    //添加工序
    public void insertProcess(MESPROCESS mesProcess) {
        MESPROCESS mesprocess1=mesProcessService.getprocess(mesProcess.getProcess());
        if(mesprocess1==null){
            mesProcessService.insertprocess(mesProcess);
        }
    }

    //添加产线
    public void insertcraft(MESCRAFT mescraft) {
        MESCRAFT mescraft1=mescraftService.getcraft(mescraft.getCraft());
        if(mescraft1==null){
            mescraftService.insertmescraft(mescraft);
        }
    }

}
