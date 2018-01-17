package com.fesine.auth.service.impl;

import com.fesine.auth.beans.CacheKeyConstants;
import com.fesine.auth.common.RequestHolder;
import com.fesine.auth.dao.IDaoService;
import com.fesine.auth.po.SysAclPo;
import com.fesine.auth.po.SysRoleAclPo;
import com.fesine.auth.po.SysRoleUserPo;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysCacheService;
import com.fesine.auth.service.SysCoreService;
import com.fesine.auth.util.JsonMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private IDaoService daoService;

    @Autowired
    private SysCacheService cacheService;



    @Override
    public List<SysAclPo> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    @Override
    public List<SysAclPo> getUserAclList(int userId) {
        if (isSuperAdmin(userId)) {
            return daoService.selectList(new SysAclPo());
        }
        SysRoleUserPo sysRoleUserPo = new SysRoleUserPo();
        sysRoleUserPo.setUserId(userId);
        List<SysRoleUserPo> sysRoleUserPoList = daoService.selectList(sysRoleUserPo);
        //根据userId获取roleIdList
        List<Integer> roleIdList = sysRoleUserPoList.stream().map(roleUserPo -> roleUserPo
                .getRoleId()).collect(Collectors.toList());
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("roleIdList", roleIdList);
        //List<Integer> getRoleIdListByUserId(userId)
        //根据roleIdList获取AclIdList
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(SysRoleAclPo.class.getName() +
                ".getAclIdListByRoleIdList", paraMap);
        if (CollectionUtils.isEmpty(roleAclPoList)) {
            return Lists.newArrayList();
        }
        //List<SysRoleAclPo> getAclIdListByRoleIdList(roleIdList)
        List<Integer> aclIdList = roleAclPoList.stream().map(roleAclPo -> roleAclPo.getAclId())
                .collect(Collectors.toList());
        //List<SysAclPo> getByIdList(aclIdList)
        paraMap = new HashMap<>();
        paraMap.put("idList", aclIdList);
        return getSysAclPoList(paraMap);
    }

    @Override
    public List<SysAclPo> getRoleAclList(int roleId) {
        SysRoleAclPo tempPo = new SysRoleAclPo();
        tempPo.setRoleId(roleId);
        List<SysRoleAclPo> roleAclPoList = daoService.selectList(tempPo);
        if (CollectionUtils.isEmpty(roleAclPoList)) {
            return Lists.newArrayList();
        }
        List<Integer> aclIdList = roleAclPoList.stream().map(roleAclPo -> roleAclPo.getAclId())
                .collect(Collectors.toList());
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("idList", aclIdList);
        return getSysAclPoList(paraMap);
    }

    @Override
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin(RequestHolder.getCurrentUser().getId())) {
            return true;
        }
        //根据url取出对应的acl
        SysAclPo aclPo = SysAclPo.builder().url(url).build();
        List<SysAclPo> aclPoList = daoService.selectList(SysAclPo.class.getName() + ".getByUrl",
                aclPo);
        if (CollectionUtils.isEmpty(aclPoList)) {
            return true;
        }
        List<SysAclPo> userAclList;
        userAclList = getCurrentUserAclListFromCache();
        //超级用户直接访问
        Set<Integer> userAclIdSet = userAclList.stream().map(aclPoTemp -> aclPoTemp.getId()).collect
                (Collectors.toSet());
        //只要有一个权限点有权限，就可以访问
        boolean hasValidAcl = false;
        for (SysAclPo sysAclPo : aclPoList) {
            if (sysAclPo == null || sysAclPo.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(sysAclPo.getId())) {
                return true;
            }
        }
        if(!hasValidAcl){
            return true;
        }
        return false;
    }

    private List<SysAclPo> getSysAclPoList(Map<String, Object> paraMap) {
        if (paraMap.isEmpty()) {
            return Lists.newArrayList();
        }
        List<SysAclPo> sysAclPoList = daoService.selectList(SysAclPo.class.getName() +
                ".getByIdList", paraMap);
        if (CollectionUtils.isEmpty(sysAclPoList)) {
            return Lists.newArrayList();
        }
        return sysAclPoList;
    }

    /**
     * 判断是否是超级管理员
     * @return
     */
    public boolean isSuperAdmin(Integer userId) {
        SysUserPo sysUserPo = SysUserPo.builder().id(userId).build();
        sysUserPo = daoService.selectOne(sysUserPo);
        if(sysUserPo.getEmail().contains("admin")){
            return true;
        }
        return false;
    }

    List<SysAclPo> getCurrentUserAclListFromCache(){
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = cacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf
                (userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAclPo> aclPoList = getCurrentUserAclList();
            if(CollectionUtils.isNotEmpty(aclPoList)){
                cacheService.saveCache(JsonMapper.obj2String(aclPoList),600, CacheKeyConstants.USER_ACLS, String.valueOf
                        (userId));
            }
            return aclPoList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAclPo>>() {});
    }


}
