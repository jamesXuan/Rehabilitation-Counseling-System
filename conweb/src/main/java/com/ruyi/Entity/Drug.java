package com.ruyi.Entity;

import com.sun.org.apache.xpath.internal.objects.XString;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;
@Data
@NodeEntity(label = "drug")
public class Drug implements Serializable {
    @Property
    private String name;

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String desc;
}
