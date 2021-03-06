package com.szwadronwilkowalfa.statystyki.repositories;

import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancerRecordRepository extends CrudRepository<CancerRecord, Long> {
    List<CancerRecord> findByRok(int rok);

    @Query("SELECT DISTINCT rok FROM CANCER_RECORD ORDER BY rok ASC")
    List<Integer> findDistinctRok();

    //@Query("SELECT c FROM CANCER_RECORD c WHERE c.rok = ?1 AND c.powiat = ?2")
    List<CancerRecord> findByRokAndPowiat(int year, Powiat powiat);
}
