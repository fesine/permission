package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.dto.AclModuleLevelDto;
import com.fesine.auth.param.AclModuleParam;
import com.fesine.auth.service.SysAclModuleService;
import com.fesine.auth.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @Autowired
    private SysTreeService treeService;

    @RequestMapping("acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    /**
     * 新增权限模块
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
     * 更新权限模块
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

    /**
     * 获取权限模块树
     *
     * @return
     */
    @RequestMapping("tree.json")
    @ResponseBody
    public JsonData tree() {
        List<AclModuleLevelDto> dtoList = treeService.aclModuleTree();
        return JsonData.success(dtoList);
    }

    @RequestMapping("delete.json")
    @ResponseBody
    public JsonData deleteAclModule(@RequestParam Integer id) {
        sysAclModuleService.delete(id);
        return JsonData.success();
    }
}
