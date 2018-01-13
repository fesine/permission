package com.fesine.auth.controller;

import com.fesine.auth.beans.PageQuery;
import com.fesine.auth.beans.PageResult;
import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.UserParam;
import com.fesine.auth.po.SysUserPo;
import com.fesine.auth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 类描述
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @RequestMapping("/user.page")
    public ModelAndView page() {
        return new ModelAndView("user");
    }

    /**
     * 新增用户
     *
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        userService.save(param);
        return JsonData.success();
    }


    /**
     * 更新用户
     *
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        userService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam int deptId, PageQuery pageQuery) {
        PageResult<SysUserPo> page = userService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(page);
    }
}
