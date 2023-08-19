package com.ruyi.Entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;
@Data
@NodeEntity(label = "disease")
public class Disease implements Serializable {
    //疾病简介
    @Property
    private String desc;
    //致病原因
    @Property
    private String cause;
    //注意事项
    @Property
    private String attention;
    //预后
    @Property
    private String afterCure;
    //疾病名称
    @Property
    private String name;
    //疾病id
    @Property
    private int disId;
    //id
    @Id
    @GeneratedValue
    private Long id;
    //流行病学
    @Property
    private String Epidemiological;
    //发病机制
    @Property
    private String Pathogenesis;
    //危险因素
    @Property
    private String riskFactor;
    //临床特征
    @Property
    private String clinFeatures;
    //自然病程
    @Property
    private String natureDisCourse;
    //鉴别诊断
    @Property
    private String diagnosis;
    //病史
    @Property
    private String medicalHistory;
    //体格检查
    @Property
    private String physicalExam;
    //辅助检查
    @Property
    private String auxiliaryExam;
    //潜在危险
    @Property
    private String potentialDanger;
    //红色信号
    @Property
    private String redSignal;
    //一般临床处理
    @Property
    private String clinicalTreatment;
    //运动治疗
    @Property
    private String SportTherapy;
    //物理疗法
    @Property
    private String Physiotherapy;
    //注射
    @Property
    private String Injection;
    //外科
    @Property
    private String surgeon;
    //会诊
    @Property
    private String Consultation;
    //治疗并发症
    @Property
    private String Complication;

}
