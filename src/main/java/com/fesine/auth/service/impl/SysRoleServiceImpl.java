package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.RoleParam;
import com.fesine.auth.po.*;
import com.fesine.auth.service.SysRoleService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.IpUtil;
import com.fesine.dao.model.Order;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    private boolean checkExist(String name, Integer id) {
        SysRolePo checkPo = new SysRolePo();
        checkPo.setName(name);
        checkPo.setId(id);
        return daoService.count(SysRolePo.class.getName() + ".countByName", checkPo) > 0;
    }
}
