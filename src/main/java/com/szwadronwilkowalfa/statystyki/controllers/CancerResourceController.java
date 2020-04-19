package com.szwadronwilkowalfa.statystyki.controllers;

import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.helpers.CancerDataHelper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.repositories.CancerRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CancerResourceController {

    Logger log = LoggerFactory.getLogger(CancerResourceController.class);

    @Autowired
    CancerRecordRepository cancerRecordRepository;

    @Value("${external.resource.url.cancer.data}")
    String cancerDataUrl;

    WebStatus status = WebStatus.READY;

    @GetMapping("/cancer/status")
    public String getStatus() {
        return status.toString();
    }

    @GetMapping("/cancer/clear")
    public void clear() {
        cancerRecordRepository.deleteAll();
    }

    @GetMapping("/cancer/read")
    public void loadJsonDataFromUrlResource() {
        if (status != WebStatus.READY) {
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
        status = WebStatus.READY;
    }

}
