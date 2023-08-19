package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "equiment")
public class Equiment implements Serializable {
    //设备名称
    @Property
    private String name;
    //设备简介
    @Property
    private  String desc;
    @Id
    @GeneratedValue
    private Long id;

    //id
    @Property
    private String eqpId;

    //适用症
    @Property
    private String suitDisease;

    //适用部位
    @Property
    private String suitPart;

    //适用训练
    @Property
    private String suitTrain;

}
