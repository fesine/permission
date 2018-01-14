package com.fesine.auth.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
@Getter
@Setter
@ToString
public class RoleParam {

    private Integer id;
    /**
     * 权限模块名称       db_column: name
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 20, min = 2, message = "角色名称长度需要在2-20个字之间")
    private String name;

    /**
     * 类型 1:管理员 2:其他       db_column: type
     */
    @NotNull(message = "必须指定角色类型")
    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type=1;
    /**
     * 状态 0:冻结 1:有效       db_column: status
     */
    @NotNull(message = "必须指定角色状态")
    @Min(value = 0, message = "权限点角色不合法")
    @Max(value = 1, message = "权限点角色不合法")
    private Integer status;
    /**
     * 备注       db_column: remark
     */
    @Length(max = 200, message = "备注长度需要在200个字以内")
    private String remark;
}
