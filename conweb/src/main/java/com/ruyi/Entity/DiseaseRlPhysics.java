package com.ruyi.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "physicsCure")
public class DiseaseRlPhysics implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Disease disease;

    @EndNode
    private Physics physics;

    @Property
    private String relation;
}
