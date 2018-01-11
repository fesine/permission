package com.fesine.auth.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Json数据处理
 * @author: Fesine
 * @createTime:2018/1/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/11
 */
@Getter
@Setter
public class JsonData {
    /**
     * 处理成功或失败标识
     */
    private boolean ret;

    /**
     * 处理结果信息
     */
    private String msg;

    /**
     * 返回数据对象
     */
    private Object data;

    public JsonData(boolean ret){
        this.ret = ret;
    }

    public static JsonData success(Object object,String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg=msg;
        return jsonData;
    }

    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success(){
        return new JsonData(true);
    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String,Object> toMap(){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret",ret);
        resultMap.put("msg", msg);
        resultMap.put("data", data);
        return resultMap;
    }
}
