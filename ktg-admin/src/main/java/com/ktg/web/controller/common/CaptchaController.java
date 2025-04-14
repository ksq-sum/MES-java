package com.ktg.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONUtil;
import com.asinking.com.openapi.sdk.core.HttpMethod;
import com.asinking.com.openapi.sdk.core.HttpRequest;
import com.asinking.com.openapi.sdk.core.HttpResponse;
import com.asinking.com.openapi.sdk.okhttp.HttpExecutor;
import com.asinking.com.openapi.sdk.sign.ApiSign;
import com.ktg.common.config.RuoYiConfig;
import com.ktg.framework.web.domain.server.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Producer;
import com.ktg.common.constant.Constants;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.core.redis.RedisCache;
import com.ktg.common.utils.sign.Base64;
import com.ktg.common.utils.uuid.IdUtils;
import com.ktg.system.service.ISysConfigService;

import static com.ktg.framework.datasource.DynamicDataSourceContextHolder.log;

/**
 * 验证码操作处理
 * 
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        ajax.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = RuoYiConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    public static void main(String[] args) throws Exception {
        String appId = "ak_nbbisNHoOA6Hg";

        Map<String, Object> queryParam = new HashMap<>();
        long timestampInSeconds = System.currentTimeMillis() / 1000;
        queryParam.put("timestamp", timestampInSeconds);
        queryParam.put("access_token", "18dd3c07-05b0-463f-ba17-a46f1b091e80");
        queryParam.put("app_key", appId);

        Map<String, Object> body = new HashMap();
        body.put("start_time",1738915191);
        body.put("end_time",1739428437);
        body.put("date_type", "update_time");
        body.put("offset", 0);
        body.put("length", 20);
        body.put("order_status", 5);

        List<Integer> platform_code = Arrays.asList(10002);
        body.put("platform_code", JSONUtil.toJsonStr(platform_code));


        Map<String, Object> signMap = new HashMap();
        signMap.putAll(queryParam);
        signMap.putAll(body);

        System.out.println("signMap:"+signMap);
        System.out.println("appId:"+appId);
        String sign = ApiSign.sign(signMap, appId);
        log.info("sign:{}", sign);
        log.info(String.valueOf(timestampInSeconds));
    }
}
