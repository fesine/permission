package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.SysRolePo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.*;
import com.fesine.auth.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleAclService roleAclService;

    @Autowired
    private SysRoleUserService roleUserService;

    @Autowired
    private SysUserService sysUserService;

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

    @RequestMapping(value = "/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam int roleId) {
        return JsonData.success(treeService.roleTree(roleId));
    }

    @RequestMapping(value = "/changeAcls.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonData changeAcls(@RequestParam int roleId,
                               @RequestParam(required = false,defaultValue = "") String aclIds) {
        List<Integer> aclsList = StringUtil.splitToListInt(aclIds);
        roleAclService.changeRoleAcls(roleId, aclsList);
        return JsonData.success();
    }

    @RequestMapping(value = "/changeUsers.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonData changeUsers(@RequestParam int roleId,
                               @RequestParam(required = false,defaultValue = "") String userIds) {
        List<Integer> userIdsList = StringUtil.splitToListInt(userIds);
        roleUserService.changeRoleUsers(roleId, userIdsList);
        return JsonData.success();
    }

    @RequestMapping(value = "/users.json")
    @ResponseBody
    public JsonData users(@RequestParam int roleId) {
        List<SysUserPo> selectedUserList = roleUserService.getListByRoleId(roleId);
        List<SysUserPo> allUserList = sysUserService.getAll();
        List<SysUserPo> unselectedUserList = Lists.newArrayList();
        Set<Integer> selectSet = selectedUserList.stream().map(sysUserPo -> sysUserPo.getId())
                .collect(Collectors.toSet());
        for (SysUserPo sysUserPo : allUserList) {
            if (sysUserPo.getStatus() == 1 && !selectSet.contains(sysUserPo.getId())) {
                unselectedUserList.add(sysUserPo);
            }
        }
        //过滤状态!=1的用户，是否有必要
        //unselectedUserList = unselectedUserList.stream().filter(sysUserPo -> sysUserPo.getStatus
        //        () != 1).collect(Collectors.toList());
        Map<String, List<SysUserPo>> map=Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam Integer id){
            roleService.delete(id);
        return JsonData.success();
    }

}
