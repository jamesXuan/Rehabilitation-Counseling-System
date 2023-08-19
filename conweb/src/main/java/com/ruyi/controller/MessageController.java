package com.ruyi.controller;

import com.ruyi.Entity.*;
import com.ruyi.dao.ConsulMapper;
import com.ruyi.dao.UserMapper;
import com.ruyi.service.DiseaseEntityService;
import com.ruyi.service.EquimentEntityService;
import com.ruyi.service.FindRelationshipService;
import com.ruyi.service.SymptomEntityService;
import com.ruyi.util.Ahocorasick;
import com.ruyi.util.IDUtils;
import com.ruyi.util.Msg;
import com.ruyi.util.QuestionParse;
import org.ahocorasick.trie.Emit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    Ahocorasick ahocorasick;
    QuestionParse questionParse;
    @Autowired
    DiseaseEntityService diseaseEntityService;
    @Autowired
    FindRelationshipService findRelationshipService;
    @Autowired
    SymptomEntityService symptomEntityService;
    @Autowired
    EquimentEntityService equimentEntityService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ConsulMapper consulMapper;
    //存储问题（记录历史答案对，存入当前答案对），答案键值对数组
    List<Msg> msg = new ArrayList<>();
    //答案模板构造
    String result;
    @RequestMapping("/toconsulindex")
    public String toconsulindex(HttpSession session){
        if(session.getAttribute("usersession")==null)
            return "redirect:/tologin";
        else
            return "redirect:/consulindex";
    }

    @RequestMapping("/consulindex")
    public String message(Model model,HttpSession session){
        Msg msg=new Msg();
        msg.setAnsw("hi!");
        msg.setQues("hello~");
        String username=session.getAttribute("usersession").toString();
        User user = userMapper.getUserInfoByname(username);
        model.addAttribute("user",user);
        //String[] str={"你好啊！","再见~"};
        model.addAttribute("msg",msg);
        //System.out.println(session.getAttribute("usersession"));
        return"consul";
    }

    //咨询
    @RequestMapping("/consul")
    @ResponseBody
    public Msg consul(Model model,@RequestParam(value = "getContext")String getContext){
        String[] str={"你好啊！","再见~"};
        String ans = "";
        //问题-答案对
        Msg msg1=new Msg();
        //model.addAttribute("msg",str);
        System.out.println("====================进入方法===============================");
        //疾病分词
//        List<String> diswords= new ArrayList<String>();
//        for(int i=0;i<diseaseEntityService.getAll().size();i++)
//            diswords.add(diseaseEntityService.getAll().get(i).getName());
        List<String> diswords = diseaseEntityService.getAll().stream().map(Disease::getName).collect(Collectors.toList());
        System.out.println("===================疾病分词===============================");
        //症状分词
//        List<String> symword= new ArrayList<String>();
//        for(int i=0;i<symptomEntityService.getAll().size();i++)
//            symword.add(symptomEntityService.getAll().get(i).getName());
        List<String> symword = symptomEntityService.getAll().stream().map(Symptom::getName).collect(Collectors.toList());
        System.out.println("===================症状分词===============================");
        //设备分词
//        List<String> eqpword= new ArrayList<String>();
//        for(int i=0;i<equimentEntityService.getAll().size();i++)
//            eqpword.add(equimentEntityService.getAll().get(i).getName());
        List<String> eqpword =equimentEntityService.getAll().stream().map(Equiment::getName).collect(Collectors.toList());
        System.out.println("===================设备分词===============================");
        //分词
        //System.out.println(jiebaUtils.getWordSplit(getContext));
        //疾病实体判断
        List<Emit> disemits = ahocorasick.dismatch(diswords,getContext);
        System.out.println("=======================疾病匹配==================================");
        //设备实体判断
        List<Emit> eqpemits = ahocorasick.eqpmatch(eqpword,getContext);
        System.out.println("=======================设备匹配==================================");
        //症状实体判断
        List<Emit> symemits = ahocorasick.symmatch(symword,getContext);
        System.out.println("=======================症状匹配==================================");
        //获取疾病的问答问题类型
        List<String> dis_ques = questionParse.parse("disease",getContext);
        //没有匹配到答案
        if(dis_ques.isEmpty()&&disemits.isEmpty()&&symemits.isEmpty()&&eqpemits.isEmpty())
            ans=ans+commonAnsGenerator();
        //疾病相关答案构造
        for(int i = 0;i<disemits.size();i++) {
            //找不到该问题的分类时，返回疾病简介
            if(dis_ques.isEmpty()&&!disemits.isEmpty()) {
                ans = ans + link_url(disemits.get(i).getKeyword(),"dis");
                continue;
            }
            //遍历疾病问题类别数组，回答用户提出的问题
            for (int j = 0; j < dis_ques.size(); j++) {
                //查询结果
                if (dis_ques.get(j).equals("drug"))
                    ans = ans + str_drug(findRelationshipService.DisGetDrug(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("symptom"))
                    ans = ans + str_symptom(findRelationshipService.DisGetSyptom(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("department"))
                    ans = ans + str_department(findRelationshipService.DisGetDepartment(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("check"))
                    ans = ans + str_check(findRelationshipService.DisGetCheck(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("sport"))
                    ans = ans + str_sport(findRelationshipService.DisGetSport(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("physics"))
                    ans = ans + str_physics(findRelationshipService.DisGetPhysics(disemits.get(i).getKeyword()));
                if (dis_ques.get(j).equals("afterCare"))
                    ans = ans + diseaseEntityService.getByname(disemits.get(i).getKeyword()).getAfterCure();
                if (dis_ques.get(j).equals("attention"))
                    ans = ans + diseaseEntityService.getByname(disemits.get(i).getKeyword()).getAttention();
                if (dis_ques.get(j).equals("cause"))
                    ans = ans + diseaseEntityService.getByname(disemits.get(i).getKeyword()).getCause();
            }
        }
        //设备相关答案构造
        for(int i = 0;i<eqpemits.size();i++) {
            //找不到该问题分类时，返回设备简介
            if(dis_ques.isEmpty()&&!eqpemits.isEmpty()) {
                ans = ans + link_url(eqpemits.get(i).getKeyword(),"equiment");
                continue;
            }
            for (int j = 0; j < dis_ques.size(); j++) {
                Equiment equ ;
                equ = equimentEntityService.getByname(eqpemits.get(i).getKeyword());
                if (dis_ques.get(j).equals("sport"))
                    ans = ans + str_eqpsport(equ.getName(),equ.getSuitTrain());
            }
        }
        //判断该症状可能是哪种疾病
        if(!symemits.isEmpty())
            ans = ans+str_disease(findRelationshipService.SymGetDiease(symemits),symemits);
        //System.out.println(findRelationshipService.SymGetDiease(symemits));
        //返回答案
        msg1.setQues(getContext);
        msg1.setAnsw(ans);
        msg.add(msg1);
        System.out.println(msg);
        //model.addAttribute("msg",msg1);
        return msg1;
    }

    //用户基本信息获取
    @RequestMapping("/info")
    @ResponseBody
    public User userinfo(@RequestParam(value = "User_name")String username){
        System.out.println(username);
        User userInfo = userMapper.getUserInfoByname(username);
        System.out.println(userInfo);
        return  userInfo;
    }
    //保存咨询记录
    @RequestMapping("/saveRecord")
    @ResponseBody
    public void saveRecord(@RequestParam("data")String data){
        System.out.println(msg);
        Date time =new Date();
        String record ="";

        //list转为字符串存储
        for(int i=0;i<msg.size();i++) {
            if(i+1==msg.size()){
                record=record+msg.get(i).getQues()+"ques-ans";
                record=record+msg.get(i).getAnsw();
            }
            else{
                record=record+msg.get(i).getQues()+"ques-ans";
                record=record+msg.get(i).getAnsw()+"-q&a-";
            }
        }
        Consul consul=new Consul();
        consul.setRecord_Id(IDUtils.getId());
        consul.setUser_Id(userMapper.getUserInfoByname(data).getUser_Id());
        consul.setRecord_context(record);
        consul.setRecord_Time(time);
        consul.setRecord_title("");
        consul.setRecord_topic("问诊");
        consulMapper.addConsulInfo(consul);
        //清空记录
        for(int i=0;i<msg.size();i++) {
            msg.remove(i);
        }
        //字符串转为list
        List<Msg> rec = new ArrayList<>();
        String[] qa =record.split("-q&a-");

        for(int i=0;i<qa.length;i++){
            Msg msgrecord =new Msg();
            msgrecord.setQues(qa[i].split("ques-ans")[0]);
            msgrecord.setAnsw(qa[i].split("ques-ans")[1]);
            rec.add(msgrecord);
        }

    }
    //药品模板
    public String str_drug(List<Drug> list){
        String str="推荐以下药物：<br>";
        for(int i = 0;i<list.size();i++)
                str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //科室模板
    public String str_department(List<Department> list){
        String str="相关科室：<br>";
        for(int i = 0;i<list.size();i++)
            str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //运动治疗模板
    public String str_sport(List<Sport> list){
        String str="推荐以下运动训练：<br>";
        for(int i = 0;i<list.size();i++)
            str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //物理治理模板
    public String str_physics(List<Physics> list){
        String str="推荐以下物理疗法：<br>";
        for(int i = 0;i<list.size();i++)
            str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //症状模板
    public String str_symptom(List<Symptom> list){
        String str="可能有以下症状：<br>";
        for(int i = 0;i<list.size();i++)
            str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //检查模板
    public String str_check(List<Check> list){
        String str="可能需要下列检查：<br>";
        for(int i = 0;i<list.size();i++)
            str=str+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        return str;
    }
    //未知答案
    public String commonAnsGenerator(){
        String[] common ={"对不起，这超出了我的认知范围","我不知道你在说什么，你可以详细说说吗？","这个目前我也不太清楚","这涉及到了我的认知盲区","你可以换一种方式表达，或许我就可以给你解答了"};
        Random ran =new Random();
        return common[ran.nextInt(common.length)];
    }
    //疾病详情
    public String link_url(String name,String type){
        if(type.equals("dis"))
            return "点击以下链接进入详情页:<br><a href="+"/article?type=1&dis="+name+"&eqp="+">"+name+"</a><br><br>";
        else if(type.equals("equiment"))
            return "点击以下链接进入详情页:<br><a href="+"/article?type=2&dis="+name+"&eqp="+">"+name+"</a><br><br>";
        else
            return null;
    }
    //疾病模板
    public String str_disease(List<Disease> list,List<Emit> sympt){
        String sympt_str="";
        String str="可能为以下疾病：<br>";
        String dis="";
        //去重
        HashSet<String> set = new HashSet<String>(list.size());
        for(int i=0;i<sympt.size();i++)
            set.add(sympt.get(i).getKeyword());
        for(String s:set) {
                sympt_str = sympt_str +"," + s;
        }
        sympt_str=sympt_str.replaceFirst(",","");
        for(int i = 0;i<list.size();i++)
            dis=dis+String.valueOf(i+1)+". "+list.get(i).getName()+"<br>";
        str=sympt_str+"症状"+str+dis;
        return str;
    }
    //设备训练模板
    public String str_eqpsport(String name,String suitTrain){
        return name+"适合做以下练习：<br>\n"+suitTrain+"<br><br>";
    }
}
