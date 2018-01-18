package com.fesine.auth.service.impl;

import com.fesine.auth.beans.LogType;
import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.SearchLogParam;
import com.fesine.auth.po.*;
import com.fesine.auth.service.SysLogService;
import com.fesine.auth.service.SysRoleAclService;
import com.fesine.auth.service.SysRoleUserService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.IpUtil;
import com.fesine.auth.util.JsonMapper;
import com.fesine.dao.model.QueryResult;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/18
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/18
 */
@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private IDaoService daoService;

    @Autowired
    private SysRoleAclService roleAclService;

    @Autowired
    private SysRoleUserService roleUserService;

    @Override
    public void recover(int id) {
        SysLogPo sysLogPo = SysLogPo.builder().id(id).build();
        sysLogPo = daoService.selectOne(sysLogPo);
        Preconditions.checkNotNull(sysLogPo, "待还原的记录不存在!");
        if (StringUtils.isBlank(sysLogPo.getOldValue()) || StringUtils.isBlank(sysLogPo
                .getNewValue())) {
            throw new ParamException("新增和删除不可以进行还原操作");
        }
        switch (sysLogPo.getType()) {
            case LogType.TYPE_DEPT:
                SysDeptPo beforeDept = SysDeptPo.builder().id(sysLogPo.getTargetId()).build();
                beforeDept = daoService.selectOne(beforeDept);
                Preconditions.checkNotNull(beforeDept, "待还原的部门不存在!");
                SysDeptPo afterDept = JsonMapper.string2Obj(sysLogPo.getOldValue(), new
                        TypeReference<SysDeptPo>() {
                        });
                afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterDept.setOperateTime(new Date());
                daoService.update(afterDept);
                this.saveDeptLog(beforeDept, afterDept);
                break;
            case LogType.TYPE_USER:
                SysUserPo beforeUser = SysUserPo.builder().id(sysLogPo.getTargetId()).build();
                beforeUser = daoService.selectOne(beforeUser);
                Preconditions.checkNotNull(beforeUser, "待还原的用户不存在!");
                SysUserPo afterUser = JsonMapper.string2Obj(sysLogPo.getOldValue(), new
                        TypeReference<SysUserPo>() {
                        });
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperateTime(new Date());
                daoService.update(afterUser);
                this.saveUserLog(beforeUser, afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModulePo beforeAclModule = SysAclModulePo.builder().id(sysLogPo.getTargetId
                        ()).build();
                beforeAclModule = daoService.selectOne(beforeAclModule);
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块不存在!");
                SysAclModulePo afterAclModule = JsonMapper.string2Obj(sysLogPo.getOldValue(), new
                        TypeReference<SysAclModulePo>() {
                        });
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperateTime(new Date());
                daoService.update(afterAclModule);
                this.saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAclPo beforeAcl = SysAclPo.builder().id(sysLogPo.getTargetId()).build();
                beforeAcl = daoService.selectOne(beforeAcl);
                Preconditions.checkNotNull(beforeAcl, "待还原的权限点不存在!");
                SysAclPo afterAcl = JsonMapper.string2Obj(sysLogPo.getOldValue(), new
                        TypeReference<SysAclPo>() {
                        });
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperateTime(new Date());
                daoService.update(afterAcl);
                this.saveAclLog(beforeAcl, afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRolePo beforeRole = SysRolePo.builder().id(sysLogPo.getTargetId()).build();
                beforeRole = daoService.selectOne(beforeRole);
                Preconditions.checkNotNull(beforeRole, "待还原的角色不存在!");
                SysRolePo afterRole= JsonMapper.string2Obj(sysLogPo.getOldValue(), new
                        TypeReference<SysRolePo>() {
                        });
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperateTime(new Date());
                daoService.update(afterRole);
                this.saveRoleLog(beforeRole, afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRolePo aclRole = SysRolePo.builder().id(sysLogPo.getTargetId()).build();
                aclRole = daoService.selectOne(aclRole);
                Preconditions.checkNotNull(aclRole, "待还原的角色不存在!");
                roleAclService.changeRoleAcls(sysLogPo.getTargetId(), JsonMapper.string2Obj
                        (sysLogPo.getOldValue(), new TypeReference<List<Integer>>() {
                        }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRolePo userRole = SysRolePo.builder().id(sysLogPo.getTargetId()).build();
                userRole = daoService.selectOne(userRole);
                Preconditions.checkNotNull(userRole, "待还原的角色不存在!");
                roleUserService.changeRoleUsers(sysLogPo.getTargetId(), JsonMapper.string2Obj
                        (sysLogPo.getOldValue(), new TypeReference<List<Integer>>() {
                        }));
                break;
            default:
                break;
        }
    }


    @Override
    public void saveDeptLog(SysDeptPo before, SysDeptPo after) {
        saveLog(after == null ? before.getId() : after.getId(), LogType.TYPE_DEPT, before,
                after);
    }

    @Override
    public void saveUserLog(SysUserPo before, SysUserPo after) {
        saveLog(after == null ? before.getId() : after.getId(), LogType.TYPE_USER, before,
                after);
    }

    @Override
    public void saveAclModuleLog(SysAclModulePo before, SysAclModulePo after) {
        saveLog(after == null ? before.getId() : after.getId(), LogType.TYPE_ACL_MODULE, before,
                after);
    }

    @Override
    public void saveAclLog(SysAclPo before, SysAclPo after) {
        saveLog(after == null ? before.getId() : after.getId(), LogType.TYPE_ACL, before, after);
    }

    @Override
    public void saveRoleLog(SysRolePo before, SysRolePo after) {
        saveLog(after == null ? before.getId() : after.getId(), LogType.TYPE_ROLE, before, after);
    }

    @Override
    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        saveLog(roleId, LogType.TYPE_ROLE_ACL, before, after);
    }

    @Override
    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        saveLog(roleId, LogType.TYPE_ROLE_USER, before, after);
    }

    @Override
    public PageResult<SysLogPo> searchPageList(SearchLogParam param, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("type", param.getType());
        if (StringUtils.isNotBlank(param.getBeforeSeg())) {
            paramMap.put("oldValue", "%" + param.getBeforeSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getAfterSeg())) {
            paramMap.put("newValue", "%" + param.getAfterSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getOperator())) {
            paramMap.put("operator", "%" + param.getOperator() + "%");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())) {
                paramMap.put("fromTime", sdf.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())) {
                paramMap.put("toTime", sdf.parse(param.getToTime()));
            }
        } catch (ParseException e) {
            throw new ParamException("传入日期格式有问题，正确格式为yyyy-MM-dd HH:mm:ss");
        }
        QueryResult<SysLogPo> qr = daoService.selectQueryResult(SysLogPo.class
                .getName() + ".searchLog", paramMap, pageQuery
                .getPageNo(), pageQuery.getPageSize());
        PageResult<SysLogPo> pr = PageResult.<SysLogPo>builder().total(qr.getTotalRecord()).data
                (qr.getResultList()).build();
        return pr;
    }

    private void saveLog(int targetId, Integer type, Object before, Object after) {
        SysLogPo logPo = new SysLogPo();
        logPo.setType(type);
        logPo.setTargetId(targetId);
        logPo.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        logPo.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        logPo.setOperator(RequestHolder.getCurrentUser().getUsername());
        logPo.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        logPo.setOperateTime(new Date());
        logPo.setStatus(1);
        daoService.insert(logPo);
    }
}
