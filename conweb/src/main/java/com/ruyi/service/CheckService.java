package com.ruyi.service;

import com.ruyi.Entity.Check;
import com.ruyi.dao.CheckRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CheckService {
    @Autowired
    CheckRepository checkRepository;
public List<Check> getall(){
    Iterable<Check> all = checkRepository.findAll();
    return (List<Check>) all;
}
}
