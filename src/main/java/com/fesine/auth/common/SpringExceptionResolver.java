package com.fesine.auth.common;

import com.fesine.auth.exception.ParamException;
import com.fesine.auth.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 自定义异常解析器
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private static final String JSON = ".json";
    private static final String PAGE = ".page";

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o,
                                         Exception e) {
        String url = httpServletRequest.getRequestURI();
        String defaultMsg = "System Error";
        ModelAndView mv;
        JsonData result;
        //.json , .page
        //所有json数据请求，使用.json结尾
        if (url.endsWith(JSON)) {
            if (e instanceof PermissionException || e instanceof ParamException) {
                result = JsonData.fail(e.getMessage());
            } else {
                log.error("unknown json exception, url:{}",url,e);
                result = JsonData.fail(defaultMsg);
            }
            mv = new ModelAndView("jsonView", result.toMap());
        } else if (url.endsWith(PAGE)) {
            log.error("unknown page exception, url:{}", url, e);
            //所有页面请求，使用.page结尾
            result = JsonData.fail(e.getMessage());
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknown exception, url:{}", url, e);
            result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
