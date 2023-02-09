package com.yu.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.springboot.bean.Menu;
import com.yu.springboot.bean.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends BaseMapper<Menu>
{

}
