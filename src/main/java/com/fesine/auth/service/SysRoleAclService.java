package com.fesine.auth.service;

import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/15
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/15
 */
public interface SysRoleAclService {

    void changeRoleAcls(Integer roleId,List<Integer> aclIdList);
}
