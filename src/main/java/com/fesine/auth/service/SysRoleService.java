package com.fesine.auth.service;

import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.SysRolePo;

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
}
