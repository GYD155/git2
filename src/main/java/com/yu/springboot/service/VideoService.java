package com.yu.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.springboot.bean.Course;
import com.yu.springboot.bean.Video;
import com.yu.springboot.mapper.CourseMapper;
import com.yu.springboot.mapper.VideoMapper;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video>
{
}
