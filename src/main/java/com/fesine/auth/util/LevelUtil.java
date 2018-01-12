package com.fesine.auth.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @description: 层级计算工具类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
public class LevelUtil {

    public static final String SEPARATOR=".";
    public static final String ROOT="0";

    /**
     //0
     //0.1
     //0.1.1
     //0.1.2
     //0.2
     //0.2.1
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel,int parentId){
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        }
        return StringUtils.join(parentLevel, SEPARATOR, parentId);
    }
}
