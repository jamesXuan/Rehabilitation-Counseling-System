package com.ruyi.controller;

import com.alibaba.fastjson.JSON;
import com.ruyi.dao.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;

@Controller
public class TestController {
    @Autowired
    ArticleMapper articleMapper;
    @RequestMapping("/t1")
    @ResponseBody
    public String test(Model model){
        model.addAttribute("data",articleMapper.getarticleBytype("日常").getArticle_context());
        String data = JSON.toJSONString(articleMapper.getarticleBytype("日常").getArticle_context());
        return data;
    }

    @RequestMapping("/test")
    public String t(){
        return "test/test";
    }
}
