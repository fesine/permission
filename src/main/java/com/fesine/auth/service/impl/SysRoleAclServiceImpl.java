package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.po.SysRoleAclPo;
import com.fesine.auth.service.SysLogService;
import com.fesine.auth.service.SysRoleAclService;
import com.fesine.auth.util.IpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
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
public class SysRoleAclServiceImpl implements SysRoleAclService {
    @Autowired
    private IDaoService daoService;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //获取原来权限点数据
        SysRoleAclPo roleAclPo = new SysRoleAclPo();
        roleAclPo.setRoleId(roleId);
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(roleAclPo);
        List<Integer> originAclIdList = roleAclPoList.stream().map(temPo -> temPo.getAclId())
                .collect(Collectors.toList());
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (originAclIdList.isEmpty()) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        sysLogService.saveRoleAclLog(roleId,originAclIdList,aclIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //删除旧数据
        SysRoleAclPo sysRoleAclPo = SysRoleAclPo.builder().roleId(roleId).build();
        daoService.delete(sysRoleAclPo);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAclPo> aclPoList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAclPo roleAclPo = SysRoleAclPo.builder().roleId(roleId).aclId(aclId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date()).build();
            aclPoList.add(roleAclPo);
        }
        //批量插入
        daoService.insert(aclPoList);
    }
}
