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
//        // ????????????????????????
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
//    //?????????????????????key?????????????????????
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
//     * ??????????????????
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
//        // ????????????????????????
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
     * ??????????????????
     * @param file ???????????????????????????
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException
    {
//        System.out.println("??????????????????????????????");
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //??????????????????
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdir();
        }
        //????????????????????????
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID =uuid +StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath +fileUUID);
        //???????????????md5
//        String md5 = SecureUtil.md5(uploadFile);
        file.transferTo(uploadFile);
        String url="http://localhost:9000/file/"+fileUUID;
        //???????????????
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
     * ??????????????????   http://localhost:9000/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // ??????????????????????????????????????????
        File uploadFile = new File(fileUploadPath + fileUUID);
        // ????????????????????????
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // ????????????????????????
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * ??????????????????
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

        System.out.println("??????????????????????????????");
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        // ?????????????????????????????????
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
//        // 1. ?????????????????????
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files;
//        if (StrUtil.isBlank(jsonStr)) {  // 2. ????????????json?????????
//            files = fileMapper.selectList(null);  // 3. ????????????????????????
//            // 4. ???????????????redis
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
//        } else {
//            // ????????????????????????
//            // 5. ?????????, ???redis?????????????????????
//            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
//            }, true);
//        }
//        return Result.success(files);
//    }
}
