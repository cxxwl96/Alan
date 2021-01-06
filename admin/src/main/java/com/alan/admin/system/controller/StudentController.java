package com.alan.admin.system.controller;

import com.alan.admin.system.validator.StudentValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.component.shiro.ShiroUtil;
import com.alan.modules.system.domain.*;
import com.alan.modules.system.service.DormitoryService;
import com.alan.modules.system.service.RoleService;
import com.alan.modules.system.service.StudentService;
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
 * @date 2020/12/30
 */
@Controller
@RequestMapping("/system/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DormitoryService dormitoryService;

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
    @RequiresPermissions("system:student:index")
    public String index(Model model, Student student, String college, String specialty, String grade, String clazz) {
        // 院系
        if (college != null && !"null".equals(college)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(college));
            student.setCollegeId(dept);
        }
        // 专业
        if (specialty != null && !"null".equals(specialty)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(specialty));
            student.setSpecialtyId(dept);
        }
        // 年级
        if (grade != null && !"null".equals(grade)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(grade));
            student.setGradeId(dept);
        }
        // 班级
        if (clazz != null && !"null".equals(clazz)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(clazz));
            student.setClazzId(dept);
        }
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("collegeId", match -> match.contains())
                .withMatcher("specialtyId", match -> match.contains())
                .withMatcher("gradeId", match -> match.contains())
                .withMatcher("clazzId", match -> match.contains())
                .withMatcher("stuNo", match -> match.contains())
                .withMatcher("stuNumber", match -> match.contains())
                .withMatcher("names", match -> match.contains())
                .withMatcher("idNo", match -> match.contains())
                .withMatcher("anthropology", match -> match.contains())
                .withMatcher("wayOfStudying", match -> match.contains())
                .withMatcher("status", match -> match.contains());

        // 获取数据列表
        Example<Student> example = Example.of(student, matcher);
        Page<Student> list = studentService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/student/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:student:add")
    public String toAdd() {
        return "/system/student/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:student:edit")
    public String toEdit(@PathVariable("id") Student student, Model model) {
        model.addAttribute("student", student);
        return "/system/student/add";
    }

    /**
     * 保存添加/修改的数据
     *
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:student:add", "system:student:edit"})
    @ResponseBody
    public ResultVo save(@Validated StudentValid valid, Student student) {
        if (student.getId() == null) {
            if (studentService.getByStuNo(student.getStuNo()) != null) {
                return ResultVoUtil.error("该学号已存在");
            }
            // 添加学生
            // 创建系统账户
            User user = new User();
            user.setUsername(student.getStuNo());
            user.setNickname(student.getNames());
            String[] secret = createSecret();
            user.setSalt(secret[0]);
            user.setPassword(secret[1]);
            user.setPicture(student.getPhoto());
            user.setSex(student.getSex());
            user.setPhone(student.getPhone());
            user = userService.save(user);
            // 为账号分配学生权限
            Set<Role> roles = new HashSet<>(0);
            Role role = roleService.getByName("student");
            roles.add(role);
            user.setRoles(roles);
            // 保存数据
            student.setUserId(user);
        } else {
            // 编辑学生
            Student beStudent = studentService.getById(student.getId());
            EntityBeanUtil.copyProperties(beStudent, student);
            Student stu = studentService.getByStuNo(student.getStuNo());
            if (stu != null && stu.getId() != student.getId()) {
                return ResultVoUtil.error("该学号已存在");
            }
            student.setUserId(beStudent.getUserId());
            // 编辑系统账户
            User user = userService.getById(student.getUserId().getId());
            user.setUsername(student.getStuNo());
            user.setNickname(student.getNames());
            user.setPicture(student.getPhoto());
            user.setSex(student.getSex());
            user.setPhone(student.getPhone());
            userService.save(user);
        }
        studentService.save(student);
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
    @RequiresPermissions("system:student:detail")
    public String toDetail(@PathVariable("id") Student student, Model model) {
        model.addAttribute("student", student);
        return "/system/student/detail";
    }

    /**
     * 获取学生信息
     */
    @PostMapping("/detail/{id}")
    @RequiresPermissions("system:student:detail")
    @ResponseBody
    public ResultVo detail(@PathVariable("id") Student student) {
        return ResultVoUtil.success("查询成功", student);
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:student:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (studentService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/me")
    @RequiresPermissions("system:student:me")
    public String toMe(Model model) {
        // 获取当前登录用户
        User subUser = ShiroUtil.getSubject();
        Student student = studentService.getByUserId(subUser.getId());
        Role role = roleService.getByName("student");
        if (student == null) {
            student = new Student();
            student.setUserId(subUser);
            student.setStuNo(subUser.getUsername());
        }
        model.addAttribute("student", student);
        return "/system/student/me";
    }

    /**
     * 跳转到show页面
     */
    @GetMapping("/show")
    @RequiresPermissions("system:student:show")
    public String toShow(Model model, Student student, String college, String specialty, String grade, String clazz) {
        // 院系
        if (college != null && !"null".equals(college)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(college));
            student.setCollegeId(dept);
        }
        // 专业
        if (specialty != null && !"null".equals(specialty)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(specialty));
            student.setSpecialtyId(dept);
        }
        // 年级
        if (grade != null && !"null".equals(grade)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(grade));
            student.setGradeId(dept);
        }
        // 班级
        if (clazz != null && !"null".equals(clazz)) {
            Dept dept = new Dept();
            dept.setId(Long.valueOf(clazz));
            student.setClazzId(dept);
        }
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("collegeId", match -> match.contains())
                .withMatcher("specialtyId", match -> match.contains())
                .withMatcher("gradeId", match -> match.contains())
                .withMatcher("clazzId", match -> match.contains())
                .withMatcher("stuNo", match -> match.contains())
                .withMatcher("stuNumber", match -> match.contains())
                .withMatcher("names", match -> match.contains())
                .withMatcher("idNo", match -> match.contains())
                .withMatcher("anthropology", match -> match.contains())
                .withMatcher("wayOfStudying", match -> match.contains())
                .withMatcher("status", match -> match.contains());

        // 获取数据列表
        Example<Student> example = Example.of(student, matcher);
        Page<Student> list = studentService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/student/show";
    }

    /**
     * 跳转到我的宿舍页面
     */
    @GetMapping("/MyDormitory")
    @RequiresPermissions("system:student:MyDormitory")
    public String toMyDormitory(Model model) {
        // 获取当前登录用户
        User subUser = ShiroUtil.getSubject();
        Student student = studentService.getByUserId(subUser.getId());
        List<Dormitory> dormitorys = null;
        if(student!=null){
            dormitorys = dormitoryService.getByStudentNo(student.getStuNo());
        }
        model.addAttribute("dormitorys", dormitorys);
        return "/system/student/myDormitory";
    }
}