package com.yu.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Constants;
import com.yu.springboot.common.Result;
import com.yu.springboot.controller.dto.UserDto;
import com.yu.springboot.controller.dto.UserPasswordDto;
import com.yu.springboot.mapper.UserMapper;
import com.yu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
//@RequestMapping("/user")
public class TestController
{
//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

//    @GetMapping("/test1")
//    public String test1()
//    {
//        return "test1";
//    }
//    @GetMapping("/test2")
//    public List<User> test2()
//    {
//        return userService.userShow();
//    }
//    @PostMapping("/test3")
//    public int test3(@RequestBody User user)
//    {
//        return userService.userInsert(user);
//    }
//    @PostMapping("/test4")
//    public int test4(@RequestBody User user)
//    {
//        return userService.userUpdate(user);
//    }
//
//    @DeleteMapping("/test5/{id}")
//    public Integer test5(@PathVariable Integer id)
//    {
//        return userService.deleteUserById(id);
//    }
//    @GetMapping("/test6")
//    public Map<String,Object> test6(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String name)
//    {
//        pageNum=(pageNum-1)*pageSize;
//        name="%"+name+"%";
//        List<User> data= userService.userSelect(pageNum,pageSize,name);
//        Integer dataNum=userService.userSelcetNum(name);
//        Map<String,Object> res=new HashMap<>();
//        res.put("data",data);
//        res.put("dataNum",dataNum);
//        return res;
//    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody User user)
    {
        return userService.saveOrUpdate(user);
    }
    @GetMapping("/test8")
    public IPage<User> test8(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam String name)
    {
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return userService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return userService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return userService.removeByIds(ids);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {

        List<User> list=userService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);
//
//        writer.addHeaderAlias("name","用户名");
//        writer.addHeaderAlias("pwd","密码");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","电话");

        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //设置标题
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream= response.getOutputStream();
        //将Writer刷新到OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();

    }
    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception
    {
        InputStream inputStream=file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
        List<User> list=reader.readAll(User.class);
        userService.saveBatch(list);
        System.out.println(list);
    }
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto)
    {
        String name=userDto.getName();
        String pwd=userDto.getPwd();
//        String role=userDto.getRole();
        if(StrUtil.isBlank(name)||StrUtil.isBlank(pwd))
        {
            return Result.error(Constants.CODE400,"参数错误");
        }
        return Result.success(userService.login(userDto));
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto)
    {
        String name=userDto.getName();
        String pwd=userDto.getPwd();
        if(StrUtil.isBlank(name)||StrUtil.isBlank(pwd))
        {
            return Result.error(Constants.CODE400,"参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        User one = userService.getOne(queryWrapper);
        return Result.success(one);
    }
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDto userPasswordDTO)
    {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }





}
