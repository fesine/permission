package com.fesine.auth.filter;

import com.fesine.auth.common.ApplicationContextHelper;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysCoreService;
import com.fesine.auth.util.JsonMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: 权限拦截
 * @author: Fesine
 * @createTime:2018/1/16
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/16
 */
@Slf4j
public class AclControlFilter implements Filter {
    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private static final String NO_AUTH_URL="/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings()
                .splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(NO_AUTH_URL);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String servletPath = req.getServletPath();
        Map parameterMap = req.getParameterMap();
        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        SysUserPo sysUserPo = RequestHolder.getCurrentUser();
        if (sysUserPo == null) {
            log.info("some one visit {}, bu no login,parameter {}", servletPath, JsonMapper
                    .obj2String(parameterMap));
            noAuth(req, resp);
            return;
        }
        SysCoreService coreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!coreService.hasUrlAcl(servletPath)) {
            log.info("{} visit {}, bu no login,parameter {}", JsonMapper.obj2String(sysUserPo),
                    servletPath, JsonMapper.obj2String(parameterMap));
            noAuth(req, resp);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void noAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String servletPath = req.getServletPath();
        if (servletPath.endsWith(".json")) {
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员!");
            resp.addHeader("Content-Type", "application/json");
            resp.getWriter().print(JsonMapper.obj2String(jsonData));
        } else {
            clientRedirect(NO_AUTH_URL, resp);
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
