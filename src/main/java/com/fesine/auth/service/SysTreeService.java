package com.fesine.auth.service;

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
}
