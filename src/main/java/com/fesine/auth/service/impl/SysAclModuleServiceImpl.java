package com.fesine.auth.service.impl;

import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.AclModuleParam;
import com.fesine.auth.po.SysAclModulePo;
import com.fesine.auth.service.SysAclModuleService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.IpUtil;
import com.fesine.auth.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {
    @Autowired
    private IDaoService daoService;
    @Override
    public void save(AclModuleParam param) {
        //检查参数
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModulePo sysAclModulePo = SysAclModulePo.builder().name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark())
                .status(param.getStatus()).build();
        //计算level
        sysAclModulePo.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param
                .getParentId()));
        sysAclModulePo.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModulePo.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModulePo.setOperateTime(new Date());
        daoService.insert(sysAclModulePo);

    }

    @Override
    public void update(AclModuleParam param) {
        //检查参数
        BeanValidator.check(param);
        //获取更新前的部门信息
        SysAclModulePo before = new SysAclModulePo();
        before.setId(param.getId());
        before = daoService.selectOne(before);
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");
        //检查部门名称
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        //创建对象
        SysAclModulePo after = SysAclModulePo.builder().id(param.getId()).name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark())
                .status(param.getStatus()).build();
        //计算level
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId
                ()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        //执行更新操作
        updateWithChild(before, after);

    }

    @Transactional(rollbackFor = Exception.class)
    void updateWithChild(SysAclModulePo before, SysAclModulePo after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            //获取当前部门的子部门
            SysAclModulePo childModule = new SysAclModulePo();
            if (oldLevelPrefix == null) {
                childModule.setLevel(".%");
            } else {
                childModule.setLevel(oldLevelPrefix.concat(".%"));
            }
            List<SysAclModulePo> childList = daoService.selectList(childModule);
            if (CollectionUtils.isNotEmpty(childList)) {
                for (SysAclModulePo modulePo : childList) {
                    String level = modulePo.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        //重新拼接子部门level
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        modulePo.setLevel(level);
                        daoService.update(modulePo);
                    }
                }
            }
        }
        //最后更新当前部门信息
        daoService.update(after);
    }

    /**
     * 检查同一层级下是否有重复的权限模块
     *
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    private boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId) {
        SysAclModulePo checkPo = new SysAclModulePo();
        checkPo.setParentId(parentId);
        checkPo.setName(aclModuleName);
        checkPo.setId(aclModuleId);
        return daoService.count(SysAclModulePo.class.getName() + ".countByNameAndParentId", checkPo) > 0;
    }

    /**
     * 根据权限模块id获取权限模块层级
     *
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {
        SysAclModulePo sysAclModulePo = new SysAclModulePo();
        sysAclModulePo.setId(aclModuleId);
        sysAclModulePo = daoService.selectOne(sysAclModulePo);
        if (sysAclModulePo == null) {
            return null;
        }
        return sysAclModulePo.getLevel();
    }
}
