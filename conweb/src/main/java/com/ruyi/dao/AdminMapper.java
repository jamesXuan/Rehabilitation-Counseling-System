package com.ruyi.dao;

import com.ruyi.Entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    //获取用户信息
    public List<Admin> getAdmininfo(int limit);

    //插入用户信息
    public int insetAdminInfo(Admin admin);

    //更新用户信息
    public int updateAdminInfo(Admin admin);

    //删除用户信息
    public int deleteAdminInfo(@Param("Admin_Id") String id);

    //查找用户信息（通过id）
    public Admin getAdminInfoById(@Param("Admin_Id") String id);

    //查找用户信息（通过name）
    public Admin getAdminInfoByname(String name);
}
