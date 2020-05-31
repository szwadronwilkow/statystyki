package com.szwadronwilkowalfa.statystyki.views.components;

import com.szwadronwilkowalfa.statystyki.services.PowiatService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

public class PowiatSettingsSpan extends Span {

    private final PowiatService powiatService;

    public PowiatSettingsSpan(PowiatService powiatService) {
        this.powiatService = powiatService;
        create();
    }

    private void create() {
        this.add(new Label("Teryt data: "));

        Button loadButton = new Button("Load Teryt data");
        loadButton.addClickListener(clickEvent ->
        {
            switch (powiatService.getStatus()) {
                case LOADING:
                    Notification.show("Loading teryt data...");
                    break;
                case DATA_EXISTS:
                    Notification.show("Data already loaded.");
                    break;
                case IDLE:
                    Notification.show("Loading teryt data start...");
                    getUI().get().access(() -> {
                        powiatService.load();
                        Alert.info("Done loading Teryt data");
                    });
                    break;
            }
        });

        Button clearButton = new Button("Clear Teryt data");
        clearButton.addClickListener(clickEvent ->
        {
            switch (powiatService.getStatus()) {
                case LOADING:
                    Notification.show("Loading Teryt data, unable to clear...");
                    break;
                case DATA_EXISTS:
                case IDLE:
                    Notification.show("Cleaning Teryt data start...");
                    Thread async = new Thread(() -> powiatService.clear());
                    async.start();
                    break;
            }
        });

        Button dataSizeButton = new Button("Show record count");
        dataSizeButton.addClickListener(clickEvent ->
        {
            Notification.show("Cancer data size: " + powiatService.getSize());
        });

        this.add(loadButton, clearButton, dataSizeButton);
    }
}
