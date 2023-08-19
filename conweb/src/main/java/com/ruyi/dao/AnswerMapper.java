package com.ruyi.dao;

import com.ruyi.Entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface AnswerMapper {
    //获取欢迎页面信息
    public List<Answer> getAnsinfo();

    //插入新欢迎页面信息
    public int insetAnsInfo(Answer ans);

    //更新欢迎页面信息
    public int updateAnsInfo(Answer ans);

    //删除欢迎页面信息
    public int deleteAnsInfo(@Param("Answer_Id") String id);

    public Answer getAnsinfoById(@Param("Answer_Id")String id);

    public Answer getAnsinfoBytype(@Param("Answer_type") String type);
}
