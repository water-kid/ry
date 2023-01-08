package com.cj.service;

import com.cj.model.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class SysConfigServiceImpl {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 查看验证码开关
     * @return
     */
    public boolean selectCaptchaEnabled(){
        // 数据库配置的字段
        String configKey = "sys.account.captchaEnabled";

        String configValue = redisTemplate.opsForValue().get(CacheConstants.SYS_CONFIG_KEY + configKey);

        if (!StringUtils.isEmpty(configValue)){
            return "true".equals(configValue);
        }

        // 从数据库返回
        return true;

    }



}
