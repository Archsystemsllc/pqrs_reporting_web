package com.archsystemsinc.pqrs.repository;

import com.archsystemsinc.pqrs.model.StateGeoJson;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by MurugarajKandaswam on 5/12/2017.
 */
public interface StateGeoJsonRepository extends CrudRepository<StateGeoJson, Integer>{ 

	StateGeoJson findByStateName(final String stateName);
	
}
