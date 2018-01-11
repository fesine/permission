package com.fesine.auth.controller;

import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.po.SysUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private IDaoService daoService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        log.info("hello");
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setId(1);
        sysUserPo = daoService.selectOne(sysUserPo);
        return "hello,permission";
    }
}
