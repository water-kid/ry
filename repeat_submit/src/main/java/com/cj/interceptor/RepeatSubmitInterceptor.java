package com.cj.interceptor;

import com.cj.RedisCache;
import com.cj.annotation.RepeatSubmit;
import com.cj.request.RepeatableReadRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {
    // redis中key的前缀
    private static final String REPEAT_SUBMIT_KEY="repeat_submit_key";
    // 存入redis的map中的key
    private static final String REPEAT_PARAMS="repeat_params";
    private static final String REPEAT_TIME="repeat_time";
    private static final String HEADER="Authorization";


    @Autowired
    RedisCache redisCache;

    /**
     *
     * @param request
     * @param response
     * @param handler   拦截下来的对象
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取参数，地址，在redis中判断是否有过

        if (handler instanceof HandlerMethod){
            // 接口方法封装成了 HandlerMethod
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null){
                // 需要判断请求重复

                // 前端传入的 请求参数
                String nowParams="";

                if (request instanceof RepeatableReadRequestWrapper){
                    // 请求参数是json
                    nowParams = ((RepeatableReadRequestWrapper) request).getReader().readLine();
                }

                if (StringUtils.isEmpty(nowParams)){
                    // 没拿到参数，是 key-value形式参数
                    nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
                }

                // 当前传递的参数信息
                HashMap<String, Object> nowDataMap = new HashMap<>();
                nowDataMap.put(REPEAT_PARAMS,nowParams);
                nowDataMap.put(REPEAT_TIME,System.currentTimeMillis());


                String requestURI = request.getRequestURI();
                String header = request.getHeader(HEADER);

                // 缓存key  = prefix + url + 你是谁   缓存的value是个Map
                String cacheKey = REPEAT_SUBMIT_KEY+requestURI+header.replace("Bearer","");

                Object cacheObject = redisCache.getCacheObject(cacheKey);
                if (cacheObject !=null){
                    // 从redis获取参数信息，， 和 当前传递参数比较，，比较，内容，和时间
                    Map<String, Object> map = (Map<String, Object>) cacheObject;
                    int interval = annotation.interval();
                    boolean compareParams = nowDataMap.get(REPEAT_PARAMS).equals(map.get(REPEAT_PARAMS));
                    boolean compareTime =((long) nowDataMap.get(REPEAT_TIME))- ((long) map.get(REPEAT_TIME))<interval;
                    // 参数一样，在interval内
                    if (compareParams && compareTime){
                        // 需要拦截
                        HashMap<String, Object> resultMap = new HashMap<>();
                        resultMap.put("code",500);
                        resultMap.put("message",annotation.message());
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(resultMap));
                        return false;
                    }
                }

                // 没有缓存，第一次请求
                redisCache.setCacheObject(cacheKey,nowDataMap,annotation.interval(), TimeUnit.MILLISECONDS);

            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
