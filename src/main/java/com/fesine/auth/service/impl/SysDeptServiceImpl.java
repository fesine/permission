package com.fesine.auth.service.impl;

import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.exception.ParamException;
import com.fesine.auth.param.DeptParam;
import com.fesine.auth.po.SysDeptPo;
import com.fesine.auth.service.SysDeptService;
import com.fesine.auth.util.BeanValidator;
import com.fesine.auth.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description: 部门管理服务实现类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private IDaoService daoService;

    @Override
    public void save(DeptParam param) {
        //检查参数
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        //创建对象
        SysDeptPo sysDeptPo = SysDeptPo.builder().name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark())
                .build();
        //计算level
        sysDeptPo.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //TODO
        sysDeptPo.setOperator("system");
        sysDeptPo.setOperateIp("127.0.0.1");
        sysDeptPo.setOperateTime(new Date());
        daoService.insert(sysDeptPo);

    }

    @Override
    public void update(DeptParam param) {
        //检查参数
        BeanValidator.check(param);
        //获取更新前的部门信息
        SysDeptPo before = new SysDeptPo();
        before.setId(param.getId());
        before = daoService.selectOne(before);
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        //检查部门名称
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        //创建对象
        SysDeptPo after = SysDeptPo.builder().id(param.getId()).name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark())
                .build();
        //计算level
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator("system-update");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());
        //执行更新操作
        updateWithChild(before, after);
    }

    @Transactional(rollbackFor = Exception.class)
    void updateWithChild(SysDeptPo before, SysDeptPo after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            //获取当前部门的子部门
            SysDeptPo childDept = new SysDeptPo();
            if (oldLevelPrefix == null) {
                childDept.setLevel(".%");
            } else {
                childDept.setLevel(oldLevelPrefix.concat(".%"));
            }
            List<SysDeptPo> childList = daoService.selectList(childDept);
            if (CollectionUtils.isNotEmpty(childList)) {
                for (SysDeptPo deptPo : childList) {
                    String level = deptPo.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        //重新拼接子部门level
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        deptPo.setLevel(level);
                        daoService.update(deptPo);
                    }
                }
            }
        }
        //最后更新当前部门信息
        daoService.update(after);
    }

    /**
     * 检查同一层级下是否有重复的部门
     *
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        SysDeptPo checkPo = new SysDeptPo();
        checkPo.setParentId(parentId);
        checkPo.setName(deptName);
        checkPo.setId(deptId);
        return daoService.count(SysDeptPo.class.getName() + ".countByNameAndParentId", checkPo) > 0;

    }


    /**
     * 根据部门id获取部门层级
     *
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDeptPo sysDeptPo = new SysDeptPo();
        sysDeptPo.setId(deptId);
        sysDeptPo = daoService.selectOne(sysDeptPo);
        if (sysDeptPo == null) {
            return null;
        }
        return sysDeptPo.getLevel();
    }
}
