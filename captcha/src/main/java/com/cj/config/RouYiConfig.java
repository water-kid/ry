package com.cj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ry")
public class RouYiConfig {
    /**
     * 静态的： 可以直接通过类名引用
     */
    private static String captchaType;

    public static String getCaptchaType() {
        return captchaType;
    }

    public static void setCaptchaType(String captchaType) {
        RouYiConfig.captchaType = captchaType;
    }
}
