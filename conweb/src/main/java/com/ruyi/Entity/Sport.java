package com.ruyi.Entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;
@Data
@NodeEntity(label = "sport")
public class Sport implements Serializable {

   //实体 名称
   @Property
   private String name;

   //自带的 id
   @Id
   @GeneratedValue
   private Long id;

   @Property
   private String desc;
}
