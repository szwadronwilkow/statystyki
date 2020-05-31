package com.szwadronwilkowalfa.statystyki.data;

import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import com.szwadronwilkowalfa.statystyki.repositories.CancerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CancerStatistics {

    public static final String CANCER_YEARS = "cancer_years";
    private final CancerRecordRepository cancerRecordRepository;

    @Autowired
    public CancerStatistics(CancerRecordRepository cancerRecordRepository) {
        this.cancerRecordRepository = cancerRecordRepository;
    }

    @Cacheable(CANCER_YEARS)
    public Optional<PlotData> getPlotDataPerYears(int... years){
        List<Integer> yearsList = null;
        if (years == null || years.length==0){
            yearsList = cancerRecordRepository.findDistinctRok();
            if (CollectionUtils.isEmpty(yearsList)) {
                return Optional.empty();
            }
        }
        PlotData plotData = new PlotData("Cancer cases");
        for (int year : yearsList) {
            List<CancerRecord> cancerRecords = cancerRecordRepository.findByRok(year);
            if (CollectionUtils.isEmpty(cancerRecords)) {
                continue;
            }
            plotData.addLabel(String.valueOf(year));
            plotData.addValue(aggregateByLiczba(cancerRecords));
        }
        if (plotData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(plotData);
    }

    private BigDecimal aggregateByLiczba(List<CancerRecord> rekordy) {
        if (!CollectionUtils.isEmpty(rekordy)){
            return new BigDecimal(rekordy.stream().mapToLong(CancerRecord::getLiczba).sum());
        }
        return BigDecimal.ZERO;
    }

    public Optional<PlotData> getPlotDataPerYearsPerLand(Powiat powiat) {
        List<Integer> yearsList = cancerRecordRepository.findDistinctRok();
        if (CollectionUtils.isEmpty(yearsList)) {
            return Optional.empty();
        }
        PlotData plotData = new PlotData("Cancer cases per " + powiat.getNazwa());
        for (int year : yearsList) {
            List<CancerRecord> cancerRecords = cancerRecordRepository.findByRokAndPowiat(year, powiat);
            if (CollectionUtils.isEmpty(cancerRecords)) {
                continue;
            }
            plotData.addLabel(String.valueOf(year));
            plotData.addValue(aggregateByLiczba(cancerRecords));
        }
        if (plotData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(plotData);
    }
}
