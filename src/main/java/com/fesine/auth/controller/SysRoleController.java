package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.SysRolePo;
import com.fesine.auth.service.SysRoleService;
import com.fesine.auth.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysTreeService treeService;

    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    /**
     * 新增权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAcl(RoleParam param) {
        roleService.save(param);
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
    public JsonData updateAcl(RoleParam param) {
        roleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        List<SysRolePo> page = roleService.getAll();
        return JsonData.success(page);
    }

    @RequestMapping(value = "/roleTree.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonData roleTree(@RequestParam int roleId) {
        return JsonData.success(treeService.roleTree(roleId));
    }

}
