package com.yu.springboot.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.ExamPaper;
import com.yu.springboot.bean.StudentPaper;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.ExamPaperService;
import com.yu.springboot.service.StudentPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/studentPaper")
public class StudentPaperController
{
    @Autowired
    private StudentPaperService studentPaperService;


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody StudentPaper studentPaper)
    {
        studentPaperService.saveOrUpdate(studentPaper);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id)
    {
        QueryWrapper<StudentPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        StudentPaper one = studentPaperService.getOne(queryWrapper);
        return Result.success(one);
//        return Result.success(studentPaperService.getById(id));
    }
//    @GetMapping("/1/{examId}")
//    public Result findOne1(@PathVariable Integer examId)
//    {
//        return Result.success(studentPaperService.get(examId));
//    }

//    @GetMapping("/exam/{examId}")
//    public Result findByExamId(@PathVariable Integer examId)
//    {
//        return Result.success(studentPaperService.getOne(new QueryWrapper<ExamPaper>().eq("exam_id",examId)));
//    }
    @GetMapping
    public Result findAll()
    {
        return Result.success(studentPaperService.list());
    }


//    @PostMapping("/test7")
//    public Result save7(@RequestBody ExamPaper examPaper)
//    {
//        studentPaperService.addPaper(examPaper);
//        return Result.success();
//    }

    @GetMapping("/test8")
    public IPage<StudentPaper> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String paper)
    {
        IPage<StudentPaper> page=new Page<>(pageNum,pageSize);
        QueryWrapper<StudentPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("paper",paper);
        return studentPaperService.page(page,queryWrapper);

    }
    @GetMapping("/test12/{userName}")
    public IPage<StudentPaper> findByUserRole(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @PathVariable String userName)
    {
//        System.out.println(userRole);
        IPage<StudentPaper> page=new Page<>(pageNum,pageSize);
        QueryWrapper<StudentPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return studentPaperService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return studentPaperService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return studentPaperService.removeByIds(ids);
    }

    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<StudentPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        StudentPaper one = studentPaperService.getOne(queryWrapper);
        return Result.success(one);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {

        List<StudentPaper> list=studentPaperService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);
//
//        writer.addHeaderAlias("name","用户名");
//        writer.addHeaderAlias("pwd","密码");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","电话");

        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //设置标题
        String fileName= URLEncoder.encode("试卷详情","UTF-8");
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream= response.getOutputStream();
        //将Writer刷新到OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();

    }
    @GetMapping("/1/{examId}")
    public Result findByUserName1(@PathVariable Integer examId)
    {
        QueryWrapper<StudentPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("exam_id",examId);
        StudentPaper one = studentPaperService.getOne(queryWrapper);
        return Result.success(one);
    }


}
