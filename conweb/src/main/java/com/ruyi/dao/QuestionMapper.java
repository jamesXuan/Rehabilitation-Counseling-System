package com.ruyi.dao;

import com.ruyi.Entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    //获取问题信息
    public List<Question> getQuesinfo();

    //插入新的问题信息
    public int insetQuesInfo(Question ques);

    //更新问题信息
    public int updateQuesInfo(Question ques);

    //删除问题信息信息
    public int deleteQuesInfo(@Param("Question_Id") String id);

    //查找问题信息信息（通过type）
    public Question getQuesInfoBytype(@Param("Question_type") String type);

    //查找问题信息信息（通过id）
    public Question getQuesInfoById(@Param("Question_Id") String id);
}
