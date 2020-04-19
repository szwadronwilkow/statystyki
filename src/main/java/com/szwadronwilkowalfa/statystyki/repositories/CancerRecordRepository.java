package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancerRecordRepository extends CrudRepository<CancerRecord, Long> {
}
