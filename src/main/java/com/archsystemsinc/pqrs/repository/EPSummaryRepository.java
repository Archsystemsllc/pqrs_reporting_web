package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.archsystemsinc.pqrs.model.EPSummary;

public interface EPSummaryRepository extends JpaRepository<EPSummary, Integer>, JpaSpecificationExecutor<EPSummary> {

}
