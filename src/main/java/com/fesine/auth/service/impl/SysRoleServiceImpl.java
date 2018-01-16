package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.SysRoleAclPo;
import com.fesine.auth.po.SysRolePo;
import com.fesine.auth.po.SysRoleUserPo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysRoleService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.IpUtil;
import com.fesine.dao.model.Order;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/14
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/14
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private IDaoService daoService;

    @Override
    public void save(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRolePo sysRolePo = SysRolePo.builder().name(param.getName()).type(param.getType())
                .status(param.getStatus()).remark(param.getRemark()).build();
        sysRolePo.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRolePo.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRolePo.setOperateTime(new Date());
        daoService.insert(sysRolePo);
    }

    @Override
    public void update(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRolePo before = new SysRolePo();
        before.setId(param.getId());
        before = daoService.selectOne(before);
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        SysRolePo sysRolePo = SysRolePo.builder().id(param.getId()).name(param.getName()).type
                (param.getType()).status(param.getStatus()).remark(param.getRemark()).build();
        sysRolePo.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRolePo.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRolePo.setOperateTime(new Date());
        daoService.update(sysRolePo);

    }

    @Override
    public List<SysRolePo> getAll() {
        SysRolePo sysRolePo = new SysRolePo();
        sysRolePo.addOrderBy(Order.asc("name"));
        return daoService.selectList(sysRolePo);
    }

    @Override
    public void delete(int id) {
        //检查当前角色是否存在
        SysRolePo sysRolePo = SysRolePo.builder().id(id).build();
        sysRolePo = daoService.selectOne(sysRolePo);
        Preconditions.checkNotNull(sysRolePo, "当前需要删除的角色不存在，无法删除");
        //检查是否有权限绑定
        SysRoleAclPo sysRoleAclPo = SysRoleAclPo.builder().roleId(id).build();
        List<SysRoleAclPo> sysRoleAclPoList = daoService.selectList(sysRoleAclPo);
        if (CollectionUtils.isNotEmpty(sysRoleAclPoList)) {
            throw new ParamException("当前角色下存在权限点，无法删除");
        }
        //检查当前权限模块下是否有权限点
        SysRoleUserPo sysRoleUserPo = SysRoleUserPo.builder().roleId(id).build();
        List<SysRoleUserPo> sysRoleUserPoList = daoService.selectList(sysRoleUserPo);
        if (CollectionUtils.isNotEmpty(sysRoleUserPoList)) {
            throw new ParamException("当前权限下存在用户，无法删除");
        }
        //执行删除
        sysRolePo = SysRolePo.builder().id(id).build();
        daoService.delete(sysRolePo);
    }

    @Override
    public List<SysRolePo> getRoleListByUserId(int userId) {
        SysRoleUserPo sysRoleUserPo = SysRoleUserPo.builder().userId(userId).build();
        List<SysRoleUserPo> roleUserPoList = daoService.selectList(sysRoleUserPo);
        if (CollectionUtils.isEmpty(roleUserPoList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleUserPoList.stream().map(roleUserPo -> roleUserPo.getRoleId
                ()).collect(Collectors.toList());
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("idList", roleIdList);
        return daoService.selectList(SysRolePo.class.getName() + ".getByIdList", paramMap);
    }

    @Override
    public List<SysRolePo> getRoleListByAclId(int aclId) {
        SysRoleAclPo sysRoleAclPo = SysRoleAclPo.builder().aclId(aclId).build();
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(sysRoleAclPo);
        List<Integer> roleIdList = roleAclPoList.stream().map(roleUserPo -> roleUserPo.getRoleId
                ()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("idList", roleIdList);
        return daoService.selectList(SysRolePo.class.getName() + ".getByIdList", paramMap);
    }

    @Override
    public List<SysUserPo> getUserListByRoleList(List<SysRolePo> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(sysRolePo -> sysRolePo.getId()).collect
                (Collectors.toList());
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("roleIdList",roleIdList);
        List<SysRoleUserPo> roleUserPoList = daoService.selectList(SysRoleUserPo.class.getName() +
                ".getUserIdListByRoleIdList", paramMap);
        if(CollectionUtils.isEmpty(roleUserPoList)){
            return Lists.newArrayList();
        }
        List<Integer> idList = roleUserPoList.stream().map(sysRoleUserPo -> sysRoleUserPo
                .getUserId()).collect(Collectors.toList());
        paramMap.put("idList", idList);
        return daoService.selectList(SysUserPo.class.getName()+".getByIdList", paramMap);
    }

    private boolean checkExist(String name, Integer id) {
        SysRolePo checkPo = new SysRolePo();
        checkPo.setName(name);
        checkPo.setId(id);
        return daoService.count(SysRolePo.class.getName() + ".countByName", checkPo) > 0;
    }
}
