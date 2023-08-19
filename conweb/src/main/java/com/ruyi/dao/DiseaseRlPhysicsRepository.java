package com.ruyi.dao;

import com.ruyi.Entity.Physics;
import com.ruyi.Entity.Sport;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlPhysicsRepository extends Neo4jRepository<DiseaseRlPhysicsRepository,Long> {
    @Query("match (n:disease{name:{0}})-[:need_physics]->(p:physics)return p")
    List<Physics> findByDis(String name);
}
