package com.ruyi.dao;

import com.ruyi.Entity.Check;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRepository extends Neo4jRepository<Check,Long> {

}
