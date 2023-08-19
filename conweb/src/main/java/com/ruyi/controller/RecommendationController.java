package com.ruyi.controller;


import com.ruyi.Entity.Article;
import com.ruyi.Entity.Department;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.service.DepartmentEntityService;
import com.ruyi.util.SideContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class RecommendationController {
    @Autowired
    DepartmentEntityService departmentEntityService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ConsulMapper consulMapper;
    @RequestMapping("/recommendation")
    public String display(Model model, @RequestParam("title") String id){
        System.out.println(articleMapper.getarticleBytitle(id));
        model.addAttribute("art",articleMapper.getarticleBytitle(id));

        //侧边栏内容
//        Map<Object,Object> map= SideContext.sideContent(departmentEntityService,articleMapper,consulMapper);
//        model.addAttribute("department",map.get("department"));
//        model.addAttribute("article",map.get("article"));
//        model.addAttribute("consult",map.get("consult"));
        return "recommendation";
    }
}
