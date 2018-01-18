package com.fesine.auth.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/18
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/18
 */
@Getter
@Setter
@ToString
public class SearchLogParam {

    /**
     * 操作类型
     */
    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    /**
     * yyy-MM-dd HH:mm:ss
     */
    private String fromTime;

    /**
     * yyy-MM-dd HH:mm:ss
     */
    private String toTime;
}
