package com.ruyi.util;

import org.ahocorasick.trie.Emit;

import java.lang.reflect.Array;
import java.util.*;
/*2022.9.27
输入：实体大类，问句
答案模板，按问题关键词进行分类
输出；对应的分类
 */
public class QuestionParse {
    static String[] symptom={"症状", "表征", "现象", "症候", "表现"};
    static String[] department={"属于什么科", "属于", "什么科", "科室"};
    static String[] sport={"锻炼", "运动", "练习", "动作"};
    static String[] physics={"物理手段"};
    static String[] drug={"药", "药品", "用药", "胶囊", "口服液", "炎片"};
    static String[] check={"检查", "检查项目", "查出", "检查", "测出", "试出"};
    static String[] equiment={"仪器","设备","装备","设施"};
    static String[] cause={"原因","成因", "为什么", "怎么会", "怎样才", "咋样才", "怎样会", "如何会", "为啥", "为何", "如何才会", "怎么才会", "会导致", "会造成"};
    static String[] afterCare={"预后情况"};
    static String[] attention={"注意","在意","提防","谨防"};
    static String[] part={"部分","部位","关节"};
    public static List<String> parse(String type,String question){
        List<String> ques = new ArrayList<>();
        //疾病-症状
        if(type.equals("disease")&&isInQues(symptom,question))
            ques.add("symptom");
        //疾病-科室
        if(type.equals("disease")&&isInQues(department,question))
            ques.add("department");
        //疾病-运动治疗
        if(type.equals("disease")&&isInQues(sport,question))
            ques.add("sport");
        //疾病-物理治疗
        if(type.equals("disease")&&isInQues(physics,question))
            ques.add("physics");
        //疾病-药物
        if(type.equals("disease")&&isInQues(drug,question))
            ques.add("drug");
        //疾病-检查
        if(type.equals("disease")&&isInQues(check,question))
            ques.add("check");
        //疾病-设备
        if(type.equals("disease")&&isInQues(equiment,question))
            ques.add("equipment");
        //疾病-原因
        if(type.equals("disease")&&isInQues(cause,question))
            ques.add("cause");
        //疾病-预后
        if(type.equals("disease")&&isInQues(afterCare,question))
            ques.add("afterCare");
        //疾病-注意
        if(type.equals("disease")&&isInQues(attention,question))
            ques.add("attention");
        //设备-部位
        if(type.equals("equipment")&&isInQues(part,question))
            ques.add("sport");
        return ques;
    }
    public static boolean isInQues(String[] str,String question){
        for(int i =0;i<str.length;i++)
            if(question.contains(str[i]))
                return true;
        return false;
    }
}
