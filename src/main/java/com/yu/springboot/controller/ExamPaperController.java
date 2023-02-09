package com.yu.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.*;
import com.yu.springboot.common.Result;
import com.yu.springboot.controller.dto.PaperDto;
import com.yu.springboot.exception.ServiceException;
import com.yu.springboot.service.ExamPaperService;
import com.yu.springboot.service.PaperQuestionService;
import com.yu.springboot.service.PaperService;
import com.yu.springboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/examPaper")
public class ExamPaperController
{
    @Autowired
    private ExamPaperService examPaperService;


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody ExamPaper examPaper) {
        examPaperService.saveOrUpdate(examPaper);
        return Result.success();
    }

    @GetMapping("/exam/{examId}")
    public Result findByExamId(@PathVariable Integer examId)
    {
        return Result.success(examPaperService.getOne(new QueryWrapper<ExamPaper>().eq("exam_id",examId)));
    }
    @GetMapping
    public Result findAll()
    {
        return Result.success(examPaperService.list());
    }
//    @GetMapping("/view/{paperId}")
//    public Result view(@PathVariable Integer paperId)
//    {
//        List<Question> list=paperQuestionService.selectQuestions(paperId);
////        System.out.println(Result.success(list));
//        return Result.success(list);
//    }
//    @PostMapping("/takePaper")
//    public Result takePaper(@RequestBody PaperDto paperDTO)
//    {
//        //删除旧的试卷
//        UpdateWrapper<PaperQuestion> updateWrapper=new UpdateWrapper<>();
//        updateWrapper.eq("paper_id",paperDTO.getPaperId());
//        paperQuestionService.remove(updateWrapper);
//
//
//        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("course_id",paperDTO.getCourseId());
//        //根据id查出该课程的所有题目，再根据type去分
//        List<Question> questionList=questionService.list(queryWrapper);
//        List<Question> type1List = questionList.stream().filter(question -> question.getType() == 1).collect(Collectors.toList());
//        List<Question> type2List = questionList.stream().filter(question -> question.getType() == 2).collect(Collectors.toList());
//        if(type1List.size()<paperDTO.getType1())
//        {
//            throw new ServiceException("-1","选择题数量不足");
//        }
//        if(type2List.size()<paperDTO.getType2())
//        {
//            throw new ServiceException("-1","问答题数量不足");
//        }
//        //开始随机组卷
//        List<PaperQuestion> paperQuestion = getPaperQuestion(type1List.size(), paperDTO.getType1(), type1List, paperDTO.getPaperId());
//        paperQuestion.addAll(getPaperQuestion(type2List.size(), paperDTO.getType2(), type2List, paperDTO.getPaperId()));
//        paperQuestionService.saveBatch(paperQuestion);
//        return Result.success();
//
//
//    }
//    //封装一个获取试卷和题目关联关系的方法
//    private List<PaperQuestion> getPaperQuestion(int questionSize, int paperQuestionSize, List<Question> source, Integer paperId)
//    {
//        List<Integer> typeRandomList=getEleList(questionSize,paperQuestionSize);
//        List<PaperQuestion> list=new ArrayList<>();
//        for(Integer index:typeRandomList)
//        {
//            Question question= source.get(index);
//            PaperQuestion paperQuestion=new PaperQuestion();
//            paperQuestion.setPaperId(paperId);
//            paperQuestion.setQuestionId(question.getId());
//            list.add(paperQuestion);
//        }
//        return list;
//    }
//    //封装一个获取随机数的方法
//    private List<Integer> getEleList(int sourceSize,int resultSize)
//    {
//        List<Integer> list = CollUtil.newArrayList();
//        for (int i = 0; i < sourceSize; i++)
//        {
//            list.add(i);
//        }
//        return RandomUtil.randomEleList(list,resultSize);
//    }

    @PostMapping("/test7")
    public Result save7(@RequestBody ExamPaper examPaper)
    {
        examPaperService.addPaper(examPaper);
        return Result.success();
    }
//    public boolean test7(@RequestBody ExamPaper examPaper)
//    {
////        if(course.getId()==null)
////        {
////            question.setTime(DateUtil.now());
//////            question.setUserId();
////        }
//        return examPaperService.saveOrUpdate(examPaper);
//    }
    @GetMapping("/test8")
    public IPage<ExamPaper> test8(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String name)
    {
        IPage<ExamPaper> page=new Page<>(pageNum,pageSize);
        QueryWrapper<ExamPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return examPaperService.page(page,queryWrapper);

    }
    @DeleteMapping("/test9/{id}")
    public boolean test9(@PathVariable Integer id)
    {
        return examPaperService.removeById(id);
    }
    @PostMapping("/test10")
    public boolean test10(@RequestBody List<Integer> ids)
    {
        return examPaperService.removeByIds(ids);
    }

    @GetMapping("/test11/{name}")
    public Result findByUserName(@PathVariable String name)
    {
        QueryWrapper<ExamPaper> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        ExamPaper one = examPaperService.getOne(queryWrapper);
        return Result.success(one);
    }


}
