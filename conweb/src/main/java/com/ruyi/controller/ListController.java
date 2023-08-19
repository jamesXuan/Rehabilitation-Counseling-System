package com.ruyi.controller;

import com.ruyi.Entity.Article;
import com.ruyi.Entity.Department;
import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Equiment;
import com.ruyi.dao.ArticleMapper;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.dao.DepartmentRepository;
import com.ruyi.service.DepartmentEntityService;
import com.ruyi.service.Dis_depService;
import com.ruyi.service.DiseaseEntityService;
import com.ruyi.service.EquimentEntityService;
import com.ruyi.util.SideContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class ListController {
    @Autowired
    EquimentEntityService equimentEntityService;
    @Autowired
    DiseaseEntityService diseaseEntityService;
    @Autowired
    Dis_depService dis_depService;
    @Autowired
    DepartmentEntityService departmentEntityService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ConsulMapper consulMapper;
    public int pageSize = 7;
    public int totalRecords ;
    public int currentPage ;
    public int pageCount;

    @RequestMapping("/list")
    public String datadisplay(Model model,
                              @RequestParam("type") String type,
                              @RequestParam("pageN") String pageN,
                              @RequestParam("dep") String dep,
                              @RequestParam("str") String str){
        //代码优化：是否可以合并？
        //疾病页面（全部）
        if(type.equals("1")){
            model.addAttribute("list_block","疾病<span>您当前的位置：<a href=\"/index\">首页><a href=\"#\">列表页></a></span>");
            //获取总记录数
            totalRecords = diseaseEntityService.getAll().size();
            pageCount = (totalRecords%pageSize==0)?(totalRecords/pageSize):(totalRecords/pageSize+1);
            if(pageN==null) {
                currentPage = 1;
            }
            else{
                currentPage = Integer.parseInt(pageN);
                if(currentPage>pageCount)
                    currentPage=pageCount;
                else if(currentPage<1)
                    currentPage=1;
            }
            //显示数据
            List<Disease> equiments =new ArrayList<>();
            for(int i = (currentPage-1)*pageSize;i < currentPage*pageSize; i++) {
                //model.addAttribute("",equimentEntityService.getAll().get(i).getName());
                //model.addAttribute("",equimentEntityService.getAll().get(i).getDesc());
                equiments.add(diseaseEntityService.getAll().get(i));
                if(i==totalRecords-1)
                    break;
            }
            model.addAttribute("equiment",equiments);
            model.addAttribute("currdentPage",currentPage);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("type",type);
            model.addAttribute("dep",dep);
            model.addAttribute("str",str);

        }
        //设备页面
        if(type.equals("2")){
            model.addAttribute("list_block","设备<span>您当前的位置：<a href=\"/index\">首页></a><a href=\"#\">列表页></a></span>");
            //获取设备信息
            //获取总记录数
            totalRecords = equimentEntityService.getAll().size();
            pageCount = (totalRecords%pageSize==0)?(totalRecords/pageSize):(totalRecords/pageSize+1);
            if(pageN==null) {
                currentPage = 1;
            }
            else{
                currentPage = Integer.parseInt(pageN);
                if(currentPage>pageCount)
                    currentPage=pageCount;
                else if(currentPage<1)
                    currentPage=1;
            }
        //显示数据
        List<Equiment> equiments =new ArrayList<>();
        for(int i = (currentPage-1)*pageSize;i < currentPage*pageSize; i++) {
            //model.addAttribute("",equimentEntityService.getAll().get(i).getName());
            //model.addAttribute("",equimentEntityService.getAll().get(i).getDesc());
            equiments.add(equimentEntityService.getAll().get(i));
            if(i==totalRecords-1)
                break;
        }
        model.addAttribute("equiment",equiments);
        model.addAttribute("currdentPage",currentPage);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("type",type);
        model.addAttribute("dep",dep);
        model.addAttribute("str",str);
        }
        //疾病页面（按科室划分）
        if(type.equals("3")){

            model.addAttribute("list_block",dep+"<span>您当前的位置：<a href=\"/index\">首页><a href=\"#\">列表页></a></span>");
            //获取总记录数
            totalRecords = dis_depService.get(dep).size();
            pageCount = (totalRecords%pageSize==0)?(totalRecords/pageSize):(totalRecords/pageSize+1);
            if(pageN==null) {
                currentPage = 1;
            }
            else{
                currentPage = Integer.parseInt(pageN);
                if(currentPage>pageCount)
                    currentPage=pageCount;
                else if(currentPage<1)
                    currentPage=1;
            }
            //显示数据
            List<Disease> diseases =new ArrayList<>();
            for(int i = (currentPage-1)*pageSize;i < currentPage*pageSize; i++) {
                //model.addAttribute("",equimentEntityService.getAll().get(i).getName());
                //model.addAttribute("",equimentEntityService.getAll().get(i).getDesc());
                diseases.add(dis_depService.get(dep).get(i));
                if(i==totalRecords-1)
                    break;
            }
            model.addAttribute("equiment",diseases);
            model.addAttribute("currdentPage",currentPage);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("type",type);
            model.addAttribute("dep",dep);
            model.addAttribute("str",str);
        }
        //搜索（如过搜索不到，界面该如何显示）
        if(type.equals("4")){
            //汉字解码
            try {
                str = java.net.URLDecoder.decode(str,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //System.out.println(str);
            model.addAttribute("list_block","搜索<span>您当前的位置：<a href=\"/index\">首页><a href=\"#\">搜索页></a></span>");
            totalRecords=diseaseEntityService.likegetByname(str).size();
            System.out.println(totalRecords);
            if(totalRecords==0) {
                currentPage=1;
                pageCount=0;
                List<Disease> equiments = new ArrayList<>();
                model.addAttribute("equiment", equiments);
            }
            else {
                pageCount = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
                if (pageN == null) {
                    currentPage = 1;
                } else {
                    currentPage = Integer.parseInt(pageN);
                    if (currentPage > pageCount)
                        currentPage = pageCount;
                    else if (currentPage < 1)
                        currentPage = 1;
                }
                //显示数据
                List<Disease> equiments = new ArrayList<>();
                for (int i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
                    //model.addAttribute("",equimentEntityService.getAll().get(i).getName());
                    //model.addAttribute("",equimentEntityService.getAll().get(i).getDesc());
                    equiments.add(diseaseEntityService.likegetByname(str).get(i));
                    if (i == totalRecords - 1)
                        break;
                }
                model.addAttribute("equiment", equiments);
            }

            model.addAttribute("currdentPage", currentPage);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("type", type);
            model.addAttribute("dep", dep);
            model.addAttribute("str", str);
        }
        //文章
        if(type.equals("5")){
            model.addAttribute("list_block","文章<span>您当前的位置：<a href=\"/index\">首页><a href=\"#\">文章页></a></span>");
            totalRecords=articleMapper.getarticleinfo().size();

            if(totalRecords==0) {
                System.out.println(totalRecords);
                currentPage=1;
                pageCount=0;
                List<Article> articles = new ArrayList<>();
                model.addAttribute("article", articles);
            }
            else {
                pageCount = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
                if (pageN == null) {
                    currentPage = 1;
                } else {
                    currentPage = Integer.parseInt(pageN);
                    if (currentPage > pageCount)
                        currentPage = pageCount;
                    else if (currentPage < 1)
                        currentPage = 1;
                }


                //显示数据
                List<Article> articles = new ArrayList<>();
                for (int i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
                    //model.addAttribute("",equimentEntityService.getAll().get(i).getName());
                    //model.addAttribute("",equimentEntityService.getAll().get(i).getDesc());
                    articles.add(articleMapper.getarticleinfo().get(i));

                    if (i == totalRecords - 1)
                        break;
                }

                model.addAttribute("equiment", articles);
            }
            model.addAttribute("currdentPage", currentPage);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("type", type);
            model.addAttribute("dep", dep);
            model.addAttribute("str", str);
        }
        //侧边栏内容
//        Map<Object,Object> map= SideContext.sideContent(departmentEntityService,articleMapper,consulMapper);
//        model.addAttribute("department",map.get("department"));
//        model.addAttribute("article",map.get("article"));
//        model.addAttribute("consult",map.get("consult"));


        return "list";
    }
}
