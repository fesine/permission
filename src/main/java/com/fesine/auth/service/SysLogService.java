package com.fesine.auth.service;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.param.SearchLogParam;
import com.fesine.auth.po.*;

import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/18
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/18
 */
public interface SysLogService {

    /**
     * 部门更新日志
     * @param before
     * @param after
     */
    void saveDeptLog(SysDeptPo before,SysDeptPo after);

    /**
     * 用户更新日志
     * @param before
     * @param after
     */
    void saveUserLog(SysUserPo before, SysUserPo after);

    /**
     * 权限模块更新日志
     * @param before
     * @param after
     */
    void saveAclModuleLog(SysAclModulePo before, SysAclModulePo after);

    /**
     * 权限更新日志
     * @param before
     * @param after
     */
    void saveAclLog(SysAclPo before, SysAclPo after);

    /**
     * 角色更新日志
     * @param before
     * @param after
     */
    void saveRoleLog(SysRolePo before, SysRolePo after);

    /**
     * 角色权限更新日志
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after);

    /**
     * 角色用户更新日志
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after);

    /**
     * 分页查询日志记录
     * @param param
     * @param pageQuery
     * @return
     */
    PageResult<SysLogPo> searchPageList(SearchLogParam param, PageQuery pageQuery);

    /**
     * 操作还原
     * @param id
     */
    void recover(int id);
}
