package com.yu.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Ask;
import com.yu.springboot.bean.Course;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.AskService;
import com.yu.springboot.service.CourseService;
import com.yu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ask")
public class AskController
{
    @Autowired
    private AskService askService;
//    @Autowired
//    private UserService userService;

    @GetMapping
    public Result findAll()
    {
        return Result.success(askService.list());
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Ask ask)
    {
//        System.out.println(course);
//        if(course.getId()==null)
//        {
//            question.setTime(DateUtil.now());
////            question.setUserId();
//        }
        if(!(ask.getReply()==null))
        {
            ask.setState("已回复");
        }
        return askService.saveOrUpdate(ask);
    }
    @GetMapping("/test8")
    public IPage<Ask> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String qus)
    {
        IPage<Ask> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("qus",qus);
//        System.out.println(queryWrapper);
        return askService.page(page,queryWrapper);

    }
//    @GetMapping("/test12/{userRole}")
//    public IPage<Course> findByUserRole(@RequestParam Integer pageNum,
//                                 @RequestParam Integer pageSize,
//                                 @PathVariable String userRole)
//    {
////        System.out.println(userRole);
//        IPage<Course> page=new Page<>(pageNum,pageSize);
//        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("user_role",userRole);
//        return askService.page(page,queryWrapper);
////        Course one = courseService.getOne(queryWrapper);
////        System.out.println(one);
////        return Result.success(one);
//    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return askService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return askService.removeByIds(ids);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {

        List<Ask> list=askService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);


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
        List<Ask> list=reader.readAll(Ask.class);
        askService.saveBatch(list);
        System.out.println(list);
    }



    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Ask one = askService.getOne(queryWrapper);
        return Result.success(one);
    }



}
