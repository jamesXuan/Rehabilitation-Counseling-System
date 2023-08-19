package com.ruyi.dao;

import com.ruyi.Entity.Department;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends Neo4jRepository<Department,Long> {
        @Query("match(d:department{name:{0}}) return d")
        Department getByName(String name);
}
