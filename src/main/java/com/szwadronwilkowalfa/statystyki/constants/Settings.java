package com.szwadronwilkowalfa.statystyki.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Settings {
    @Value("${external.resource.url.cancer.data}")
    String cancerDataUrl;

    public String getDefault(UrlType urlType) {
        switch (urlType){
            case CANCER:
                return cancerDataUrl;
            default:
                return "";
        }
    }

    public String getCancerDataUrl() {
        return cancerDataUrl;
    }
}
