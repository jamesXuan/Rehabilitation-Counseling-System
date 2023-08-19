package com.ruyi.controller;

import com.ruyi.Entity.*;
import com.ruyi.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DataController {
    @Autowired
    DiseaseEntityService diseaseEntityService;
    @Autowired
    FindRelationshipService findRelationshipService;
    @Autowired
    SymptomEntityService symptomEntityService;
    @Autowired
    DepartmentEntityService departmentEntityService;
    @Autowired
    EquimentEntityService equimentEntityService;
    @Autowired
    CheckService checkService;
    @Autowired
    DrugService drugService;
    @Autowired
    SportService sportService;
    @Autowired
    PhysicsService physicsService;

    @RequestMapping("/importdata")
    public String display(Model model){
        model.addAttribute("type",diseaseEntityService.getDatatype());
        int size=diseaseEntityService.getAll().size();
        System.out.println(size);
        return "importdata";
    }

    @RequestMapping("/getdisease")
    @ResponseBody
    public List<Triads> getdisease(@RequestParam("Entity_type") String type){
        System.out.println(type);
        List<Triads> str = new ArrayList<>();
        if(type.equals("disease")){
            List<Disease> disinfo = diseaseEntityService.getAll();
            for(int i=0;i<disinfo.size();i++){
                str.add(new Triads(disinfo.get(i).getName(),"简介",disinfo.get(i).getDesc()));
                str.add(new Triads(disinfo.get(i).getName(),"病因",disinfo.get(i).getCause()));
                str.add(new Triads(disinfo.get(i).getName(),"注意",disinfo.get(i).getAttention()));
                str.add(new Triads(disinfo.get(i).getName(),"预后",disinfo.get(i).getAfterCure()));
                str.add(new Triads(disinfo.get(i).getName(),"流行病学",disinfo.get(i).getEpidemiological()));
                str.add(new Triads(disinfo.get(i).getName(),"发病机制",disinfo.get(i).getPathogenesis()));
                str.add(new Triads(disinfo.get(i).getName(),"危险因素",disinfo.get(i).getRiskFactor()));
                str.add(new Triads(disinfo.get(i).getName(),"临床特征",disinfo.get(i).getClinFeatures()));
                str.add(new Triads(disinfo.get(i).getName(),"自然病程",disinfo.get(i).getNatureDisCourse()));
                str.add(new Triads(disinfo.get(i).getName(),"鉴别诊断",disinfo.get(i).getDiagnosis()));
                str.add(new Triads(disinfo.get(i).getName(),"病史",disinfo.get(i).getMedicalHistory()));
                str.add(new Triads(disinfo.get(i).getName(),"体格检查",disinfo.get(i).getPhysicalExam()));
                str.add(new Triads(disinfo.get(i).getName(),"辅助检查",disinfo.get(i).getAuxiliaryExam()));
                str.add(new Triads(disinfo.get(i).getName(),"潜在危险",disinfo.get(i).getPotentialDanger()));
                str.add(new Triads(disinfo.get(i).getName(),"红色信号",disinfo.get(i).getRedSignal()));
                str.add(new Triads(disinfo.get(i).getName(),"临床处理",disinfo.get(i).getClinicalTreatment()));
                str.add(new Triads(disinfo.get(i).getName(),"运动治疗",disinfo.get(i).getSportTherapy()));
                str.add(new Triads(disinfo.get(i).getName(),"物理治疗",disinfo.get(i).getPhysiotherapy()));
                str.add(new Triads(disinfo.get(i).getName(),"注射",disinfo.get(i).getInjection()));
                str.add(new Triads(disinfo.get(i).getName(),"外科",disinfo.get(i).getSurgeon()));
                str.add(new Triads(disinfo.get(i).getName(),"会诊",disinfo.get(i).getConsultation()));
                str.add(new Triads(disinfo.get(i).getName(),"治疗并发症",disinfo.get(i).getComplication()));
            }
            //属性和关系之间的分隔符
            str.add(new Triads("","",""));

                for(int i =0;i<disinfo.size();i++){
                    List<Department> departments = findRelationshipService.DisGetDepartment(disinfo.get(i).getName());
                    for(Department dep:departments)
                        str.add(new Triads(disinfo.get(i).getName(),dep.getName(),"属于"));
                    List<Physics> physics = findRelationshipService.DisGetPhysics(disinfo.get(i).getName());
                    for(Physics phy:physics)
                        str.add(new Triads(disinfo.get(i).getName(),phy.getName(),"物理治疗"));
                    List<Sport> sports = findRelationshipService.DisGetSport(disinfo.get(i).getName());
                    for(Sport spo:sports)
                        str.add(new Triads(disinfo.get(i).getName(),spo.getName(),"运动治疗"));
                    List<Check> checks = findRelationshipService.DisGetCheck(disinfo.get(i).getName());
                    for(Check check:checks)
                        str.add(new Triads(disinfo.get(i).getName(),check.getName(),"需要检查"));
                    List<Symptom> symptoms = findRelationshipService.DisGetSyptom(disinfo.get(i).getName());
                    for(Symptom sym:symptoms)
                        str.add(new Triads(disinfo.get(i).getName(),sym.getName(),"拥有症状"));
                    List<Drug> drugs = findRelationshipService.DisGetDrug(disinfo.get(i).getName());
                    for(Drug drug:drugs)
                        str.add(new Triads(disinfo.get(i).getName(),drug.getName(),"需要药物"));
                }
        }
        else if(type.equals("check")){
            List<Check> check = checkService.getall();
            for(Check checks:check){
                str.add(new Triads(checks.getName(),"简介",checks.getDesc()));
            }
        }
        else if(type.equals("symptom")){
            List<Symptom> syminfo = symptomEntityService.getAll();
            for(Symptom sym:syminfo){
                str.add(new Triads(sym.getName(),"介绍",sym.getDesc()));
            }
        }
        else if(type.equals("drug")){
            List<Drug> drugs = drugService.getall();
            for(Drug drug:drugs){
                str.add(new Triads(drug.getName(),"简介",drug.getDesc()));
            }
        }
        else if(type.equals("department")){
            List<Department> depinfo = departmentEntityService.getAll();
            for(Department dep:depinfo){
                str.add(new Triads(dep.getName(),"简介",dep.getDesc()));
            }
        }
        else if(type.equals("equiment")){
            List<Equiment> equimentinfo = equimentEntityService.getAll();
            for(Equiment equ:equimentinfo) {
                str.add(new Triads(equ.getName(), "简介", equ.getDesc()));
                str.add(new Triads(equ.getName(),"适用症",equ.getSuitDisease()));
                str.add(new Triads(equ.getName(),"适用部位",equ.getSuitPart()));
                str.add(new Triads(equ.getName(),"适用训练",equ.getSuitTrain()));
            }
        }
        else if(type.equals("sport")){
            List<Sport> sportinfo = sportService.getall();
            for(Sport sport:sportinfo){
                str.add(new Triads(sport.getName(),"简介",sport.getDesc()));
            }
        }
        else if(type.equals("physics")){
            List<Physics> physics = physicsService.getall();
            for(Physics physic:physics){
                str.add(new Triads(physic.getName(),"简介",physic.getDesc()));
            }
        }
        else{
            System.out.println("暂无信息");
        }

        //str.add("hello",'','');
        return str;
    }

    @RequestMapping("/addTriadsinfo")
    public String addinfo(@RequestParam("entity1")String entity1,
                          @RequestParam("rel")String rel,
                          @RequestParam("entity2")String entity2,
                          Model model
                          ){
        //创建关系(默认是已有的label)
        if(rel.equals("belongs_to")){
            Disease dis = diseaseEntityService.getByname(entity1);
            Department dep = departmentEntityService.getByName(entity2);
            if(dep==null||dis==null)
                System.out.println("暂无该节点");
            else//查询是否有该关系
                {
                    if(!findRelationshipService.queryrel(entity1,entity2)){
                        findRelationshipService.addrel(entity1,entity2);
                    }
                    else{
                        System.out.println("该关系已存在！！！");
                    }

            }
        }
        else if(rel.equals("has_symptom")){

        }
        else if(rel.equals("need_check")){

        }
        else if(rel.equals("need_drug")){

        }
        else if(rel.equals("need_physics")){

        }
        else if(rel.equals("need_sport")){

        }
        //创建节点（如何区分疾病与疾病之间并发症的关系？）
        else if(entity1.equals(entity2)) {
            diseaseEntityService.insernode(entity1);
            int size=diseaseEntityService.getAll().size();
            System.out.println(size);
            //初始化个节点属性，给disId赋值
            diseaseEntityService.insertinfo(entity1,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    size+1);
        }
        else{
            Disease dname = diseaseEntityService.getByname(entity1);
            System.out.println(dname);
            String desc=dname.getDesc();
            String cause=dname.getCause();
            String complication=dname.getComplication();
            String consultation=dname.getConsultation();
            String epidemiological=dname.getEpidemiological();
            String injection=dname.getInjection();
            String pathogenesis=dname.getPathogenesis();
            String physiotherapy=dname.getPhysiotherapy();
            String sportTherapy=dname.getSportTherapy();
            String afterCure=dname.getAfterCure();
            String attention=dname.getAttention();
            String auxiliaryExam=dname.getAuxiliaryExam();
            String clinFeatures=dname.getClinFeatures();
            String clinicalTreatment=dname.getClinicalTreatment();
            String diagnosis=dname.getDiagnosis();
            String medicalHistory=dname.getMedicalHistory();
            String natureDisCourse=dname.getNatureDisCourse();
            String physicalExam=dname.getPhysicalExam();
            String potentialDanger=dname.getPotentialDanger();
            String redSignal=dname.getRedSignal();
            String riskFactor=dname.getRiskFactor();
            String surgeon=dname.getSurgeon();
            int disId=dname.getDisId();
            //判断哪个属性被修改
            if(rel.equals("desc")){
                desc=entity2;
            }
            if(rel.equals("cause")){
                cause=entity2;
            }
            if(rel.equals("complication")){
                complication=entity2;
            }
            if(rel.equals("consultation")){
                consultation=entity2;
            }
            if(rel.equals("epidemiological")){
                epidemiological=entity2;
            }
            if(rel.equals("injection")){
                injection=entity2;
            }
            if(rel.equals("pathogenesis")){
                pathogenesis=entity2;
            }
            if(rel.equals("physiotherapy")){
                physiotherapy=entity2;
            }
            if(rel.equals("sportTherapy")){
                sportTherapy=entity2;
            }
            if(rel.equals("afterCure")){
                afterCure=entity2;
            }
            if(rel.equals("attention")){
                attention=entity2;
            }
            if(rel.equals("auxiliaryExam")){
                auxiliaryExam=entity2;
            }
            if(rel.equals("clinFeatures")){
                clinFeatures=entity2;
            }
            if(rel.equals("clinicalTreatment")){
                clinicalTreatment=entity2;
            }
            if(rel.equals("diagnosis")){
                diagnosis=entity2;
            }
            if(rel.equals("medicalHistory")){
                medicalHistory=entity2;
            }
            if(rel.equals("natureDisCourse")){
                natureDisCourse=entity2;
            }
            if(rel.equals("physicalExam")){
                physicalExam=entity2;
            }
            if(rel.equals("potentialDanger")){
                potentialDanger=entity2;
            }
            if(rel.equals("redSignal")){
                redSignal=entity2;
            }
            if(rel.equals("riskFactor")){
                riskFactor=entity2;
            }
            if(rel.equals("surgeon")){
                surgeon=entity2;
            }

            diseaseEntityService.insertinfo(entity1,
                    desc,
                    cause,
                    complication,
                    consultation,
                    epidemiological,
                    injection,
                    pathogenesis,
                    physiotherapy,
                    sportTherapy,
                    afterCure,
                    attention,
                    auxiliaryExam,
                    clinFeatures,
                    clinicalTreatment,
                    diagnosis,
                    medicalHistory,
                    natureDisCourse,
                    physicalExam,
                    potentialDanger,
                    redSignal,
                    riskFactor,
                    surgeon,
                    disId);

        }

        return "redirect:/importdata";
    }
}
