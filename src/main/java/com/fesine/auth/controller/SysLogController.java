package com.fesine.auth.controller;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.SearchLogParam;
import com.fesine.auth.po.SysLogPo;
import com.fesine.auth.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/18
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/18
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("log.page")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchLog(SearchLogParam searchLogParam, PageQuery pageQuery) {
        PageResult<SysLogPo> result = sysLogService.searchPageList(searchLogParam,
                pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam int id){
        sysLogService.recover(id);
        return JsonData.success();
    }
}

