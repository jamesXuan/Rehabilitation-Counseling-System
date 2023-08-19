package com.ruyi.controller;

import com.ruyi.Entity.Article;
import com.ruyi.Entity.Consul;
import com.ruyi.Entity.Department;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.service.DepartmentEntityService;
import com.ruyi.util.Msg;
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
public class HistoryConsulController {
    @Autowired
    DepartmentEntityService departmentEntityService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ConsulMapper consulMapper;
    @RequestMapping("/consulHistory")
    public String display(Model model, @RequestParam("type") String type){
        model.addAttribute("type",type);
        Consul coninfo = consulMapper.getConsulInfoBytitle("马凡综合征咨询");
        List<Msg> msg = new ArrayList<>();
        String[] qa =coninfo.getRecord_context().split("-q&a-");

        for(int i=0;i<qa.length;i++){
            Msg msgrecord =new Msg();
            msgrecord.setQues(qa[i].split("ques-ans")[0]);
            msgrecord.setAnsw(qa[i].split("ques-ans")[1]);
            msg.add(msgrecord);
        }
        model.addAttribute("coninfo",msg);
        //侧边栏内容
//        Map<Object,Object> map= SideContext.sideContent(departmentEntityService,articleMapper,consulMapper);
//        model.addAttribute("department",map.get("department"));
//        model.addAttribute("article",map.get("article"));
//        model.addAttribute("consult",map.get("consult"));
        return "consulHistory";
    }


}
