package com.alan.admin.system.controller;

import com.alan.admin.system.validator.DormitoryValid;
import com.alan.common.enums.StatusEnum;
import com.alan.common.utils.EntityBeanUtil;
import com.alan.common.utils.ResultVoUtil;
import com.alan.common.utils.StatusUtil;
import com.alan.common.vo.ResultVo;
import com.alan.modules.system.domain.Dormitory;
import com.alan.modules.system.service.DormitoryService;
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
 * @date 2021/01/04
 */
@Controller
@RequestMapping("/system/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:dormitory:index")
    public String index(Model model, Dormitory dormitory) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<Dormitory> example = Example.of(dormitory, matcher);
        Page<Dormitory> list = dormitoryService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/dormitory/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:dormitory:add")
    public String toAdd() {
        return "/system/dormitory/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:dormitory:edit")
    public String toEdit(@PathVariable("id") Dormitory dormitory, Model model) {
        model.addAttribute("dormitory", dormitory);
        return "/system/dormitory/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:dormitory:add", "system:dormitory:edit"})
    @ResponseBody
    public ResultVo save(@Validated DormitoryValid valid, Dormitory dormitory) {
        // 复制保留无需修改的数据
        if (dormitory.getId() != null) {
            Dormitory beDormitory = dormitoryService.getById(dormitory.getId());
            EntityBeanUtil.copyProperties(beDormitory, dormitory);
        }

        // 保存数据
        dormitoryService.save(dormitory);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:dormitory:detail")
    public String toDetail(@PathVariable("id") Dormitory dormitory, Model model) {
        model.addAttribute("dormitory",dormitory);
        return "/system/dormitory/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:dormitory:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (dormitoryService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}