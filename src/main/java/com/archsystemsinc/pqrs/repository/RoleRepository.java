package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
