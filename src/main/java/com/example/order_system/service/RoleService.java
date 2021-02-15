package com.example.order_system.service;

import com.example.order_system.domain.Role;
import com.example.order_system.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


}
