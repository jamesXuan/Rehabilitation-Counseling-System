package com.ruyi.controller;


import com.ruyi.Entity.Department;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.dao.EquimentRepository;
import com.ruyi.service.DepartmentEntityService;
import com.ruyi.service.DiseaseEntityService;
import com.ruyi.service.EquimentEntityService;
import com.ruyi.service.FindRelationshipService;
import com.ruyi.util.SideContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Slf4j
public class ContextController {
    @Autowired
    DiseaseEntityService diseaseEntityService;
    @Autowired
    FindRelationshipService findRelationshipService;
    @Autowired
    EquimentEntityService equimentEntityService;
    @Autowired
    DepartmentEntityService departmentEntityService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ConsulMapper consulMapper;
    @RequestMapping("/article")
    public String display(Model model,
                          @RequestParam("type") String type,
                          @RequestParam("dis") String dis,
                          @RequestParam("eqp")String eqp){

        //疾病详情页
        if(type.equals("1")|type.equals("3")|type.equals("4")){
            model.addAttribute("disEntity",diseaseEntityService.getByname(dis));
            //model.addAttribute("drug",findRelationshipService.DisGetDrug(dis));
            //model.addAttribute("check",findRelationshipService.DisGetCheck(dis));
            List<String> assitCheck = Arrays.asList(diseaseEntityService.getByname(dis).getAuxiliaryExam().
                    replace("[","").replace("]","").replace("\'","").split("。"));
            model.addAttribute("assitCheck",assitCheck);
            List<String> bodyCheck = Arrays.asList(diseaseEntityService.getByname(dis).getPhysicalExam().
                    replace("[","").replace("]","").replace("\'","").split("。"));
            model.addAttribute("bodyCheck",bodyCheck);
            List<String> sport = Arrays.asList(diseaseEntityService.getByname(dis).getSportTherapy().
                    replace("[","").replace("]","").replace("\"","").split(","));
            model.addAttribute("sport",sport);
            List<String> physics = Arrays.asList(diseaseEntityService.getByname(dis).getPhysiotherapy().
                    replace("[","").replace("]","").replace("\"","").split(","));
            model.addAttribute("physics",physics);
            //model.addAttribute("symptom",findRelationshipService.DisGetSyptom(dis));
            model.addAttribute("department",findRelationshipService.DisGetDepartment(dis));
            model.addAttribute("type",type);
            System.out.println(diseaseEntityService.getDatatype());
        }

        //设备详情页
        if(type.equals("2")){
            model.addAttribute("disEntity",equimentEntityService.getByname(dis));
            System.out.println(equimentEntityService.getByname(dis));
            model.addAttribute("type",type);
        }

        //侧边栏内容
//        Map<Object,Object> map=SideContext.sideContent(departmentEntityService,articleMapper,consulMapper);
//        model.addAttribute("department",map.get("department"));
//        model.addAttribute("article",map.get("article"));
//        model.addAttribute("consult",map.get("consult"));
//        System.out.println(map.get("consult"));

        return "article";
    }

}
