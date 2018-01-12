package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.param.UserParam;
import com.fesine.auth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("user.page")
    public ModelAndView page() {
        return new ModelAndView("user");
    }

    /**
     * 新增部门
     *
     * @param param
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveDept(UserParam param) {
        userService.save(param);
        return JsonData.success();
    }


    /**
     * 更新部门
     *
     * @param param
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateDept(UserParam param) {
        userService.update(param);
        return JsonData.success();
    }
}
