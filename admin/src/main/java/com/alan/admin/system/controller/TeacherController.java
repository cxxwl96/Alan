package com.alan.admin.system.controller;

import com.alan.admin.system.validator.TeacherValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.component.shiro.ShiroUtil;
import com.alan.modules.system.domain.*;
import com.alan.modules.system.service.RoleService;
import com.alan.modules.system.service.TeacherService;
import com.alan.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/05
 */
@Controller
@RequestMapping("/system/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:teacher:index")
    public String index(Model model, Teacher teacher, String college) {
        // 院系
        if (college != null) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(college));
            teacher.setCollegeId(dept);
        }
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
     *
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:teacher:add", "system:teacher:edit"})
    @ResponseBody
    public ResultVo save(@Validated TeacherValid valid, Teacher teacher) {
        if (teacher.getId() == null) {
            if (teacherService.getByNo(teacher.getNo()) != null) {
                return ResultVoUtil.error("该教职工号已存在");
            }
            // 添加教师
            // 创建系统账户
            User user = new User();
            user.setUsername(teacher.getNo());
            user.setNickname(teacher.getNames());
            String[] secret = createSecret();
            user.setSalt(secret[0]);
            user.setPassword(secret[1]);
            user.setPicture(teacher.getPhoto());
            user.setSex(teacher.getSex());
            user.setPhone(teacher.getPhone());
            user = userService.save(user);
            // 为账号分配教师权限
            Set<Role> roles = new HashSet<>(0);
            Role role = roleService.getByName("teacher");
            roles.add(role);
            user.setRoles(roles);
            // 保存数据
            teacher.setUserId(user);
        } else {
            // 编辑教师
            Teacher beTeacher = teacherService.getById(teacher.getId());
            EntityBeanUtil.copyProperties(beTeacher, teacher);
            Teacher tea = teacherService.getByNo(teacher.getNo());
            if (tea != null && tea.getId() != teacher.getId()) {
                return ResultVoUtil.error("该教职工号已存在");
            }
            teacher.setUserId(beTeacher.getUserId());
            // 编辑系统账户
            User user = userService.getById(teacher.getUserId().getId());
            user.setUsername(teacher.getNo());
            user.setNickname(teacher.getNames());
            user.setPicture(teacher.getPhoto());
            user.setSex(teacher.getSex());
            user.setPhone(teacher.getPhone());
            userService.save(user);
        }
        teacherService.save(teacher);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    private String[] createSecret() {
        String[] secret = new String[2];
        secret[0] = ShiroUtil.getRandomSalt();
        secret[1] = ShiroUtil.encrypt("123456", secret[0]);
        return secret;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:teacher:detail")
    public String toDetail(@PathVariable("id") Teacher teacher, Model model) {
        model.addAttribute("teacher", teacher);
        return "/system/teacher/detail";
    }

    /**
     * 获取教师信息
     */
    @PostMapping("/detail/{id}")
    @RequiresPermissions("system:teacher:detail")
    @ResponseBody
    public ResultVo detail(@PathVariable("id") Teacher teacher) {
        return ResultVoUtil.success("查询成功", teacher);
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

    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/me")
    @RequiresPermissions("system:teacher:me")
    public String toMe(Model model) {
        // 获取当前登录用户
        User subUser = ShiroUtil.getSubject();
        Teacher teacher = teacherService.getByUserId(subUser.getId());
        Role role = roleService.getByName("teacher");
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setUserId(subUser);
            teacher.setNo(subUser.getUsername());
        }
        model.addAttribute("teacher", teacher);
        return "/system/teacher/me";
    }

    /**
     * 跳转到show页面
     */
    @GetMapping("/show")
    @RequiresPermissions("system:teacher:show")
    public String show(Model model, Teacher teacher, String college) {
        // 院系
        if (college != null) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(college));
            teacher.setCollegeId(dept);
        }
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
        return "/system/teacher/show";
    }
}