package com.fesine.auth.service;

import com.fesine.auth.param.DeptParam;

/**
 * @description: 部门管理服务接口
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public interface SysDeptService {

    /**
     * 保存部门信息
     * @param param
     */
    void save(DeptParam param);

    /**
     * 更新部门信息
     * @param param
     */
    void update(DeptParam param);
}
