package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
	
	List<Person> findAll();
	Person findByEmail(String email);
}
