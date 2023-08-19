package com.ruyi.service;

import com.ruyi.Entity.Sport;
import com.ruyi.dao.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService {
    @Autowired
    SportRepository sportRepository;

    public List<Sport> getall(){
        return (List<Sport>) sportRepository.findAll();
    }
}
