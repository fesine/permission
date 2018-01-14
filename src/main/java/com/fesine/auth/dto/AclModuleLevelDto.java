package com.fesine.auth.dto;

import com.fesine.auth.po.SysAclModulePo;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @description: 部门树DTO
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModulePo {
    private static final long serialVersionUID = -3426062199087962378L;

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    /**
     * 将po转成dto使用
     * @param aclModulePo
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModulePo aclModulePo) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModulePo, dto);
        return dto;
    }
}
