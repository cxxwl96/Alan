package com.alan.admin.system.controller;

import com.alan.common.utils.ResultVoUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Course;
import com.alan.modules.system.domain.Role;
import com.alan.modules.system.domain.Score;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.service.RoleService;
import com.alan.modules.system.service.ScoreService;
import com.alan.modules.system.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author cxxwl96@sina.com
 * @date 2021/01/10
 */
@Controller
@RequestMapping("/system/statistics")
public class StatisticsController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/user/count")
    @RequiresPermissions("system:statistics:user:count")
    @ResponseBody
    public ResultVo userCount(Role role) {
        List<Role> roles = getRoleList(role);
        if (roles == null && roles.size() == 0) {
            return ResultVoUtil.error(500, "接口请求失败");
        }
        JSONArray jsonArray = new JSONArray();
        for (Role item : roles) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", item);
            jsonObject.put("users", item.getUsers());
            jsonObject.put("count", item.getUsers().size());
            jsonArray.add(jsonObject);
        }
        return ResultVoUtil.success("查询成功", jsonArray);
    }

    /**
     * 获取角色列表
     *
     * @param role
     * @return
     */
    private List<Role> getRoleList(Role role) {
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 获取数据列表
        Example<Role> example = Example.of(role, matcher);
        return roleService.getList(example);
    }

    @GetMapping("/score/count")
    @RequiresPermissions("system:statistics:score:count")
    @ResponseBody
    public ResultVo scoreCount(Score score) {
        if (score.getStudentId() != null && score.getStudentId().getId() != null) {
            Student student = new Student();
            student.setId(score.getStudentId().getId());
            score.setStudentId(student);
        }
        if (score.getCourseId() != null && score.getCourseId().getId() != null) {
            Course course = new Course();
            course.setId(score.getCourseId().getId());
            score.setCourseId(course);
        }
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<Score> example = Example.of(score, matcher);
        List<Score> list = scoreService.getList(example);
        return ResultVoUtil.success("查询成功", list);
    }

    /**
     * 跳转到用户统计页面
     */
    @GetMapping("/user")
    @RequiresPermissions("system:statistics:user")
    public String toUser(){
        return "/system/statistics/user";
    }

    /**
     * 跳转到分数统计页面
     */
    @GetMapping("/score")
    @RequiresPermissions("system:statistics:score")
    public String toScore(){
        return "/system/statistics/score";
    }

    /**
     * 跳转到3D分数统计页面
     */
    @GetMapping("/score3D")
    @RequiresPermissions("system:statistics:score3D")
    public String toScore3D(){
        return "/system/statistics/score3D";
    }
}
