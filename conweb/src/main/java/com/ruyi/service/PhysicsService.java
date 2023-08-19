package com.ruyi.service;

import com.ruyi.Entity.Physics;
import com.ruyi.dao.PhysicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicsService {
    @Autowired
    PhysicsRepository physicsRepository;

    public List<Physics> getall(){
        return (List<Physics>) physicsRepository.findAll();
    }
}
