package com.ruyi.service;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Sport;
import com.ruyi.dao.DiseaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DiseaseEntityService {
    @Autowired
    DiseaseRepository diseaseRepository;

    public List<Disease> getAll() {
        Iterable<Disease> all = diseaseRepository.findAll();
        List<Disease> diseasesEntity = (List<Disease>) all;
        return diseasesEntity;
    }
    //按名称查询
    public Disease getByname(String name){

        return diseaseRepository.findByname(name);
    }
    //模糊查询
    public List<Disease> likegetByname(String str){
        List<Disease> diseases = diseaseRepository.likeFindByname(str);
        return diseases;
    }

    public List<Object> getDatatype(){
        return diseaseRepository.getDatatype();
    }
    //创建疾病节点
    public void insernode(String str){
        diseaseRepository.insernode(str);
    }
    //添加疾病属性
    public void insertinfo(String name,
                           String desc,
                           String cause,
                           String complication,
                           String consultation,
                           String epidemiological,
                           String injection,
                           String pathogenesis,
                           String physiotherapy,
                           String sportTherapy,
                           String afterCure,
                           String attention,
                           String auxiliaryExam,
                           String clinFeatures,
                           String clinicalTreatment,
                           String diagnosis,
                           String medicalHistory,
                           String natureDisCourse,
                           String physicalExam,
                           String potentialDanger,
                           String redSignal,
                           String riskFactor,
                           String surgeon,
                           int disId){
        //diseaseRepository.insertinfo(key,vaule);
        diseaseRepository.insertProperty(name,
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
}
