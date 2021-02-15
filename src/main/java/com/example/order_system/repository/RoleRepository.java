package com.example.order_system.repository;

import com.example.order_system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);

}
