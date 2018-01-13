package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.AclModuleParam;
import com.fesine.auth.service.SysAclModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {
    @Autowired
    private SysAclModuleService sysAclModuleService;

    @RequestMapping("acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    /**
     * 新增部门
     *
     * @param param
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    /**
     * 更新部门
     *
     * @param param
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }
}
