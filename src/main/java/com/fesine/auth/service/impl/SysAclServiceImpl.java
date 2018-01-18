package com.fesine.auth.service.impl;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.AclParam;
import com.fesine.auth.po.SysAclPo;
import com.fesine.auth.service.SysAclService;
import com.fesine.auth.service.SysLogService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.IpUtil;
import com.fesine.dao.model.Order;
import com.fesine.dao.model.QueryResult;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/14
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/14
 */
@Service
public class SysAclServiceImpl implements SysAclService {
    @Autowired
    private IDaoService daoService;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(),param.getName(), param.getId())) {
            throw new ParamException("同一层级下权限点名称已经存在");
        }
        SysAclPo sysAclPo = SysAclPo.builder().name(param.getName()).aclModuleId(param
                .getAclModuleId()).url(param.getUrl()).type(param.getType())
                .status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        sysAclPo.setCode(generatorCode());
        sysAclPo.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclPo.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclPo.setOperateTime(new Date());
        daoService.insert(sysAclPo);
        sysLogService.saveAclLog(null, sysAclPo);
    }

    @Override
    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下权限点名称已经存在");
        }
        SysAclPo before = new SysAclPo();
        before.setId(param.getId());
        before = daoService.selectOne(before);
        Preconditions.checkNotNull(before, "待更新的权限点不存在");
        SysAclPo after = SysAclPo.builder().id(param.getId()).name(param.getName()).aclModuleId(param
                .getAclModuleId()).url(param.getUrl()).type(param.getType())
                .status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        daoService.update(after);
        sysLogService.saveAclLog(before, after);
    }

    @Override
    public PageResult<SysAclPo> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        SysAclPo sysAclPo = new SysAclPo();
        sysAclPo.setAclModuleId(aclModuleId);
        sysAclPo.addOrderBy(Order.asc("seq"));
        sysAclPo.addOrderBy(Order.asc("name"));
        QueryResult<SysAclPo> queryResult = daoService.selectQueryResult(sysAclPo,
                pageQuery.getPageNo(), pageQuery.getPageSize());
        PageResult<SysAclPo> pageResult = PageResult.<SysAclPo>builder().total(queryResult
                .getTotalRecord()).data(queryResult.getResultList()).build();
        return pageResult;
    }

    /**
     * 检查同一模块下面是否存在相同名称的权限点
     * @param aclModuleId
     * @param aclName
     * @param aclId
     * @return
     */
    private boolean checkExist(Integer aclModuleId, String aclName, Integer aclId) {
        SysAclPo checkPo = new SysAclPo();
        checkPo.setAclModuleId(aclModuleId);
        checkPo.setName(aclName);
        checkPo.setId(aclId);
        return daoService.count(SysAclPo.class.getName() + ".countByNameAndAclModuleId", checkPo) > 0;

    }

    private String generatorCode(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date())+"_"+ new Random().nextInt(2);
    }
}
