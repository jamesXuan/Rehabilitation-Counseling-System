package com.ruyi.service;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Equiment;
import com.ruyi.dao.EquimentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EquimentEntityService {
    @Autowired
    EquimentRepository equimentRepository;

    public List<Equiment> getAll(){
        Iterable<Equiment> all = equimentRepository.findAll();
        List<Equiment> equimentsEntity = (List<Equiment>) all;
        return equimentsEntity;
    }

    public Equiment getByname(String name){

        return equimentRepository.findByname(name);
    }
}
