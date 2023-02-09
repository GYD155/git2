package com.yu.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Role;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Result;
import com.yu.springboot.mapper.FileMapper;
import com.yu.springboot.mapper.RoleMapper;
import com.yu.springboot.service.RoleService;
import com.yu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController
{
    @Autowired
    private RoleService roleService;


    @GetMapping("/test77")
    public Result findAll()
    {
        return Result.success(roleService.list());
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Role role)
    {
        return roleService.saveOrUpdate(role);
    }
    @GetMapping("/test8")
    public IPage<Role> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Role> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return roleService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return roleService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return roleService.removeByIds(ids);
    }
    /**
     * 绑定角色和菜单的关系
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds)
    {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success( roleService.getRoleMenu(roleId));
    }


}
