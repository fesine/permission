package com.fesine.auth.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
@Setter
@Getter
public class TestVo {
    @NotBlank
    private String msg;

    @NotNull(message = "id不能为空。")
    @Max(value = 10,message = "最大值不能超过10")
    @Min(value = 0,message = "最小值不能小于0")
    private Integer id;


    private List<String> list;
}
