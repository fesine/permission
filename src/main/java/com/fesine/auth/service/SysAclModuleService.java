package com.fesine.auth.service;

import com.fesine.auth.param.AclModuleParam;

/**
 * @description: 权限模块服务接口
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysAclModuleService {

    /**
     * 保存权限模块信息
     * @param param
     */
    void save(AclModuleParam param);

    /**
     * 更新权限模块信息
     * @param param
     */
    void update(AclModuleParam param);

    /**
     * 删除权限模块信息
     * @param id
     */
    void delete(Integer id);
}
