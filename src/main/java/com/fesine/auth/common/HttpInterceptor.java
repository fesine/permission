package com.fesine.auth.common;

import com.fesine.auth.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 自定义http请求拦截器，前后处理操作
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME="requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map paramMap = request.getParameterMap();
        if(url.endsWith(".page")|| url.endsWith(".json")){
            log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(paramMap));
            request.setAttribute(START_TIME,System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //String url = request.getRequestURI().toString();
        //log.info("request finished. url:{}, cost:{}", url, System.currentTimeMillis()
        //        - (Long) request.getAttribute(START_TIME));
        RequestHolder.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object
            handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        if (url.endsWith(".page") || url.endsWith(".json")) {
            log.info("request completed. url:{}, cost:{}", url, System.currentTimeMillis()
                    - (Long)request.getAttribute(START_TIME));
        }
        RequestHolder.remove();
    }
}
