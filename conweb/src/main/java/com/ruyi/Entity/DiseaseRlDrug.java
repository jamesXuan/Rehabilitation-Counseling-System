package com.ruyi.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "drugCure")
public class DiseaseRlDrug implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Disease disease;

    @EndNode
    private Drug drug;

    @Property
    private String relation;
}
