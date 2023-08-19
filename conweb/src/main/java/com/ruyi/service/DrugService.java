package com.ruyi.service;

import com.ruyi.Entity.Drug;
import com.ruyi.dao.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DrugService {
    @Autowired
    DrugRepository drugRepository;

    public List<Drug> getall(){
        return (List<Drug>) drugRepository.findAll();
    }
}
