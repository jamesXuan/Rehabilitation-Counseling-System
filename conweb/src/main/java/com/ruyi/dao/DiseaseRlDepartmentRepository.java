package com.ruyi.dao;

import com.ruyi.Entity.Department;
import com.ruyi.Entity.Disease;
import com.ruyi.Entity.DiseaseRlDepartment;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRlDepartmentRepository extends Neo4jRepository<DiseaseRlDepartment,Long> {


    @Query("MATCH(n:disease)-[:belongs_to]->(d:department{name:{0}})return n")
    List<Disease> getDis(String department);

    @Query("MATCH(n:disease{name:{0}})-[:belongs_to]->(d:department)return d")
    List<Department> findByDis(String disease);

    @Query("RETURN EXISTS( (:disease {name:{0}})-[:belongs_to]->(:department {name:{1}}) )")
    Boolean queryrel(String head,String tail);

    @Query("match(n:disease{name:{0}}),(d:department{name:{1}}) create (n)-[:belongs_to]->(d)")
    void addrel(String head,String tail);
}
