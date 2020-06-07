package com.szwadronwilkowalfa.statystyki.views.components;

import com.szwadronwilkowalfa.statystyki.constants.UrlType;
import com.szwadronwilkowalfa.statystyki.services.CancerService;
import com.szwadronwilkowalfa.statystyki.services.UrlService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.Command;

public class CancerDataSpan extends Span {

    private final CancerService cancerService;
    private final UrlService urlService;

    public CancerDataSpan(CancerService cancerService, UrlService urlService){
        this.cancerService = cancerService;
        this.urlService = urlService;
        create();
    }

    private void create() {
        this.add(new Label("Cancer data: "));

        Button cancerLoadButton = createLoadButton();
        Button cancerClearButton = createClearButton();
        Button cancerDataSizeButton = createDataSizeButton();
        UrlField urlField = new UrlField(urlService, UrlType.CANCER);

        this.add(cancerLoadButton, cancerClearButton, cancerDataSizeButton, urlField);
    }

    private Button createDataSizeButton() {
        Button cancerDataSizeButton = new Button("Show record count");
        cancerDataSizeButton.addClickListener(clickEvent ->
        {
            Alert.info("Cancer data size: " + cancerService.getSize());
        });
        return cancerDataSizeButton;
    }

    private Button createClearButton() {
        Button cancerClearButton = new Button("Clear Cancer data");
        cancerClearButton.addClickListener(clickEvent ->
        {
            switch (cancerService.getStatus()) {
                case LOADING:
                    Alert.error("Loading cancer data, unable to clear...");
                    break;
                case DATA_EXISTS:
                case IDLE:
                    cancerService.evictCache();
                    Alert.info("Cleaning cancer data start...");
                    getUI().get().access(() -> {
                        cancerService.clear();
                        Alert.info("Cleaning done...");
                    });
                    break;
            }
        });
        return cancerClearButton;
    }

    private Button createLoadButton() {
        Button cancerLoadButton = new Button("Load Cancer data");
        cancerLoadButton.addClickListener(clickEvent ->
        {
            switch (cancerService.getStatus()) {
                case LOADING:
                    Alert.error("Loading in progress...");
                    break;
                case DATA_EXISTS:
                    Alert.error("Data already loaded.");
                    break;
                case IDLE:
                    Alert.info("Loading data...");
                    cancerService.evictCache();
                    UI ui = getUI().get();
                    ui.access((Command) () -> {
                        try {
                            cancerService.load();
                            Alert.info("Loading done.");
                        } catch (Exception e){
                            Alert.error("Loading failed with message: " + e.getMessage());
                        }
                    });
                    break;
            }
        });
        return cancerLoadButton;
    }

}
