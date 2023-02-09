package com.yu.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Sign;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.CourseService;
import com.yu.springboot.service.ExamService;
import com.yu.springboot.service.SignService;
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
@RequestMapping("/exam")
public class ExamController
{
    @Autowired
    private ExamService examService;
    @Autowired
    private SignService signService;

    @GetMapping
    public Result findAll()
    {
        return Result.success(examService.list());
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Exam exam)
    {
        System.out.println(exam);
//        if(course.getId()==null)
//        {
//            question.setTime(DateUtil.now());
////            question.setUserId();
//        }
        return examService.saveOrUpdate(exam);
    }
    @GetMapping("/test8")
    public IPage<Exam> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Exam> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Exam> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);

        return examService.page(page,queryWrapper);

    }
    @GetMapping("/test12/{userRole}")
    public IPage<Exam> findByUserRole(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @PathVariable String userRole)
    {
//        System.out.println(userRole);
        IPage<Exam> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Exam> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_role",userRole);
        return examService.page(page,queryWrapper);
//        Course one = courseService.getOne(queryWrapper);
//        System.out.println(one);
//        return Result.success(one);
    }
//    @GetMapping("/test8/front")
//    public IPage<Exam> test8Front(@RequestParam Integer pageNum,
//                             @RequestParam Integer pageSize,
//                             @RequestParam String name)
//    {
//        IPage<Exam> page=new Page<>(pageNum,pageSize);
//        QueryWrapper<Exam> queryWrapper=new QueryWrapper<>();
//        queryWrapper.like("name",name);
//
//        Page<Exam> page1 = examService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        signService.list(new QueryWrapper<Sign>().eq("user_name",curr))
//        for(Exam record:page.getRecords())
//        {
//            Integer examId= record.getId();
//        }
//        return examService.page(page1);
//
//    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return examService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return examService.removeByIds(ids);
    }
//    @GetMapping("/export")
//    public void export(HttpServletResponse response)throws Exception
//    {
//
//        List<Course> list=examService.list();
//
//        ExcelWriter writer= ExcelUtil.getWriter(true);
//
//
//        writer.write(list,true);
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
//
//        //设置标题
//        String fileName= URLEncoder.encode("用户信息","UTF-8");
//        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
//        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
//
//        ServletOutputStream outputStream= response.getOutputStream();
//        //将Writer刷新到OutPut
//        writer.flush(outputStream,true);
//        outputStream.close();
//        writer.close();
//
//    }
//    @PostMapping("/import")
//    public void imp(MultipartFile file) throws Exception
//    {
//        InputStream inputStream=file.getInputStream();
//        ExcelReader reader=ExcelUtil.getReader(inputStream);
//        List<Course> list=reader.readAll(Course.class);
//        examService.saveBatch(list);
//        System.out.println(list);
//    }



    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<Exam> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Exam one = examService.getOne(queryWrapper);
        return Result.success(one);
    }


}
