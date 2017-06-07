package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.archsystemsinc.pqrs.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
