package com.ktg.mes.wm.controller.mobile;

import com.ktg.common.core.domain.AjaxResult;
import com.ktg.mes.wm.domain.WmBarcode;
import com.ktg.mes.wm.service.IWmBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinjinlu
 * @description
 * @date 2024/12/5
 */
@RestController
@RequestMapping("/mobile/wm/barcode")
public class WmBarcodeMobController {
    @Autowired
    private IWmBarcodeService wmBarcodeService;

    /**
     * 获取某个对象的二维码地址
     * @return
     */
    @GetMapping("/getBarcodeUrl")
    public AjaxResult getBarcodeUrl(WmBarcode wmBarcode){
        List<WmBarcode> list = wmBarcodeService.selectWmBarcodeList(wmBarcode);
        if(!CollectionUtils.isEmpty(list)){
            return AjaxResult.success(list.get(0));
        }
        return AjaxResult.success();
    }
}
