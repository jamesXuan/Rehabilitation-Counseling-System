package com.ruyi.dao;

import com.ruyi.Entity.Symptom;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyptomRepository extends Neo4jRepository<Symptom,Long> {

}
