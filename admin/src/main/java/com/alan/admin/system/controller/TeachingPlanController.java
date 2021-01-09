package com.alan.admin.system.controller;

import com.alan.admin.system.validator.TeachingPlanValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Dept;
import com.alan.modules.system.domain.TeachingPlan;
import com.alan.modules.system.service.DeptService;
import com.alan.modules.system.service.TeachingPlanService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/07
 */
@Controller
@RequestMapping("/system/teachingPlan")
public class TeachingPlanController {

    @Autowired
    private TeachingPlanService teachingPlanService;

    @Autowired
    private DeptService deptService;

    /**
     * 跳转首页
     */
    @GetMapping("/index")
    @RequiresPermissions("system:teachingPlan:index")
    public String toIndex() {
        return "/system/teachingPlan/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:teachingPlan:add")
    public String toAdd(Model model, TeachingPlan teachingPlan) {
        model.addAttribute("teachingPlan", teachingPlan);
        return "/system/teachingPlan/add";
    }

    /**
     * 保存添加/修改的数据
     *
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:teachingPlan:add"})
    @ResponseBody
    public ResultVo save(@Validated TeachingPlanValid valid, TeachingPlan teachingPlan) {
        // 复制保留无需修改的数据
        if (teachingPlan.getId() != null) {
            TeachingPlan beTeachingPlan = teachingPlanService.getById(teachingPlan.getId());
            EntityBeanUtil.copyProperties(beTeachingPlan, teachingPlan);
        }

        // 保存数据
        teachingPlanService.save(teachingPlan);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 查询
     *
     * @param teachingPlan 查询对象
     */
    @PostMapping("/getList")
    @RequiresPermissions({"system:teachingPlan:getList"})
    @ResponseBody
    public ResultVo getList(TeachingPlan teachingPlan) {
        // 院系
        if (teachingPlan.getCollegeId() != null && teachingPlan.getCollegeId().getId() != null) {
            Dept dept = new Dept();
            dept.setId(teachingPlan.getCollegeId().getId());
            teachingPlan.setCollegeId(dept);
        }
        // 专业
        if (teachingPlan.getSpecialtyId() != null && teachingPlan.getSpecialtyId().getId() != null) {
            Dept dept = new Dept();
            dept.setId(teachingPlan.getSpecialtyId().getId());
            teachingPlan.setSpecialtyId(dept);
        }
        // 年级
        if (teachingPlan.getGradeId() != null && teachingPlan.getGradeId().getId() != null) {
            Dept dept = new Dept();
            dept.setId(teachingPlan.getGradeId().getId());
            teachingPlan.setGradeId(dept);
        }
        // 班级
        if (teachingPlan.getClazzId() != null && teachingPlan.getClazzId().getId() != null) {
            Dept dept = new Dept();
            dept.setId(teachingPlan.getClazzId().getId());
            teachingPlan.setClazzId(dept);
        }

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<TeachingPlan> example = Example.of(teachingPlan, matcher);
        List<TeachingPlan> list = teachingPlanService.getList(example);

        return ResultVoUtil.success("查询成功", list);
    }

    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:teachingPlan:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (teachingPlanService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}