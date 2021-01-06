package com.alan.admin.system.controller;

import com.alan.admin.system.validator.ScoreValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Course;
import com.alan.modules.system.domain.Score;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.service.ScoreService;
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
@RequestMapping("/system/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:score:index")
    public String index(Model model, Score score) {
        //
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
        Page<Score> list = scoreService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/score/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:score:add")
    public String toAdd() {
        return "/system/score/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:score:edit")
    public String toEdit(@PathVariable("id") Score score, Model model) {
        model.addAttribute("score", score);
        return "/system/score/add";
    }

    /**
     * 保存添加/修改的数据
     *
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:score:add", "system:score:edit"})
    @ResponseBody
    public ResultVo save(@Validated ScoreValid valid, Score score) {
        // 复制保留无需修改的数据
        if (score.getId() != null) {
            Score beScore = scoreService.getById(score.getId());
            EntityBeanUtil.copyProperties(beScore, score);
        }

        // 保存数据
        scoreService.save(score);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:score:detail")
    public String toDetail(@PathVariable("id") Score score, Model model) {
        model.addAttribute("score", score);
        return "/system/score/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:score:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (scoreService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}