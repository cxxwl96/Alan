package com.alan.admin.system.controller;

import com.alan.admin.system.validator.StudentValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.component.shiro.ShiroUtil;
import com.alan.modules.system.domain.Dept;
import com.alan.modules.system.domain.Role;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.domain.User;
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

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:student:index")
    public String index(Model model, Student student) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userId", match -> match.contains())
                .withMatcher("stuNo", match -> match.contains())
                .withMatcher("stuNumber", match -> match.contains())
                .withMatcher("names", match -> match.contains())
                .withMatcher("idNo", match -> match.contains())
                .withMatcher("domicile", match -> match.contains())
                .withMatcher("familyAddress", match -> match.contains())
                .withMatcher("currentAddress", match -> match.contains());

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
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:student:add", "system:student:edit"})
    @ResponseBody
    public ResultVo save(@Validated StudentValid valid, Student student) {
        // 复制保留无需修改的数据
        if (student.getId() != null) {
            Student beStudent = studentService.getById(student.getId());
            EntityBeanUtil.copyProperties(beStudent, student);
        }
        // 为学生创建账号
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
        studentService.save(student);
        return ResultVoUtil.SAVE_SUCCESS;
    }
    private String[] createSecret(){
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
        model.addAttribute("student",student);
        return "/system/student/detail";
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

}