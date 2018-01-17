package com.fesine.auth.service;

import com.fesine.auth.beans.CacheKeyConstants;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/17
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/17
 */
public interface SysCacheService {

    void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix);

    void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix,String... keys);

    String getFromCache(CacheKeyConstants prefix, String... keys);

}
