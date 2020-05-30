package com.szwadronwilkowalfa.statystyki.services;

import com.szwadronwilkowalfa.statystyki.constants.Settings;
import com.szwadronwilkowalfa.statystyki.constants.UrlType;
import com.szwadronwilkowalfa.statystyki.model.UrlResource;
import com.szwadronwilkowalfa.statystyki.repositories.UrlResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    UrlResourceRepository urlResourceRepository;

    @Autowired
    Settings settings;

    public UrlResource findByName(String name) {
        return urlResourceRepository.findByName(name);
    }

    public void save(UrlResource resource) {
        urlResourceRepository.save(resource);
    }

    public String findByNameOrDefault(UrlType urlType) {
        UrlResource urlResource = urlResourceRepository.findByName(urlType.name());
        if (urlResource == null){
            return settings.getDefault(urlType);
        }
        return urlResource.getUrl();
    }

    public void update(UrlType urlType, String url) {
        UrlResource resource = urlResourceRepository.findByName(urlType.name());
        if (resource == null){
            resource = new UrlResource(urlType.name(), url);
        } else{
            resource.setUrl(url);
        }
        urlResourceRepository.save(resource);
    }
}
