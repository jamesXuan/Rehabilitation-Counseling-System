package com.ruyi.service;


import com.ruyi.Entity.Disease;
import com.ruyi.dao.DiseaseRlDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Dis_depService {
    @Autowired
    DiseaseRlDepartmentRepository diseaseRlDepartmentRepository;

    public List<Disease> get(String department){
        List<Disease> diseaseEntity = diseaseRlDepartmentRepository.getDis(department);
        return diseaseEntity;
    }
}
