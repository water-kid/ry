package com.cj.controller;

import com.cj.config.RouYiConfig;
import com.cj.model.CacheConstants;
import com.cj.service.SysConfigServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.UUID;

@RestController
public class CaptchaController {

    @Autowired
    SysConfigServiceImpl configService;

    @Resource(name = "captchaProducer")
    DefaultKaptcha captchaProducer;

    @Resource(name = "captchaProducerMath")
    DefaultKaptcha captchaProducerMath;

    @Autowired

    public void getCode(){

        boolean captchaEnabled = configService.selectCaptchaEnabled();

        if (!captchaEnabled){
            return;
        }

        UUID uuid = UUID.randomUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY+uuid;


        String captchaType = RouYiConfig.getCaptchaType();

        if ("math".equals(captchaType)){
            String text = captchaProducerMath.createText();


            BufferedImage image = captchaProducerMath.createImage(text);
        }


    }
}
