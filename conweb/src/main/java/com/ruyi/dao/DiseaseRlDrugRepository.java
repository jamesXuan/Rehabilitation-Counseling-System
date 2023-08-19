package com.ruyi.dao;

import com.ruyi.Entity.DiseaseRlDrug;
import com.ruyi.Entity.Drug;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlDrugRepository extends Neo4jRepository<DiseaseRlDrug,Long> {
    @Query("match (n:disease{name:{0}})-[:need_drug]->(d:drug)return d")
    List<Drug> findByDis(String name);
}
