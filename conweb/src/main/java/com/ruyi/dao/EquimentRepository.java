package com.ruyi.dao;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Equiment;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquimentRepository extends Neo4jRepository<Equiment,Long> {

    @Query("match(n:equiment{name:{0}}) return n")
    Equiment findByname(String name);
}
