package com.fesine.auth.controller;

import com.fesine.auth.common.ApplicationContextHelper;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.TestVo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
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

    //@Autowired
    //private IDaoService daoService;

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");

        //throw new PermissionException("permission error");
        //throw new RuntimeException();
        return JsonData.success("hello,permission");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException{
        log.info("validate");
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setId(1);
        IDaoService daoService = ApplicationContextHelper.popBean(IDaoService.class);
        sysUserPo = daoService.selectOne(sysUserPo);
        log.info(JsonMapper.obj2String(sysUserPo));
        BeanValidator.check(vo);
        return JsonData.success("hello,validate");
    }
}
