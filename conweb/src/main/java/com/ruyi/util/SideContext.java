package com.ruyi.util;

import com.ruyi.Entity.Article;
import com.ruyi.Entity.Consul;
import com.ruyi.Entity.Department;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.service.DepartmentEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SideContext {


    public static Map<Object,Object> sideContent(DepartmentEntityService departmentEntityService,ArticleMapper articleMapper,ConsulMapper consulMapper){
        Map<Object,Object> map =new HashMap<>();
        List<Department> dep = new ArrayList<>();
        List<Article> article = new ArrayList<>();
        List<Consul> con = new ArrayList<>();
        for(int i =1;i<7;i++) {
            dep.add(departmentEntityService.getAll().get(i));
            article.add(articleMapper.getarticleinfo().get(i));

        }
        System.out.println(dep);
        System.out.println(article);
        con.add(consulMapper.getConsulInfoBytitle("马凡综合征咨询"));
        map.put("department",dep);
        map.put("article",article);
        map.put("consult",con);

        return map;
    }
}
