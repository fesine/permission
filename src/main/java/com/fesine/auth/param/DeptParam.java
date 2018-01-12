package com.fesine.auth.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @description: 部门参数类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Getter
@Setter
@ToString
public class DeptParam {
    /**
     * 部门id       db_column: id
     */
    private Integer id;
    /**
     * 部门名称       db_column: name
     */
    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15,min = 2,message = "部门名称长度需要在2-15个字之间")
    private String name;

    /**
     * 上级部门id       db_column: parent_id
     */
    private Integer parentId = 0;
    /**
     * 部门层级       db_column: level
     */
    private String level;
    /**
     * 部门在当前层级下的排序，由小到大       db_column: seq
     */
    @NotNull(message = "展示顺序不能为空")
    private Integer seq;
    /**
     * 备注       db_column: remark
     */
    @Length(max = 150,message = "备注长度需要在150个字以内")
    private String remark;

}
