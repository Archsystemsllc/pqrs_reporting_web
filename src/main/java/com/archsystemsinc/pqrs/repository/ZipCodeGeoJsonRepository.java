/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import org.springframework.data.repository.CrudRepository;

import com.archsystemsinc.pqrs.model.ZipCodeGeoJSON;

/**
 * @author MurugarajKandaswamy
 *
 */
public interface ZipCodeGeoJsonRepository extends CrudRepository<ZipCodeGeoJSON, Integer> {

	ZipCodeGeoJSON findByZipCode(final String zipCode);
	
}
