package com.ruyi.dao;

import com.ruyi.Entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    //获取文章页面信息
    public List<Article> getarticleinfo();

    //插入新文章信息
    public int insetarticleInfo(Article article);


    //删除文章页面信息
    public int deletearticleInfo(@Param("Article_Id") String id);

    //查询文章（按类别）
    public Article getarticleBytype(@Param("Article_type") String type);

    public Article getarticleBytitle(@Param("Article_Id") String id);
}
