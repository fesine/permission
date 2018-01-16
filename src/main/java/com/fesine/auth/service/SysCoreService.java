package com.fesine.auth.service;

import com.fesine.auth.po.SysAclPo;

import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/15
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/15
 */
public interface SysCoreService {

    /**
     * 获取当前用户的权限点
     * @return
     */
    List<SysAclPo> getCurrentUserAclList();

    /**
     * 获取某一用户权限点
     * @param userId
     * @return
     */
    List<SysAclPo> getUserAclList(int userId);

    /**
     * 获取当前角色的权限点
     * @param roleId
     * @return
     */
    List<SysAclPo> getRoleAclList(int roleId);

    /**
     * 判断是否具有访问权限
     * @param url
     * @return
     */
    boolean hasUrlAcl(String url);
}
