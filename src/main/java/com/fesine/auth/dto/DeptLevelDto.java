package com.fesine.auth.dto;

import com.fesine.auth.po.SysDeptPo;
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
public class DeptLevelDto extends SysDeptPo {
    private static final long serialVersionUID = -3426062199087962378L;

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    /**
     * 将po转成dto使用
     * @param deptPo
     * @return
     */
    public static DeptLevelDto adapt(SysDeptPo deptPo) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(deptPo, dto);
        return dto;
    }
}
