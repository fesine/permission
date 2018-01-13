package com.fesine.auth.controller;

import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysUserService;
import com.fesine.auth.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
@Controller
public class UserController {
    @Autowired
    private SysUserService userService;

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }
    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //可以是邮箱或手机号码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SysUserPo sysUserPo = userService.findByKeyword(username);
        String errMsg = "";
        String ret = request.getParameter("ret");
        if (StringUtils.isBlank(username)) {
            errMsg = "用户名不可为空";
        } else if (StringUtils.isBlank(password)) {
            errMsg = "密码不可为空";
        } else if (sysUserPo == null) {
            errMsg = "用户不存在";
        } else if (!sysUserPo.getPassword().equals(MD5Util.encrypt(password))) {
            errMsg = "用户名或密码错误";
        } else if (sysUserPo.getStatus() != 1) {
            errMsg = "账户已被冻结，请联系管理员";
        } else {
            //登录成功操作
            request.getSession().setAttribute("user", sysUserPo);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
            }
        }

        request.setAttribute("error", errMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
}
