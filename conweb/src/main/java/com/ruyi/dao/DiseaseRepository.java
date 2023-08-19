package com.ruyi.dao;

import com.ruyi.Entity.Disease;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends Neo4jRepository<Disease,Long> {
    @Query("match(n:disease{name:{0}}) return n")
     Disease findByname(String name);

    @Query("match(n:disease) where n.name contains {0}return n")
    List<Disease> likeFindByname(String str);

    @Query("CALL db.labels()")
    List<Object> getDatatype();

    @Query("MERGE (n:disease{name:$str})")
    void insernode(@Param("str") String str);

    @Query("match(d:disease) where d.name=$name set d.desc=$desc, d.cause=$cause,d.Complication=$complication," +
            "d.Consultation=$consultation,d.Epidemiological=$epidemiological,d.Injection=$injection,d.Pathogenesis=$pathogenesis," +
            "d.Physiotherapy=$physiotherapy,d.SportTherapy=$sportTherapy,d.afterCure=$afterCure,d.attention=$attention,d.auxiliaryExam=$auxiliaryExam," +
            "d.clinFeatures=$clinFeatures,d.clinicalTreatment=$clinicalTreatment,d.diagnosis=$diagnosis,d.medicalHistory=$medicalHistory,d.natureDisCourse=$natureDisCourse," +
            "d.physicalExam=$physicalExam,d.potentialDanger=$potentialDanger,d.redSignal=$redSignal,d.riskFactor=$riskFactor,d.surgeon=$surgeon,d.disId=$disId")
    void insertProperty(@Param("name")String name,
                        @Param("desc")String desc,
                        @Param("cause")String cause,
                        @Param("complication")String complication,
                        @Param("consultation")String consultation,
                        @Param("epidemiological")String epidemiological,
                        @Param("injection")String injection,
                        @Param("pathogenesis")String pathogenesis,
                        @Param("physiotherapy")String physiotherapy,
                        @Param("sportTherapy")String sportTherapy,
                        @Param("afterCure")String afterCure,
                        @Param("attention")String attention,
                        @Param("auxiliaryExam")String auxiliaryExam,
                        @Param("clinFeatures")String clinFeatures,
                        @Param("clinicalTreatment")String clinicalTreatment,
                        @Param("diagnosis")String diagnosis,
                        @Param("medicalHistory")String medicalHistory,
                        @Param("natureDisCourse")String natureDisCourse,
                        @Param("physicalExam")String physicalExam,
                        @Param("potentialDanger")String potentialDanger,
                        @Param("redSignal")String redSignal,
                        @Param("riskFactor")String riskFactor,
                        @Param("surgeon")String surgeon,
                        @Param("disId")int disId
                        );
}
