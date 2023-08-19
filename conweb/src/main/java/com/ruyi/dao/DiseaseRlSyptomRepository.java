package com.ruyi.dao;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.DiseaseRlSyptom;
import com.ruyi.Entity.Symptom;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlSyptomRepository extends Neo4jRepository<DiseaseRlSyptom,Long> {
    @Query("match (n:disease{name:{0}})-[:has_symptom]->(s:symptom)return s")
    List<Symptom> findByDis(String name);

    @Query("match (d:disease)-[:has_symptom]->(s:symptom{name:{0}}) return d")
    List<Disease> findBySym(String name);

    @Query("match (d:disease)-[:has_symptom]->(s:symptom{name:{0}}) with d match(d:disease)-[:has_symptom]->(s:symptom{name:{1}}) return d")
    List<Disease> findBySym2(String name1,String name2);

    @Query("match (d:disease)-[:has_symptom]->(s:symptom{name:{0}}) with d match(d:disease)-[:has_symptom]->(s:symptom{name:{1}}) with d match(d:disease)-[:has_symptom]->(s:symptom{name:{2}}) return d")
    List<Disease> findBySym3(String name1,String name2,String name3);
}
