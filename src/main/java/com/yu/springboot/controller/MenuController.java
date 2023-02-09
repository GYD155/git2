package com.yu.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Menu;
import com.yu.springboot.bean.Role;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.MenuService;
import com.yu.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuController
{
    @Autowired
    private MenuService menuService;

    @GetMapping("/ids")
    public Result findAllIds()
    {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }
    @GetMapping
    public Result findAll(@RequestParam String name)
    {
//        IPage<Menu> page=new Page<>(pageNum,pageSize);


        return Result.success(menuService.findMenus(name));
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Menu menu)
    {
        return menuService.saveOrUpdate(menu);
    }
    @GetMapping("/test8")
    public IPage<Menu> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Menu> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return menuService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return menuService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return menuService.removeByIds(ids);
    }

}
