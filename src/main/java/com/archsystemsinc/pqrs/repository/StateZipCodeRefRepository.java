/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.archsystemsinc.pqrs.model.StateZipCodeRef;

/**
 * @author MurugarajKandaswam
 *
 */
public interface StateZipCodeRefRepository extends CrudRepository<StateZipCodeRef, Integer>{
	List<StateZipCodeRef> findByStateName(String stateName);
	
	List<StateZipCodeRef> findByStateCode(String stateCode);
}
