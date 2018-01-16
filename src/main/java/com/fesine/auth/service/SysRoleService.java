package com.fesine.auth.service;

import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.SysRolePo;
import com.fesine.auth.po.SysUserPo;

import java.util.List;

/**
 * @description: 角色服务接口
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysRoleService {

    /**
     * 保存角色信息
     * @param param
     */
    void save(RoleParam param);

    /**
     * 更新角色信息
     * @param param
     */
    void update(RoleParam param);

    /**
     * 获取所有角色
     * @return
     */
    List<SysRolePo> getAll();

    /**
     * 删除角色
     * @param id
     */
    void delete(int id);

    /**
     * 根据用户id获取角色列表
     * @param userId
     * @return
     */
    List<SysRolePo> getRoleListByUserId(int userId);

    /**
     * 根据权限点id获取角色列表
     * @param aclId
     * @return
     */
    List<SysRolePo> getRoleListByAclId(int aclId);

    /**
     * 根据角色id列表获取用户列表
     * @param roleList
     * @return
     */
    List<SysUserPo> getUserListByRoleList(List<SysRolePo> roleList);
}
