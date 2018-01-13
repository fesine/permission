package com.fesine.auth.service.impl;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.UserParam;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysUserService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.MD5Util;
import com.fesine.auth.util.PasswordUtil;
import com.fesine.dao.model.QueryResult;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description: 用户管理实现类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private IDaoService daoService;

    @Override
    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getEmail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        String password = PasswordUtil.randomPassword();
        password = "123456";
        password = MD5Util.encrypt(password);
        SysUserPo userPo = SysUserPo.builder().username(param.getUsername()).telephone(param
                .getTelephone()).email(param.getEmail()).deptId(param.getDeptId()).remark(param
                .getRemark()).password(password).status(param.getStatus()).build();
        userPo.setOperator(RequestHolder.getCurrentUser().getUsername());
        userPo.setOperateIp("127.0.0.1");
        userPo.setOperateTime(new Date());
        //TODO 发送邮件
        daoService.insert(userPo);

    }

    @Override
    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getEmail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUserPo before = new SysUserPo();
        before.setId(param.getId());
        before = daoService.selectOne(before);
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUserPo userPo = SysUserPo.builder().id(param.getId()).username(param.getUsername())
                .telephone(param
                        .getTelephone()).email(param.getEmail()).deptId(param.getDeptId()).remark
                        (param
                        .getRemark()).status(param.getStatus()).build();
        userPo.setOperator(RequestHolder.getCurrentUser().getUsername());
        userPo.setOperateIp("127.0.0.1");
        userPo.setOperateTime(new Date());
        daoService.update(userPo);
    }

    @Override
    public SysUserPo findByKeyword(String keyword) {
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setUsername(keyword);
        return daoService.selectOne(SysUserPo.class.getName() + ".findByKeyword", sysUserPo);
    }

    @Override
    public PageResult<SysUserPo> getPageByDeptId(int deptId, PageQuery pageQuery) {
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setDeptId(deptId);
        QueryResult<SysUserPo> queryResult = daoService.selectQueryResult(sysUserPo,
                pageQuery.getPageNo(), pageQuery.getPageSize());
        PageResult<SysUserPo> pageResult = PageResult.<SysUserPo>builder().total(queryResult
                .getTotalRecord()).data(queryResult.getResultList()).build();
        return pageResult;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId) {
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setTelephone(telephone);
        sysUserPo.setId(userId);
        return daoService.count(SysUserPo.class.getName() + ".countByEmailOrTelephone", sysUserPo)
                > 0;
    }

    public boolean checkEmailExist(String email, Integer userId) {
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setEmail(email);
        sysUserPo.setId(userId);
        return daoService.count(SysUserPo.class.getName() + ".countByEmailOrTelephone", sysUserPo)
                > 0;
    }


}
