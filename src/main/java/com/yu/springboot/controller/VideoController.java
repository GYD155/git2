package com.yu.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.springboot.bean.Files;
import com.yu.springboot.bean.Video;
import com.yu.springboot.common.Result;
import com.yu.springboot.mapper.FileMapper;
import com.yu.springboot.mapper.VideoMapper;
import com.yu.springboot.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/video")
public class VideoController
{
//    @Value("${files.upload.path}")
//    private String fileUploadPath;
//
//    @Resource
//    private FileMapper fileMapper;
//
//    @GetMapping("/all")
//    public Result all()
//    {
//        return Result.success(fileMapper.selectList(null));
//    }
//
//    @PostMapping("/upload")
//    public String upload(@RequestParam MultipartFile file) throws Exception
//    {
//        String originalFilename = file.getOriginalFilename();
//        String type = FileUtil.extName(originalFilename);
//        long size=file.getSize();
//
//        File uploadParentFile = new File(fileUploadPath);
//        if(!uploadParentFile.exists())
//        {
//            uploadParentFile.mkdirs();
//        }
//
//        String uuid = IdUtil.fastSimpleUUID();
//        String fileUuid=uuid+StrUtil.DOT+type;
//        File uploadFile = new File(fileUploadPath + fileUuid);
//        file.transferTo(uploadFile);
//
//        String url="http://localhost:9090/file/"+fileUuid;
//
//        Files saveFile=new Files();
//        saveFile.setName(originalFilename);
//        saveFile.setType(type);
//        saveFile.setSize(size/1024);
//        saveFile.setUrl(url);
//        fileMapper.insert(saveFile);
//        return url;
//
//
//    }
//    @GetMapping("/{fileUuid}")
//    public void  download(@PathVariable String fileUuid, HttpServletResponse response) throws  Exception
//    {
//        File uploadFile = new File(fileUploadPath + fileUuid);
//        ServletOutputStream os = response.getOutputStream();
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
//        response.setContentType("application/octet-stream");
//        // 读取文件的字节流
//        os.write(FileUtil.readBytes(uploadFile));
//        os.flush();
//        os.close();
//    }
//    @PostMapping("/update")
//    public Result update(@RequestBody Files files) {
//        fileMapper.updateById(files);
////        flushRedis(Constants.FILES_KEY);
//        return Result.success();
//    }
//
//    @GetMapping("/detail/{id}")
//    public Result getById(@PathVariable Integer id) {
//        return Result.success(fileMapper.selectById(id));
//    }
//
//    //清除一条缓存，key为要清空的数据
////    @CacheEvict(value="files",key="'frontAll'")
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        Files files = fileMapper.selectById(id);
//        files.setDelete(true);
//        fileMapper.updateById(files);
////        flushRedis(Constants.FILES_KEY);
//        return Result.success();
//    }
//
//    @PostMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        // select * from sys_file where id in (id,id,id...)
//        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("id", ids);
//        List<Files> files = fileMapper.selectList(queryWrapper);
//        for (Files file : files) {
//            file.setDelete(true);
//            fileMapper.updateById(file);
//        }
//        return Result.success();
//    }
//
//    /**
//     * 分页查询接口
//     * @param pageNum
//     * @param pageSize
//     * @param name
//     * @return
//     */
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum,
//                           @RequestParam Integer pageSize,
//                           @RequestParam(defaultValue = "") String name) {
//
//        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//        // 查询未删除的记录
//        queryWrapper.eq("isdelete", false);
//        queryWrapper.orderByDesc("id");
//        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
//        }
//        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
//    }

    @Value("${files.upload.path}")
    private String fileUploadPath;

    //    @Value("${server.ip}")
//    private String serverIp;
//
    @Resource
    private VideoService videoService;
    @Resource
    private VideoMapper videoMapper;
    //
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException
    {
//        System.out.println("前端文件加载访问成功");
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdir();
        }
        //定义唯一文件标识
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID =uuid +StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath +fileUUID);
        //获取文件的md5
//        String md5 = SecureUtil.md5(uploadFile);
        file.transferTo(uploadFile);
        String url="http://localhost:9000/file/"+fileUUID;
        //存储数据库
        Video savaFile = new Video();
        savaFile.setName(originalFilename);
//        savaFile.setCheckFlag("0");
//        savaFile.setPublishState("1");
        savaFile.setUrl(url);
//        fileMapper.insert(savaFile);
        videoMapper.insert(savaFile);
        return url;
    }


    /**
     * 文件下载接口   http://localhost:9000/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
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
                           @RequestParam(defaultValue = "") String name)
    {

        System.out.println("前端页面加载访问成功");
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        // 查询已经通过审核的记录
//        queryWrapper.eq("checkFlag", "1");
//        queryWrapper.orderByDesc("videoId");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(videoMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }


    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Integer id)
    {
        return Result.success(videoMapper.selectById(id));
    }


    @DeleteMapping("/{id}")
    public Result deleteFile(@PathVariable Integer id) {
        Video video = videoMapper.selectById(id);
        System.out.println("id="+id);
        videoMapper.deleteById(id);
        return Result.success();
    }

    @RequestMapping("/delete")
    public String delete(Integer videoId)
    {
        System.out.println("id="+videoId);
        int i= videoMapper.deleteById(videoId);
        return i >0 ?"success":"error";
    }


    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids)
    {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Video> files = videoMapper.selectList(queryWrapper);
        for (Video file : files) {
//            file.setIsDelete(true);
            videoMapper.updateById(file);
        }
        return Result.success();
    }
    @PostMapping("/update")
    public Result update(@RequestBody Video video)
    {
        return  Result.success(videoMapper.updateById(video));

    }
    //    @AuthAccess
//    @GetMapping("/front/all")
//    public Result frontAll() {
//        // 1. 从缓存获取数据
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files;
//        if (StrUtil.isBlank(jsonStr)) {  // 2. 取出来的json是空的
//            files = fileMapper.selectList(null);  // 3. 从数据库取出数据
//            // 4. 再去缓存到redis
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
//        } else {
//            // 减轻数据库的压力
//            // 5. 如果有, 从redis缓存中获取数据
//            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
//            }, true);
//        }
//        return Result.success(files);
//    }
}
