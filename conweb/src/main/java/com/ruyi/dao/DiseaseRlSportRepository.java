package com.ruyi.dao;

import com.ruyi.Entity.Check;
import com.ruyi.Entity.DiseaseRlSport;
import com.ruyi.Entity.Sport;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlSportRepository extends Neo4jRepository<DiseaseRlSport,Long> {
    @Query("match (n:disease{name:{0}})-[:need_sport]->(s:sport)return s")
    List<Sport> findByDis(String name);
}
