package com.cj.filter;

import com.cj.request.RepeatableReadRequestWrapper;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RepeatableRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 前端传 Content-type 有的时候 带了编码格式，有的时候没带
        if (StringUtils.startsWithIgnoreCase(request.getContentType(),"application/json")){
            RepeatableReadRequestWrapper requestWrapper = new RepeatableReadRequestWrapper(httpServletRequest, ((HttpServletResponse) response));

            chain.doFilter(requestWrapper,response);
            return;
        }

        chain.doFilter(request,response);
    }
}
