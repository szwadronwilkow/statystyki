package com.szwadronwilkowalfa.statystyki.helpers;

import com.szwadronwilkowalfa.statystyki.model.Powiat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PowiatDataHelper {

    private static final Logger log = LoggerFactory.getLogger(PowiatDataHelper.class);

    @Cacheable("powiat_data")
    public static List<Powiat> loadDataFromUrlResource(String path) {
        List<Powiat> powiaty = new ArrayList<>();

        try (InputStream stream = new ClassPathResource(path).getInputStream();
             Scanner s = new Scanner(stream)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] split = line.split(";");
                Powiat powiat = new Powiat();
                powiat.setTeryt(split[0].trim());
                powiat.setNazwa(split[1].trim());
                powiaty.add(powiat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return powiaty;
    }
}
