package com.ruyi.dao;
import com.ruyi.Entity.Welcome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface WelcomeMapper {
    //获取欢迎页面信息
    public List<Welcome> getWelinfo();

    //插入新欢迎页面信息
    public int insetWelInfo(Welcome wel);

    //更新欢迎页面信息
    public int updateWelInfo(Welcome wel);

    //删除欢迎页面信息
    public int deleteWelInfo(@Param("Welcome_Id") String id);

    public Welcome getWelById(@Param("Welcome_Id")String id);

    public Welcome getWelBytitle(@Param("Welcome_titleone") String title);
}
