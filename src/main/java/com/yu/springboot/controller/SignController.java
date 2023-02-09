package com.yu.springboot.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Sign;
import com.yu.springboot.common.Result;
import com.yu.springboot.service.ExamService;
import com.yu.springboot.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sign")
public class SignController
{
    @Autowired
    private SignService signService;
    @Autowired
    private ExamService examService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Sign sign) {
        signService.saveOrUpdate(sign);
        return Result.success();
    }
    @GetMapping
    public Result findAll()
    {
        return Result.success(signService.list());
    }
    @PostMapping("/test7")
    public boolean test7(@RequestBody Sign sign)
    {
        if(sign.getId()==null)
        {
            sign.setName(DateUtil.now());
//            question.setUserId();
        }
        return signService.saveOrUpdate(sign);
    }
    @GetMapping("/test8")
    public IPage<Sign> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<Sign> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Sign> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return signService.page(page,queryWrapper);

    }
//        @GetMapping("/test8/front")
//    public IPage<Exam> test8Front(@RequestParam Integer pageNum,
//                             @RequestParam Integer pageSize,
//                             @RequestParam String name)
//    {
//        IPage<Exam> page=new Page<>(pageNum,pageSize);
//        QueryWrapper<Exam> queryWrapper=new QueryWrapper<>();
//        queryWrapper.like("name",name);
//
//        Page<Exam> page1 = examService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        //查出当前用户所有报名的考试
//        List<Sign> signList = signService.list(new QueryWrapper<Sign>().eq("user_name", name));
//        for(Exam exam:page.getRecords())
//        {
//            exam.setEnable(false);
//            Integer examId= exam.getId();
//            signList.stream().filter(sign->sign.getExamId().equals(examId)).findFirst().ifPresent(sign->{
//               //当且仅当 该用户有考试且审核通过 才能设置开始考试按钮
//                exam.setEnable("审核通过".equals(sign.getState()));
//            });
//
//        }
//        return examService.page(page1);

//    }
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum,
//                           @RequestParam Integer pageSize,
//                           @RequestParam Integer id
//                           )
//    {
//
//        return Result.success(signService.findPage(new Page<>(pageNum, pageSize), id));
//    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return signService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return signService.removeByIds(ids);
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
        QueryWrapper<Sign> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Sign one = signService.getOne(queryWrapper);
        return Result.success(one);
    }


}
