package com.alan.admin.system.controller;

import com.alan.admin.system.validator.TeachingPlanValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.TeachingPlan;
import com.alan.modules.system.service.TeachingPlanService;
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

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:teachingPlan:index")
    public String index(Model model, TeachingPlan teachingPlan) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<TeachingPlan> example = Example.of(teachingPlan, matcher);
        Page<TeachingPlan> list = teachingPlanService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/teachingPlan/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:teachingPlan:add")
    public String toAdd() {
        return "/system/teachingPlan/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:teachingPlan:edit")
    public String toEdit(@PathVariable("id") TeachingPlan teachingPlan, Model model) {
        model.addAttribute("teachingPlan", teachingPlan);
        return "/system/teachingPlan/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:teachingPlan:add", "system:teachingPlan:edit"})
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
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:teachingPlan:detail")
    public String toDetail(@PathVariable("id") TeachingPlan teachingPlan, Model model) {
        model.addAttribute("teachingPlan",teachingPlan);
        return "/system/teachingPlan/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
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