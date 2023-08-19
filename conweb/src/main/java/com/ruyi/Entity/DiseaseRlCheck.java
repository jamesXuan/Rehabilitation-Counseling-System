package com.ruyi.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "needCheck")
public class DiseaseRlCheck implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Disease disease;

    @EndNode
    private Check check;

    @Property
    private String relation;
}
