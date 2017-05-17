package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.archsystemsinc.pqrs.model.EPCount;

public interface EPCountRepository extends JpaRepository<EPCount, Long>, JpaSpecificationExecutor<EPCount> {

}
