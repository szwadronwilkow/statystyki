package com.szwadronwilkowalfa.statystyki.controllers;

import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.helpers.CancerDataHelper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CancerResourceController {

    Logger log = LoggerFactory.getLogger(CancerResourceController.class);

    @Value("${external.resource.url.cancer.data}")
    String cancerDataUrl;

    WebStatus status = WebStatus.READY;

    @GetMapping("/cancer/status")
    public String getStatus() {
        return status.toString();
    }

    @GetMapping("/cancer/read")
    public List<CancerRecord> loadJsonDataFromUrlResource() {
        status = WebStatus.LOADING;
        List<CancerRecord> cancerRecords = CancerDataHelper.loadJsonDataFromUrlResource(cancerDataUrl);
        return cancerRecords;
    }


}
