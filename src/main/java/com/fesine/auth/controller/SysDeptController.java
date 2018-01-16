package com.fesine.auth.controller;

import com.fesine.auth.common.JsonData;
import com.fesine.auth.dto.DeptLevelDto;
import com.fesine.auth.param.DeptParam;
import com.fesine.auth.service.SysDeptService;
import com.fesine.auth.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @description: 部门管理控制类
 * @author: Fesine
 * @createTime:2018/1/12
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/1/12
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService treeService;

    @RequestMapping("dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept");
    }

    /**
     * 新增部门
     * @param param
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }

    /**
     * 获取部门树
     * @return
     */
    @RequestMapping("tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = treeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门
     *
     * @param param
     * @return
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }


    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public JsonData deleteDept(@RequestParam Integer id) {
        sysDeptService.delete(id);
        return JsonData.success();
    }


}
