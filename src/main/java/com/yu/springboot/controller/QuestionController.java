package com.yu.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Question;
import com.yu.springboot.bean.User;
import com.yu.springboot.common.Constants;
import com.yu.springboot.common.Result;
import com.yu.springboot.controller.dto.UserDto;
import com.yu.springboot.controller.dto.UserPasswordDto;
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
@RequestMapping("/question")
public class QuestionController
{
    @Autowired
    private QuestionService questionService;

    @PostMapping("/test7")
    public boolean test7(@RequestBody Question question)
    {
        if(question.getId()==null)
        {
            question.setTime(DateUtil.now());
//            question.setUserId();
        }
        return questionService.saveOrUpdate(question);
    }
    @GetMapping("/test8")
    public IPage<Question> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Question> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return questionService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return questionService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return questionService.removeByIds(ids);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception
    {

        List<Question> list=questionService.list();

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
        List<Question> list=reader.readAll(Question.class);
        questionService.saveBatch(list);
        System.out.println(list);
    }



    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Question one = questionService.getOne(queryWrapper);
        return Result.success(one);
    }


}
