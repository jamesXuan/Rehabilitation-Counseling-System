package com.ruyi.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "needEquiment")
public class EquimentRlSport implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Sport sport;

    @EndNode
    private Equiment equiment;

    @Property
    private String relation;
}
