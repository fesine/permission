package com.fesine.auth.common;

import com.fesine.auth.po.SysUserPo;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 线程变量类
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
public class RequestHolder {

    private static final ThreadLocal<SysUserPo> userHolder = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUserPo sysUserPo){
        userHolder.set(sysUserPo);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUserPo getCurrentUser(){
        return userHolder.get();
    }
    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }


}
