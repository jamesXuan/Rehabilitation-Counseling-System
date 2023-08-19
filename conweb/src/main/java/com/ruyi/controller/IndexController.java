package com.ruyi.controller;

import com.ruyi.Entity.Consul;
import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Sport;
import com.ruyi.Entity.Welcome;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.dao.SportRepository;
import com.ruyi.dao.WelcomeMapper;
import com.ruyi.service.DiseaseEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.query.GraphRepositoryQuery;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class IndexController {


    @Autowired
    private DiseaseEntityService diseaseEntityService;
    @Autowired
    WelcomeMapper welcomeMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ConsulMapper consulMapper;
    @RequestMapping("/index")
    public String data(Model model){
        List<Welcome> nav_list = welcomeMapper.getWelinfo();
        model.addAttribute("nav_list",nav_list);

        //疾病
        model.addAttribute("module_one","<h2>脊柱疾病</h2>");
        model.addAttribute("module_one_desc","脊柱病就是脊柱的骨质、椎间盘、韧带、肌肉发生病变，进而压迫、牵引刺激脊髓、脊神经、血管、植物神经从而出现复杂多样的症状。");
        List<Disease> dis =new ArrayList<>();
        for(int i =2 ;i<6;i++) {
            dis.add(diseaseEntityService.getAll().get(i));
        }
        model.addAttribute("dis",dis);


        //数据展示栏1
        int size = diseaseEntityService.getAll().size();
        System.out.println(size);
        model.addAttribute("dis_data",size);
        model.addAttribute("dis_count","疾病种类");
        model.addAttribute("dis_context","丰富的疾病种类");

        //数据展示栏2
        model.addAttribute("visit_data","23");
        model.addAttribute("visit_count","访问量");
        model.addAttribute("visit_context","每天有很多人访问该网站");

        //数据展示栏3
        int size1 = articleMapper.getarticleinfo().size();
        model.addAttribute("xx_data",size1);
        model.addAttribute("xx_count","康复文章");
        model.addAttribute("xx_context","覆盖了各种各样的康复文章");

        //数据展示栏4
        int size2 = consulMapper.getConsulinfo().size();
        model.addAttribute("xxx_data",size2);
        model.addAttribute("xxx_count","咨询记录");
        model.addAttribute("xxx_context","拥有丰富的咨询记录");

        //每日推荐
        model.addAttribute("Daily_Recommendation","每日推荐");
        model.addAttribute("Recommendation_context","此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本");

        String context ="此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本此处是文本";
        //咨询记录
        //博客1
        model.addAttribute("one_blog_date","2017-03-25");
        model.addAttribute("one_blog_title","颈椎病");
        model.addAttribute("one_blog_context","患者：你好，医生，我最近颈椎有点不舒服，又酸又疼，都不太敢低头。医生：出现这个症状多久了");
        //博客2
        model.addAttribute("two_blog_date","2018-08-15");
        model.addAttribute("two_blog_title","年轻运动员腰背痛");
        model.addAttribute("two_blog_context","患者： 你好，医生，我最近腰背有点酸疼，晚上睡觉翻身的时候都会痛。医生： 这种情况持续多久了");
        //博客3
        Consul coninfo = consulMapper.getConsulInfoBytitle("马凡综合征咨询");
        model.addAttribute("three_blog_date","2022-09-22");
        model.addAttribute("three_blog_title",coninfo.getRecord_title());
        model.addAttribute("three_blog_context",coninfo.getRecord_context());
        return "index";
    }

}
