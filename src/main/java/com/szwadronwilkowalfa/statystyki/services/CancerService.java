package com.szwadronwilkowalfa.statystyki.services;

import com.szwadronwilkowalfa.statystyki.constants.Settings;
import com.szwadronwilkowalfa.statystyki.constants.UrlType;
import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.data.CancerStatistics;
import com.szwadronwilkowalfa.statystyki.helpers.CancerDataHelper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import com.szwadronwilkowalfa.statystyki.model.UrlResource;
import com.szwadronwilkowalfa.statystyki.repositories.CancerRecordRepository;
import com.szwadronwilkowalfa.statystyki.repositories.PowiatRepository;
import com.szwadronwilkowalfa.statystyki.repositories.UrlResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancerService {

    Logger log = LoggerFactory.getLogger(CancerService.class);

    @Autowired
    CancerRecordRepository cancerRecordRepository;

    @Autowired
    UrlResourceRepository urlResourceRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PowiatRepository powiatRepository;

    @Autowired
    Settings settings;

    volatile WebStatus status = WebStatus.IDLE;

    public WebStatus getStatus() {
        return status;
    }

    public void clear() {
        cancerRecordRepository.deleteAll();
        status = WebStatus.IDLE;
    }

    public long getSize(){
        return cancerRecordRepository.count();
    }

    @CacheEvict(cacheNames = CancerStatistics.CANCER_YEARS, allEntries = true)
    public void evictCache(){}

    @Transactional
    public void load() {
        if (status != WebStatus.IDLE) {
            throw new RuntimeException("Loading in progress. Try Later.");
        }
        if (cancerRecordRepository.count() > 0) {
            throw new RuntimeException("Already loaded");
        }
        status = WebStatus.LOADING;
        List<CancerRecord> cancerRecords;
        String url = getUrl();
        try {
            cancerRecords = CancerDataHelper.loadDataFromUrlResource(url);
            List<CancerRecord> recordsToImport = new ArrayList<>();
            for (CancerRecord record : cancerRecords) {
                String teryt = record.getPowiat().getTeryt();
                Optional<Powiat> powiat = powiatRepository.findByTeryt(teryt);
                if (powiat.isEmpty()) {
                    log.error("Unable to find powiat for teryt="+teryt);
                    continue;
                }
                record.setPowiat(powiat.get());
                recordsToImport.add(record);
            }
            cancerRecordRepository.saveAll(recordsToImport);
        } catch (Exception e) {
            log.error(e.getMessage());
            status = WebStatus.IDLE;
            throw new RuntimeException("Unable to read " + url);
        }
        status = WebStatus.DATA_EXISTS;
    }

    public String getUrl() {
        UrlResource urlResource = urlResourceRepository.findByName(UrlType.CANCER.name());
        if (urlResource == null){
            urlResource = new UrlResource(UrlType.CANCER.name(), settings.getCancerDataUrl());
            urlResourceRepository.save(urlResource);
        }
        return urlResource.getUrl();
    }
}
