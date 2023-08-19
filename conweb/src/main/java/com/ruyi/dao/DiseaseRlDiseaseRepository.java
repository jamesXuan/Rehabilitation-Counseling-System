package com.ruyi.dao;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.DiseaseRlDisease;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRlDiseaseRepository extends Neo4jRepository<DiseaseRlDisease,Long> {



}
