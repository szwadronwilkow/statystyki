package com.szwadronwilkowalfa.statystyki.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szwadronwilkowalfa.statystyki.model.CancerRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CancerDataHelper {

    public static List<CancerRecord> loadJsonDataFromUrlResource(String paginatedUrl){
        List<CancerRecord> cancerList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        String url = paginatedUrl;
        while(true) {
            String jsonString = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONObject links = jsonObject.getJSONObject("links");
            String next = links.getString("next");
            String last = links.getString("last");

            cancerList.addAll(loadPageData(restTemplate, url));
            if (next.equals(last)) {
                break;
            }
            url = next;
            if (url.startsWith("http:")){
                url = StringUtils.replace(url, "http:", "https:");
            }
        }
        return cancerList;
    }

    private static Collection<CancerRecord> loadPageData(RestTemplate restTemplate, String nextUrl) {
        List<CancerRecord> cancerRecords = new ArrayList<>();
        String jsonString = restTemplate.getForObject(nextUrl, String.class);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("data");
        for (int i=0; i<array.length(); i++) {
            try {
                JSONObject attributes = array.getJSONObject(i).getJSONObject("attributes");
                CancerRecord cr = new ObjectMapper().readValue(attributes.toString(), CancerRecord.class);
                cancerRecords.add(cr);
            } catch (JsonProcessingException e) {
                // do nothing
            }
        }
        return cancerRecords;
    }
}
