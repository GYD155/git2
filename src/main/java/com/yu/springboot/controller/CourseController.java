package com.yu.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Question;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.CourseService;
import com.yu.springboot.service.QuestionService;
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
@RequestMapping("/course")
public class CourseController
{
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @GetMapping
    public Result findAll()
    {
        return Result.success(courseService.list());
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Course course)
    {
//        System.out.println(course);
//        if(course.getId()==null)
//        {
//            question.setTime(DateUtil.now());
////            question.setUserId();
//        }
        return courseService.saveOrUpdate(course);
    }
    @GetMapping("/test8")
    public IPage<Course> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Course> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
//        System.out.println(queryWrapper);
        return courseService.page(page,queryWrapper);

    }
    @GetMapping("/test12/{userRole}")
    public IPage<Course> findByUserRole(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @PathVariable String userRole)
    {
//        System.out.println(userRole);
        IPage<Course> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_role",userRole);
        return courseService.page(page,queryWrapper);
//        Course one = courseService.getOne(queryWrapper);
//        System.out.println(one);
//        return Result.success(one);
    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return courseService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return courseService.removeByIds(ids);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {

        List<Course> list=courseService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);


        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //????????????
        String fileName= URLEncoder.encode("????????????","UTF-8");
        //Content-disposition???MIME??????????????????MIME????????????MIME??????????????????????????????????????????
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream= response.getOutputStream();
        //???Writer?????????OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();

    }
    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception
    {
        InputStream inputStream=file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);
        List<Course> list=reader.readAll(Course.class);
        courseService.saveBatch(list);
        System.out.println(list);
    }



    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Course one = courseService.getOne(queryWrapper);
        return Result.success(one);
    }



}
