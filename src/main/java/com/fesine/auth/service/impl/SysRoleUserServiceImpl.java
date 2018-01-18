package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.po.SysRoleUserPo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysLogService;
import com.fesine.auth.service.SysRoleUserService;
import com.fesine.auth.util.IpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/16
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/16
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private IDaoService daoService;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public List<SysUserPo> getListByRoleId(Integer roleId) {
        SysRoleUserPo sysRoleUserPo = new SysRoleUserPo();
        sysRoleUserPo.setRoleId(roleId);
        List<SysRoleUserPo> roleUserPoList = daoService.selectList(sysRoleUserPo);
        if (CollectionUtils.isEmpty(roleUserPoList)) {
            return Lists.newArrayList();
        }
        List<Integer> userIdList = Lists.newArrayList();
        for (SysRoleUserPo roleUserPo : roleUserPoList) {
            userIdList.add(roleUserPo.getUserId());
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("idList", userIdList);
        return daoService.selectList(SysUserPo.class.getName() + ".getByIdList", paramMap);
    }

    @Override
    public void changeRoleUsers(Integer roleId, List<Integer> userIdList) {
        //获取原来权限点数据
        SysRoleUserPo roleUserPo = new SysRoleUserPo();
        roleUserPo.setRoleId(roleId);
        List<SysRoleUserPo> roleUserPoList = daoService.selectList(roleUserPo);
        List<Integer> originUserIdList = roleUserPoList.stream().map(temPo ->
                temPo.getUserId()).collect(Collectors.toList());
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (originUserIdSet.isEmpty()) {
                return;
            }
        }
        updateRoleUsers(roleId, userIdList);
        sysLogService.saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRoleUsers(Integer roleId, List<Integer> userIdList) {
        //删除旧数据
        SysRoleUserPo sysRoleUserPo = SysRoleUserPo.builder().roleId(roleId).build();
        daoService.delete(sysRoleUserPo);
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUserPo> userPoList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUserPo roleUserPo = SysRoleUserPo.builder().roleId(roleId).userId(userId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date()).build();
            userPoList.add(roleUserPo);
        }
        //批量插入
        daoService.insert(userPoList);
    }
}
