package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.archsystemsinc.pqrs.model.EPStateCount;

public interface EPStateCountRepository extends JpaRepository<EPStateCount, Integer>, JpaSpecificationExecutor<EPStateCount> {

}
