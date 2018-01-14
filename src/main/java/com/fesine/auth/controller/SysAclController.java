package com.fesine.auth.controller;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.AclParam;
import com.fesine.auth.po.SysAclPo;
import com.fesine.auth.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/13
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/13
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    /**
     * 新增权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveAcl(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    /**
     * 更新权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAcl(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam int aclModuleId, PageQuery pageQuery) {
        PageResult<SysAclPo> page = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(page);
    }

}
