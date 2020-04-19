package com.szwadronwilkowalfa.statystyki.controllers;

import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.helpers.CancerDataHelper;
import com.szwadronwilkowalfa.statystyki.helpers.PowiatDataHelper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import com.szwadronwilkowalfa.statystyki.repositories.CancerRecordRepository;
import com.szwadronwilkowalfa.statystyki.repositories.PowiatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PowiatController {

    Logger log = LoggerFactory.getLogger(PowiatController.class);
    WebStatus status = WebStatus.READY;

    @Autowired
    PowiatRepository powiatRepository;

    @GetMapping("/powiat/clear")
    public void clear() {
        powiatRepository.deleteAll();
    }

    @GetMapping("/powiat/read")
    public void loadJsonDataFromUrlResource() {
        if (status != WebStatus.READY) {
            return;
        }
        if (powiatRepository.count() > 0) {
            return;
        }

        status = WebStatus.LOADING;
        List<Powiat> cancerRecords;
        try {
            cancerRecords = PowiatDataHelper.loadDataFromUrlResource("/data/teryt.csv");
            powiatRepository.saveAll(cancerRecords);
        } catch (Exception e) {
            log.error(e.getMessage());
            status = WebStatus.ERROR;
        }
        status = WebStatus.READY;
    }

}
