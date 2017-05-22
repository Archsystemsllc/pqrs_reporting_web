package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.archsystemsinc.pqrs.model.EPZipCodeCount;

public interface EPZipCodeCountRepository extends JpaRepository<EPZipCodeCount, Integer>, JpaSpecificationExecutor<EPZipCodeCount> {
}
