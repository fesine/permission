package com.fesine.auth.controller;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.UserParam;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysRoleService;
import com.fesine.auth.service.SysTreeService;
import com.fesine.auth.service.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysTreeService treeService;
    @Autowired
    private SysRoleService roleService;

    @RequestMapping("noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }

    @RequestMapping("/user.page")
    public ModelAndView page() {
        return new ModelAndView("user");
    }

    /**
     * 新增用户
     *
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        userService.save(param);
        return JsonData.success();
    }


    /**
     * 更新用户
     *
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        userService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam int deptId, PageQuery pageQuery) {
        PageResult<SysUserPo> page = userService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(page);
    }

    /**
     * 查看指定用户的角色和权限
     * @param id
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam int id) {
        //获取角色
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", treeService.userAclTree(id));
        map.put("roles",roleService.getRoleListByUserId(id));
        return JsonData.success(map);
    }
}
