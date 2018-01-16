package com.fesine.auth.service;

import com.fesine.auth.po.SysUserPo;

import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/16
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/16
 */
public interface SysRoleUserService {

    /**
     * 根据roleId获取用户列表
     * @param roleId
     * @return
     */
    List<SysUserPo> getListByRoleId(Integer roleId);

    void changeRoleUsers(Integer roleId,List<Integer> userIdList);
}
