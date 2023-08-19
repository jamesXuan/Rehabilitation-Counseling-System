package com.ruyi.dao;

import com.ruyi.Entity.Sport;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepository extends Neo4jRepository<Sport,Long> {

}
