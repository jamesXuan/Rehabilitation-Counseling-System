package com.ruyi.dao;

import com.ruyi.Entity.EquimentRlSport;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquimentRlSportRepository extends Neo4jRepository<EquimentRlSport,Long> {
}
