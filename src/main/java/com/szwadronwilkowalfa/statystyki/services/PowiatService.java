package com.szwadronwilkowalfa.statystyki.services;

import com.szwadronwilkowalfa.statystyki.constants.WebStatus;
import com.szwadronwilkowalfa.statystyki.helpers.PowiatDataHelper;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import com.szwadronwilkowalfa.statystyki.repositories.PowiatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowiatService {

    Logger log = LoggerFactory.getLogger(PowiatService.class);
    WebStatus status = WebStatus.IDLE;

    @Autowired
    PowiatRepository powiatRepository;

    public void clear() {
        powiatRepository.deleteAll();
    }

    public void load() {
        if (status != WebStatus.IDLE) {
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
        status = WebStatus.DATA_EXISTS;
    }

    public WebStatus getStatus() {
        return status;
    }

    public long getSize() {
        return powiatRepository.count();
    }
}
