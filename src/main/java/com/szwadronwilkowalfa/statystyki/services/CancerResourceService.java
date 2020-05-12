package com.szwadronwilkowalfa.statystyki.services;

import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.helpers.CancerDataHelper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.repositories.CancerRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancerResourceService {

    Logger log = LoggerFactory.getLogger(CancerResourceService.class);

    @Autowired
    CancerRecordRepository cancerRecordRepository;

    @Value("${external.resource.url.cancer.data}")
    String cancerDataUrl;

    volatile WebStatus status = WebStatus.IDLE;

    public WebStatus getStatus() {
        return status;
    }

    public void clear() {
        cancerRecordRepository.deleteAll();
    }

    public long getSize(){
        return cancerRecordRepository.count();
    }

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
            cancerRecords = CancerDataHelper.loadDataFromUrlResource(cancerDataUrl);
            cancerRecordRepository.saveAll(cancerRecords);
        } catch (Exception e) {
            log.error(e.getMessage());
            status = WebStatus.ERROR;
        }
        status = WebStatus.DATA_EXISTS;
    }

}
