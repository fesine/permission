package com.fesine.auth.dto;

import com.fesine.auth.po.SysAclPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/15
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/15
 */
@Getter
@Setter
@ToString
public class AclDto extends SysAclPo{
    private static final long serialVersionUID = -1081702994600931238L;

    /**
     * 是否默认选中
     */
    private boolean checked = false;

    /**
     * 是否有权限操作
     */
    private boolean hasAcl = false;

    /**
     * 将po转成dto使用
     *
     * @param aclPo
     * @return
     */
    public static AclDto adapt(SysAclPo aclPo) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(aclPo, dto);
        return dto;
    }
}
