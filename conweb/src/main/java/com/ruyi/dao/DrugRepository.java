package com.ruyi.dao;

import com.ruyi.Entity.Drug;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends Neo4jRepository<Drug,Long> {

}
