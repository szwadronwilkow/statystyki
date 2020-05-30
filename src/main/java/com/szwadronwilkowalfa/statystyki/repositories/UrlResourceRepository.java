package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.UrlResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlResourceRepository extends CrudRepository<UrlResource, Long> {
    UrlResource findByName(String name);
}
