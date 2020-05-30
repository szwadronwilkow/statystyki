package com.szwadronwilkowalfa.statystyki.helpers;

import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CancerDataHelper {

    private static final Logger log = LoggerFactory.getLogger(CancerDataHelper.class);

    public static List<CancerRecord> loadDataFromUrlResource(String urlString) throws IOException {
        URL url = new URL(urlString);
        List<CancerRecord> records = new ArrayList<>();
        try (InputStream stream = url.openStream();
             Scanner s = new Scanner(stream)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] split = line.split(";");
                try {
                    CancerRecord record = new CancerRecord();
                    record.setRok(Integer.parseInt(split[0]));
                    record.setPowiat(new Powiat(split[0], null));
                    record.setPlec(split[2].toUpperCase());
                    record.setIcd10(split[3].toUpperCase());
                    record.setLiczba(Integer.parseInt(split[4]));
                    records.add(record);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return records;
    }

}
