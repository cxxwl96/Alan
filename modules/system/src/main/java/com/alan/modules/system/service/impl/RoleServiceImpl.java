package com.alan.modules.system.service.impl;

import com.alan.common.data.PageSort;
import com.alan.common.enums.StatusEnum;
import com.alan.modules.system.domain.Role;
import com.alan.modules.system.domain.Student;
import com.alan.modules.system.repository.RoleRepository;
import com.alan.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author cxxwl96@sina.com
 * @date 2020/8/14
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取用户角色列表
     * @param id 用户ID
     */
    @Override
    @Transactional
    public Set<Role> getUserOkRoleList(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.findByUsers_IdAndStatus(id, status);
    }

    /**
     * 判断指定的用户是否存在角色
     * @param id 用户ID
     */
    @Override
    public Boolean existsUserOk(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.existsByUsers_IdAndStatus(id, status);
    }

    /**
     * 根据角色ID查询角色数据
     * @param id 角色ID
     */
    @Override
    @Transactional
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Role> getPageList(Example<Role> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest(Sort.Direction.ASC);
        return roleRepository.findAll(example, page);
    }

    /**
     * 获取角色列表数据
     * @param sort 排序对象
     */
    @Override
    public List<Role> getListBySortOk(Sort sort) {
        return roleRepository.findAllByStatus(sort, StatusEnum.OK.getCode());
    }

    /**
     * 角色标识是否重复
     * @param role 角色实体类
     */
    @Override
    public boolean repeatByName(Role role) {
        Long id = role.getId() != null ? role.getId() : Long.MIN_VALUE;
        return roleRepository.findByNameAndIdNot(role.getName(), id) != null;
    }

    /**
     * 保存角色
     * @param role 角色实体类
     */
    @Override
    public Role save(Role role){
        return roleRepository.save(role);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 删除角色时取消与角色和菜单的关联
        if(statusEnum == StatusEnum.DELETE){
            // 非规范的Jpa操作，直接采用SQL语句
            roleRepository.cancelUserJoin(ids);
            roleRepository.cancelMenuJoin(ids);
        }
        return roleRepository.updateStatus(statusEnum.getCode(), ids) > 0;
    }

    /**
     * 根据角色名获取角色
     *
     * @param name
     * @return
     */
    @Override
    public Role getByName(String name) {
        /*Role role = new Role();
        role.setName(name);
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.equals(name));

        // 获取数据列表
        Example<Role> example = Example.of(role, matcher);
        Page<Role> list = getPageList(example);*/
        return roleRepository.findByName(name);
    }

    /**
     * 查询数据列表
     *
     * @param example
     * @return
     */
    @Override
    public List<Role> getList(Example<Role> example) {
        return roleRepository.findAll(example);
    }
}
