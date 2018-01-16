package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.po.SysAclPo;
import com.fesine.auth.po.SysRoleAclPo;
import com.fesine.auth.po.SysRoleUserPo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysCoreService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/15
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/15
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private IDaoService daoService;

    @Override
    public List<SysAclPo> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    @Override
    public List<SysAclPo> getUserAclList(int userId) {
        if (isSuperAdmin(userId)) {
            return daoService.selectList(new SysAclPo());
        }
        SysRoleUserPo sysRoleUserPo = new SysRoleUserPo();
        sysRoleUserPo.setUserId(userId);
        List<SysRoleUserPo> sysRoleUserPoList = daoService.selectList(sysRoleUserPo);
        //根据userId获取roleIdList
        List<Integer> roleIdList = sysRoleUserPoList.stream().map(roleUserPo -> roleUserPo
                .getRoleId()).collect(Collectors.toList());
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("roleIdList", roleIdList);
        //List<Integer> getRoleIdListByUserId(userId)
        //根据roleIdList获取AclIdList
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(SysRoleAclPo.class.getName() +
                ".getAclIdListByRoleIdList", paraMap);
        if (CollectionUtils.isEmpty(roleAclPoList)) {
            return Lists.newArrayList();
        }
        //List<SysRoleAclPo> getAclIdListByRoleIdList(roleIdList)
        List<Integer> aclIdList = roleAclPoList.stream().map(roleAclPo -> roleAclPo.getAclId())
                .collect(Collectors.toList());
        //List<SysAclPo> getByIdList(aclIdList)
        paraMap = new HashMap<>();
        paraMap.put("idList", aclIdList);
        return getSysAclPoList(paraMap);
    }

    @Override
    public List<SysAclPo> getRoleAclList(int roleId) {
        SysRoleAclPo tempPo = new SysRoleAclPo();
        tempPo.setRoleId(roleId);
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(tempPo);
        if (CollectionUtils.isEmpty(roleAclPoList)) {
            return Lists.newArrayList();
        }
        List<Integer> aclIdList = roleAclPoList.stream().map(roleAclPo -> roleAclPo.getAclId())
                .collect(Collectors.toList());
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("idList", aclIdList);
        return getSysAclPoList(paraMap);
    }

    private List<SysAclPo> getSysAclPoList(Map<String, Object> paraMap) {
        if (paraMap.isEmpty()) {
            return Lists.newArrayList();
        }
        List<SysAclPo> sysAclPoList = daoService.selectList(SysAclPo.class.getName() +
                ".getByIdList", paraMap);
        if (CollectionUtils.isEmpty(sysAclPoList)) {
            return Lists.newArrayList();
        }
        return sysAclPoList;
    }

    /**
     * 判断是否是超级管理员
     * @return
     */
    public boolean isSuperAdmin(int userId) {
        SysUserPo sysUserPo = SysUserPo.builder().id(userId).build();
        sysUserPo = daoService.selectOne(sysUserPo);
        if(sysUserPo.getEmail().contains("admin")){
            return true;
        }
        return false;
    }


}
