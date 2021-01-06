package com.alan.admin.system.controller;

import com.alan.admin.system.validator.CourseValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Course;
import com.alan.modules.system.service.CourseService;
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
 * @date 2021/01/06
 */
@Controller
@RequestMapping("/system/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:course:index")
    public String index(Model model, Course course) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("names", match -> match.contains());

        // 获取数据列表
        Example<Course> example = Example.of(course, matcher);
        Page<Course> list = courseService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/course/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:course:add")
    public String toAdd() {
        return "/system/course/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:course:edit")
    public String toEdit(@PathVariable("id") Course course, Model model) {
        model.addAttribute("course", course);
        return "/system/course/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:course:add", "system:course:edit"})
    @ResponseBody
    public ResultVo save(@Validated CourseValid valid, Course course) {
        // 复制保留无需修改的数据
        if (course.getId() != null) {
            Course beCourse = courseService.getById(course.getId());
            EntityBeanUtil.copyProperties(beCourse, course);
        }

        // 保存数据
        courseService.save(course);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:course:detail")
    public String toDetail(@PathVariable("id") Course course, Model model) {
        model.addAttribute("course",course);
        return "/system/course/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:course:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (courseService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}