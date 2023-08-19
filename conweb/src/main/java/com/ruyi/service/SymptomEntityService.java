package com.ruyi.service;

import com.ruyi.Entity.Disease;
import com.ruyi.Entity.Symptom;
import com.ruyi.dao.SyptomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SymptomEntityService {

    @Autowired
    SyptomRepository syptomRepository;

    public List<Symptom> getAll() {
        Iterable<Symptom> all = syptomRepository.findAll();
        List<Symptom> diseasesEntity = (List<Symptom>) all;
        return diseasesEntity;
    }
}
