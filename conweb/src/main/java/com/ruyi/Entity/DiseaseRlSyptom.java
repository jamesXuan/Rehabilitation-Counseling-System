package com.ruyi.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "hasSyptom")
public class DiseaseRlSyptom implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Disease disease;

    @EndNode
    private Symptom syptom;

    @Property
    private String relation;
}
