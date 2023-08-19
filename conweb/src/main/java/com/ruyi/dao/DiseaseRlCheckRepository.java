package com.ruyi.dao;

import com.ruyi.Entity.Check;
import com.ruyi.Entity.DiseaseRlCheck;
import com.ruyi.Entity.Drug;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlCheckRepository extends Neo4jRepository<DiseaseRlCheck,Long> {
    @Query("match (n:disease{name:{0}})-[:need_check]->(c:check)return c")
    List<Check> findByDis(String name);

    @Query("MATCH (n:disease{name:{0}})-[r:need_check]->(c:check{name:{1}}) RETURN r")
    String queryrel(String head,String tail);

}
