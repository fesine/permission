package com.fesine.auth.service;

import com.fesine.auth.dto.AclModuleLevelDto;
import com.fesine.auth.dto.DeptLevelDto;

import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysTreeService {

    /**
     * 生成部门树
     * @return
     */
    List<DeptLevelDto> deptTree();

    /**
     * 生成权限模块树
     * @return
     */
    List<AclModuleLevelDto> aclModuleTree();

    /**
     * 根据角色id获取权限模块树
     * @param roleId
     * @return
     */
    List<AclModuleLevelDto> roleTree(int roleId);

    /**
     * 根据用户id获取分配的权限树
     * @param userId
     * @return
     */
    List<AclModuleLevelDto> userAclTree(int userId);
}
