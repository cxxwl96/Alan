package com.alan.admin.system.controller;

import com.alan.admin.system.validator.TeacherValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Teacher;
import com.alan.modules.system.service.TeacherService;
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
 * @date 2021/01/05
 */
@Controller
@RequestMapping("/system/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:teacher:index")
    public String index(Model model, Teacher teacher) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("names", match -> match.contains())
                .withMatcher("phone", match -> match.contains())
                .withMatcher("email", match -> match.contains());

        // 获取数据列表
        Example<Teacher> example = Example.of(teacher, matcher);
        Page<Teacher> list = teacherService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/teacher/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:teacher:add")
    public String toAdd() {
        return "/system/teacher/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:teacher:edit")
    public String toEdit(@PathVariable("id") Teacher teacher, Model model) {
        model.addAttribute("teacher", teacher);
        return "/system/teacher/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:teacher:add", "system:teacher:edit"})
    @ResponseBody
    public ResultVo save(@Validated TeacherValid valid, Teacher teacher) {
        // 复制保留无需修改的数据
        if (teacher.getId() != null) {
            Teacher beTeacher = teacherService.getById(teacher.getId());
            EntityBeanUtil.copyProperties(beTeacher, teacher);
        }

        // 保存数据
        teacherService.save(teacher);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:teacher:detail")
    public String toDetail(@PathVariable("id") Teacher teacher, Model model) {
        model.addAttribute("teacher",teacher);
        return "/system/teacher/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:teacher:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (teacherService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}