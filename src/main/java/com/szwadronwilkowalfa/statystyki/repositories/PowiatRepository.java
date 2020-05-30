package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.Powiat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowiatRepository extends CrudRepository<Powiat, String> {

}
