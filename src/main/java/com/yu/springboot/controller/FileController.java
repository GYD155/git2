package com.yu.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Exam;
import com.yu.springboot.bean.Files;
import com.yu.springboot.common.Result;
import com.yu.springboot.mapper.FileMapper;
import com.yu.springboot.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController
{
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;
    @Resource
    private FileService fileService;

    @GetMapping("/all")
    public Result all()
    {
        return Result.success(fileMapper.selectList(null));
    }

    @GetMapping("/test/{id}")
    public Result findOne(@PathVariable Integer id)
    {
        return Result.success(fileMapper.selectById(id));
    }
//    @GetMapping("/test12/{type}")
//    public IPage<Files> findByUserRole1(@RequestParam Integer pageNum,
//                                      @RequestParam Integer pageSize,
//                                      @PathVariable String type)
//    {
////        System.out.println(userRole);
//        IPage<Files> page=new Page<>(pageNum,pageSize);
//        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("type",type);
//        return fileService.page(page,queryWrapper);
////        Course one = courseService.getOne(queryWrapper);
////        System.out.println(one);
////        return Result.success(one);
//    }
//    @GetMapping("/test/{type}")
//    public Result findOne1(@PathVariable String type)
//    {
//        return Result.success(fileMapper.(type));
//    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception
    {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size=file.getSize();

        File uploadParentFile = new File(fileUploadPath);
        if(!uploadParentFile.exists())
        {
            uploadParentFile.mkdirs();
        }

        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid=uuid+StrUtil.DOT+type;
        File uploadFile = new File(fileUploadPath + fileUuid);
        file.transferTo(uploadFile);

        String url="http://localhost:9090/file/"+fileUuid;

        Files saveFile=new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        fileMapper.insert(saveFile);
        return url;


    }
    @PostMapping("/upload1/{userRole}")
    public String upload1(@RequestParam MultipartFile file,@PathVariable String userRole) throws Exception
    {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size=file.getSize();

        File uploadParentFile = new File(fileUploadPath);
        if(!uploadParentFile.exists())
        {
            uploadParentFile.mkdirs();
        }

        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid=uuid+StrUtil.DOT+type;
        File uploadFile = new File(fileUploadPath + fileUuid);
        file.transferTo(uploadFile);

        String url="http://localhost:9090/file/"+fileUuid;

        Files saveFile=new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setUserRole(userRole);
        fileMapper.insert(saveFile);
        return url;


    }
    @GetMapping("/{fileUuid}")
    public void  download(@PathVariable String fileUuid, HttpServletResponse response) throws  Exception
    {
        File uploadFile = new File(fileUploadPath + fileUuid);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        fileMapper.updateById(files);
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(fileMapper.selectById(id));
    }

    //清除一条缓存，key为要清空的数据
//    @CacheEvict(value="files",key="'frontAll'")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setDelete(true);
        fileMapper.updateById(files);
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("isdelete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("type", "mp4");
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @GetMapping("/page2")
    public Result findPage2(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.ne("type", "mp4");
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @GetMapping("/test122/{userRole}")
    public IPage<Files> findByUserRole(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @PathVariable String userRole)
    {
//        System.out.println(userRole);
        IPage<Files> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_role",userRole);
        return fileService.page(page,queryWrapper);
//        Course one = courseService.getOne(queryWrapper);
//        System.out.println(one);
//        return Result.success(one);
    }
//    @GetMapping("/page3/{userRole}")
//    public Result findPage3(@RequestParam Integer pageNum,
//                            @RequestParam Integer pageSize,
//                            @RequestParam(defaultValue = "") String userRole) {
//
//        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//        // 查询未删除的记录
//        queryWrapper.eq("user_role", userRole);
////        queryWrapper.orderByDesc("id");
////        if (!"".equals(name)) {
////            queryWrapper.like("name", name);
////        }
//        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
//    }
}
