package com.ruyi.dao;

import com.ruyi.Entity.Consul;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConsulMapper {
    //获取咨询记录信息
    public List<Consul> getConsulinfo();

    //删除咨询记录信息
    public int deleteConsulInfo(@Param("Consul_Id") String id);

    //查找咨询记录信息（通过id）
    public Consul getConsulInfoById(@Param("Consul_Id") String id);

    //查找咨询记录信息（通过topic）
    public List<Consul> getConsulInfoBytopic(String topic);

    //查找咨询记录信息（通过topic）
    public Consul getConsulInfoBytitle(String title);
    //插入咨询记录
    public int addConsulInfo(Consul consul);
}
