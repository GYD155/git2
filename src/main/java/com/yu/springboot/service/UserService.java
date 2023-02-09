package com.yu.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Menu;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Constants;
import com.yu.springboot.controller.dto.UserDto;
import com.yu.springboot.controller.dto.UserPasswordDto;
import com.yu.springboot.exception.ServiceException;
import com.yu.springboot.mapper.RoleMapper;
import com.yu.springboot.mapper.RoleMenuMapper;
import com.yu.springboot.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper,User>
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuService menuService;

    public UserDto login(UserDto userDto)
    {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",userDto.getName());
        queryWrapper.eq("pwd",userDto.getPwd());
        User one=getOne(queryWrapper);
        if(one!=null)
        {
            BeanUtils.copyProperties(one,userDto, String.valueOf(true));

            String role=one.getRole();
            List<Menu> roleMenus = getRoleMenus(role);
            userDto.setMenus(roleMenus);
            return userDto;
        }
        else
        {
            throw new ServiceException(Constants.CODE600,"用户名或密码错误");
        }

    }

    public User register(UserDto userDto)
    {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",userDto.getName());
        queryWrapper.eq("pwd",userDto.getPwd());
        User one=getOne(queryWrapper);
        if(one==null)
        {
            one=new User();
            BeanUtils.copyProperties(userDto,one, String.valueOf(true));
            save(one);
        }
        else
        {
            throw new ServiceException(Constants.CODE600,"用户已存在");
        }
        return one;
    }

    public void updatePassword(UserPasswordDto userPasswordDTO)
    {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE600, "密码错误");
        }
    }
    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag)
    {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus)
        {
            if (menuIds.contains(menu.getId()))
            {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
//    @Autowired
//    private UserMapper userMapper;
//
//    public int userUpdate(User user)//更新数据
//    {
//        return userMapper.update(user);
//    }
//    public int userInsert(User user)//插入数据
//    {
//        return userMapper.insert(user);
//    }
//    public List<User> userShow()//展示数据
//    {
//        return userMapper.findAll();
//    }
//
//    public Integer deleteUserById(Integer id)
//    {
//        return userMapper.delete(id);
//    }
//
//    public List<User> userSelect(Integer pageNum, Integer pageSize,String name)
//    {
//        return userMapper.select(pageNum,pageSize,name);
//    }
//    public Integer userSelcetNum(String name)
//    {
//        return userMapper.selectNum(name);
//    }
//    public boolean saveUser(User user)
//    {
//         return saveOrUpdate(user);
//    }
}
