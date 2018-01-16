package com.fesine.auth.service;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.param.UserParam;
import com.fesine.auth.po.SysUserPo;

import java.util.List;

/**
 * @description: 用户管理服务接口
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysUserService {
    /**
     * 用户添加
     * @param param
     */
    void save(UserParam param);

    /**
     * 用户更新
     * @param param
     */
    void update(UserParam param);

    /**
     * 根据关键字查询用户
     * @param keyword
     * @return
     */
    SysUserPo findByKeyword(String keyword);

    /**
     * 根据部门id分页获取用户
     * @param deptId
     * @param pageQuery
     * @return
     */
    PageResult<SysUserPo> getPageByDeptId(int deptId, PageQuery pageQuery);

    /**
     * 获取全部用户列表
     * @return
     */
    List<SysUserPo> getAll();

}
