package com.szwadronwilkowalfa.statystyki.views.components;

import com.szwadronwilkowalfa.statystyki.services.CancerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

public class CancerDataSpan extends Span {

    private final CancerService cancerService;

    public CancerDataSpan(CancerService cancerService){
        this.cancerService = cancerService;
        create();
    }


    private void create() {
        this.add(new Label("Cancer data: "));

        Button cancerLoadButton = new Button("Load Cancer data");
        cancerLoadButton.addClickListener(clickEvent ->
        {
            switch (cancerService.getStatus()) {
                case ERROR:
                    Notification.show("Error occurred...");
                    break;
                case LOADING:
                    Notification.show("Loading cancer data...");
                    break;
                case DATA_EXISTS:
                    Notification.show("Data already loaded.");
                    break;
                case IDLE:
                    Notification.show("Loading cancer data start...");
                    Thread async = new Thread(() -> cancerService.load());
                    async.start();
                    break;
            }
        });

        Button cancerClearButton = new Button("Clear Cancer data");
        cancerClearButton.addClickListener(clickEvent ->
        {
            switch (cancerService.getStatus()) {
                case LOADING:
                    Notification.show("Loading cancer data, unable to clear...");
                    break;
                case DATA_EXISTS:
                case IDLE:
                    Notification.show("Cleaning cancer data start...");
                    Thread async = new Thread(() -> cancerService.clear());
                    async.start();
                    break;
            }
        });

        Button cancerDataSizeButton = new Button("Show record count");
        cancerDataSizeButton.addClickListener(clickEvent ->
        {
            Notification.show("Cancer data size: " + cancerService.getSize());
        });

        this.add(cancerLoadButton, cancerClearButton, cancerDataSizeButton);
    }
}
