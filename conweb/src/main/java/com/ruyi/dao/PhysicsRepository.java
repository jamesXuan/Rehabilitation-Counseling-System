package com.ruyi.dao;

import com.ruyi.Entity.Physics;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicsRepository extends Neo4jRepository<Physics,Long> {

}
