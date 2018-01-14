package com.fesine.auth.service;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.param.AclParam;
import com.fesine.auth.po.SysAclPo;

/**
 * @description: 部门管理服务接口
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysAclService {

    /**
     * 保存部门信息
     * @param param
     */
    void save(AclParam param);

    /**
     * 更新部门信息
     * @param param
     */
    void update(AclParam param);

    /**
     * 根据权限模块id获取权限点列表
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    PageResult<SysAclPo> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery);
}
