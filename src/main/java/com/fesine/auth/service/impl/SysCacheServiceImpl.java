package com.fesine.auth.service.impl;

import com.fesine.auth.beans.CacheKeyConstants;
import com.fesine.auth.service.RedisPool;
import com.fesine.auth.service.SysCacheService;
import com.fesine.auth.util.JsonMapper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * @description: 封装redis操作方法
 * @author: Fesine
 * @createTime:2018/1/17
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/17
 */
@Service
@Slf4j
public class SysCacheServiceImpl implements SysCacheService {
    @Resource(name = "redisPool")
    private RedisPool redisPool;

    @Value("${redis.flag}")
    private boolean redisFlag;

    @Override
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSaveValue, timeoutSeconds, prefix, null);
    }

    @Override
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix,
                          String... keys) {
        if(!redisFlag){
            return;
        }
        if (toSaveValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generatorCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeoutSeconds, toSaveValue);
        } catch (Exception e) {
            log.error("save cache exception,prefix:{},keys:{}",prefix, JsonMapper.obj2String(keys));
        }finally {
            redisPool.safeClose(shardedJedis);
        }

    }

    @Override
    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        if (!redisFlag) {
            return null;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generatorCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            return shardedJedis.get(cacheKey);
        } catch (Exception e) {
            log.error("get from cache exception,prefix:{},keys:{}", prefix, JsonMapper.obj2String(keys));
            return null;
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    private String generatorCacheKey(CacheKeyConstants prefix, String... keys){
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return  key;
    }


}
