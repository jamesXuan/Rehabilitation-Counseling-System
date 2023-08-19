package com.ruyi.service;

import com.ruyi.Entity.*;
import com.ruyi.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.ahocorasick.trie.Emit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FindRelationshipService {
    @Autowired
    DiseaseRlDepartmentRepository diseaseRlDepartmentRepository;
    @Autowired
    DiseaseRlDrugRepository diseaseRlDrugRepository;
    @Autowired
    DiseaseRlCheckRepository diseaseRlCheckRepository;
    @Autowired
    DiseaseRlPhysicsRepository diseaseRlPhysicsRepository;
    @Autowired
    DiseaseRlSportRepository diseaseRlSportRepository;
    @Autowired
    DiseaseRlSyptomRepository diseaseRlSyptomRepository;
    //通过科室查疾病
    public List<Disease> DepGetDis(String department){
        List<Disease> diseaseEntity = diseaseRlDepartmentRepository.getDis(department);
        return diseaseEntity;
    }

    //通过疾病查药物
    public List<Drug> DisGetDrug(String disease){
        List<Drug> drugs = diseaseRlDrugRepository.findByDis(disease);
        return drugs;
    }

    //通过疾病了解所需检查
    public List<Check> DisGetCheck(String disease){
        List<Check> checks = diseaseRlCheckRepository.findByDis(disease);
        return checks;
    }

    //通过疾病了解运动方式
    public List<Sport> DisGetSport(String disease){
        List<Sport> sports = diseaseRlSportRepository.findByDis(disease);
        return sports;
    }
    //疾病所需的物理治疗
    public List<Physics> DisGetPhysics(String disease){
        List<Physics> physics = diseaseRlPhysicsRepository.findByDis(disease);
        return physics;
    }
    //通过疾病查症状
    public List<Symptom> DisGetSyptom(String disease){
        List<Symptom> symptoms = diseaseRlSyptomRepository.findByDis(disease);
        return symptoms;
    }
    //通过疾病查科室
    public List<Department> DisGetDepartment(String disease){
        List<Department> departments = diseaseRlDepartmentRepository.findByDis(disease);
        return departments;
    }
    //通过症状查疾病
    public List<Disease> SymGetDiease(List<Emit> symptoms){
        List<Disease> diseases =new ArrayList<>();
        if(symptoms.size()==1) {
             diseases = diseaseRlSyptomRepository.findBySym(symptoms.get(0).getKeyword());
            return diseases;
        }
        if(symptoms.size()==2){
             diseases = diseaseRlSyptomRepository.findBySym2(symptoms.get(0).getKeyword(),symptoms.get(1).getKeyword());
            return diseases;
        }
        if(symptoms.size()==3){
            diseases = diseaseRlSyptomRepository.findBySym3(symptoms.get(0).getKeyword(),symptoms.get(1).getKeyword(),symptoms.get(2).getKeyword());
            return diseases;
        }
        return diseases;
    }

    //查询两个实体间是否存在疾病-科室的关系
    public Boolean queryrel(String head,String tail){
        return diseaseRlDepartmentRepository.queryrel(head,tail);
    }
    //添加两个实体间疾病-科室的关系
    public void addrel(String head,String tail){
         diseaseRlDepartmentRepository.addrel(head,tail);
    }
}
