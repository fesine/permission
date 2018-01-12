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
 * @description: 用户参数类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Getter
@Setter
@ToString
public class UserParam {

    /**
     * 用户id       db_column: id
     */
    private Integer id;
    /**
     * 用户姓名       db_column: username
     */
    @NotBlank(message = "用户名不允许为空")
    @Length(min = 2,max = 20,message = "用户名长度在2-20个字以内")
    private String username;
    /**
     * 手机号码       db_column: telephone
     */
    @NotBlank(message = "手机号码不允许为空")
    @Length(min = 2, max = 13, message = "手机号码长度在2-13个字以内")
    private String telephone;
    /**
     * 用户邮箱       db_column: email
     */
    @NotBlank(message = "邮箱不允许为空")
    @Length(min = 5, max = 50, message = "邮箱长度在5-50个字以内")
    private String email;
    /**
     * 用户所在部门id       db_column: dept_id
     */
    @NotNull(message = "必须指定用户所在部门")
    private Integer deptId;
    /**
     * 用户状态 0:冻结，1:有效，2:删除       db_column: status
     */
    @NotNull(message = "必须指定用户状态")
    @Min(value = 0,message = "用户状态不合法")
    @Max(value = 2,message = "用户状态不合法")
    private Integer status;
    /**
     * 备注       db_column: remark
     */
    @Length(max = 200,message = "备注最大长度不能超过200")
    private String remark;
}
