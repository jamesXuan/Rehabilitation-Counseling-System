package com.ruyi.Entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;
@Data
@NodeEntity(label = "department")
public class Department implements Serializable {
    @Property
    private String name;

    @Id
    @GeneratedValue
    private long id;

    @Property
    private String desc;
}
