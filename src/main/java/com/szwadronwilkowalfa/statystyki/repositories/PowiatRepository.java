package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.Powiat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowiatRepository extends CrudRepository<Powiat, String> {

    @Query("SELECT p from POWIAT p WHERE LENGTH(p.teryt)<=2 ORDER BY p.nazwa")
    List<Powiat> findLands();

    Powiat findByNazwa(String name);

    Optional<Powiat> findByTeryt(String teryt);
}
