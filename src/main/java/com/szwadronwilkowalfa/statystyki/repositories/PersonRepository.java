package com.szwadronwilkowalfa.statystyki.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.szwadronwilkowalfa.statystyki.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
	
	List<Person> findAll();

}
