package com.fesine.auth.service.impl;

import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.dto.AclModuleLevelDto;
import com.fesine.auth.dto.DeptLevelDto;
import com.fesine.auth.po.SysAclModulePo;
import com.fesine.auth.po.SysDeptPo;
import com.fesine.auth.service.SysTreeService;
import com.fesine.auth.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description: 将部门信息转换成树结构
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {
    @Autowired
    private IDaoService daoService;

    @Override
    public List<DeptLevelDto> deptTree() {
        //获取所有部门信息
        List<SysDeptPo> deptPoList = daoService.selectList(new SysDeptPo());
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDeptPo sysDeptPo : deptPoList) {
            DeptLevelDto dto = DeptLevelDto.adapt(sysDeptPo);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        //获取所有权限模块信息
        List<SysAclModulePo> aclModulePoList = daoService.selectList(new SysAclModulePo());
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModulePo sysAclModulePo : aclModulePoList) {
            AclModuleLevelDto dto = AclModuleLevelDto.adapt(sysAclModulePo);
            dtoList.add(dto);
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelList){
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return Lists.newArrayList();
        }
        //level --> List结构[aclModule1,aclModule2]
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            //相同level值，会将value转换成list
            levelAclModuleMap.put(dto.getLevel(), dto);
            //如果是顶级权限模块，加入到列表
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //根权限模块按seq进行从小到大排序
        Collections.sort(rootList, aclModuleSeqComparator);
        //生成树操作
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList){
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        //level --> List结构[dept1,dept2]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : deptLevelList) {
            //相同level值，会将value转换成list
            levelDeptMap.put(dto.getLevel(), dto);
            //如果是顶级部门，加入到列表
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //根部门按seq进行从小到大排序
        Collections.sort(rootList, deptSeqComparator);
        //生成树操作
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> aclModuleLevelList, String level,
                                  Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < aclModuleLevelList.size(); i++) {
            //遍历每层部门数据
            AclModuleLevelDto aclModuleLevelDto = aclModuleLevelList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, aclModuleLevelDto.getId());
            //处理下一层数据
            List<AclModuleLevelDto> tempAclModuleList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempAclModuleList)) {
                //排序
                Collections.sort(tempAclModuleList, aclModuleSeqComparator);
                aclModuleLevelDto.setAclModuleList(tempAclModuleList);
                //递归处理
                transformAclModuleTree(tempAclModuleList, nextLevel, levelAclModuleMap);
            }
        }
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level,
                                  Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            //遍历每层部门数据
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层数据
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                deptLevelDto.setDeptList(tempDeptList);
                //递归处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    /**
     * lambada表达式实现基本排序
     */

    Comparator<DeptLevelDto> deptSeqComparator = Comparator.comparing(DeptLevelDto::getSeq);
    Comparator<AclModuleLevelDto> aclModuleSeqComparator = Comparator.comparing(AclModuleLevelDto::getSeq);

            //(o1, o2) -> o1.getSeq().compareTo(o2.getSeq());
    //        new Comparator<DeptLevelDto>() {
    //    @Override
    //    public int compare(DeptLevelDto o1, DeptLevelDto o2) {
    //        return o1.getSeq() - o2.getSeq();
    //    }
    //};
}
