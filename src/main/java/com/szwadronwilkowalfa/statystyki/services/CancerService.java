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
import com.szwadronwilkowalfa.statystyki.repositories.UrlResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

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

    public void load() {
        if (status != WebStatus.IDLE) {
            return;
        }
        if (cancerRecordRepository.count() > 0) {
            return;
        }

        status = WebStatus.LOADING;
        List<CancerRecord> cancerRecords;
        try {
            cancerRecords = CancerDataHelper.loadDataFromUrlResource(getUrl());
            for (CancerRecord record : cancerRecords) {
                String teryt = record.getPowiat().getTeryt();
                record.setPowiat(entityManager.find(Powiat.class, teryt));
            }
            cancerRecordRepository.saveAll(cancerRecords);
        } catch (Exception e) {
            log.error(e.getMessage());
            status = WebStatus.ERROR;
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
