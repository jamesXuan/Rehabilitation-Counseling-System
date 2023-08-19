package com.ruyi.service;


import com.ruyi.Entity.Department;
import com.ruyi.dao.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentEntityService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getAll(){
        Iterable<Department> all = departmentRepository.findAll();
        List<Department> departmentsEntity = (List<Department>) all;
        return departmentsEntity;
    }

    public Department getByName(String name){
        return departmentRepository.getByName(name);
    }
}
