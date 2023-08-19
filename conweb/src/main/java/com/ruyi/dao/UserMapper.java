package com.ruyi.dao;

import com.ruyi.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //获取用户信息
    public List<User> getUserinfo();

    //插入用户信息
    public int insetUserInfo(User user);

    //更新用户信息
    public int updateUserInfo(User user);

    //删除用户信息
    public int deleteUserInfo(@Param("User_Id") String id);

    //查找用户信息（通过id）
    public User getUserInfoById(@Param("User_Id") String id);

    //查找用户信息（通过name）
    public User getUserInfoByname(String name);
}
