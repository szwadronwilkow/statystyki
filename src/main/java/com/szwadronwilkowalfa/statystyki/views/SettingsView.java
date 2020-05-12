package com.szwadronwilkowalfa.statystyki.views;

import com.szwadronwilkowalfa.statystyki.services.CancerResourceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsView extends Div {

    private final CancerResourceService cancerResourceService;



    @Autowired
    public SettingsView(CancerResourceService cancerResourceService) {
        this.cancerResourceService = cancerResourceService;

        Span cancerDataSpan = createCancerDataSpan();

        this.add(cancerDataSpan);
    }

    private Span createCancerDataSpan() {
        Span cancerSpan = new Span();
        cancerSpan.add(new Label("Cancer data: "));

        Button cancerLoadButton = new Button("Load Cancer data");
        cancerLoadButton.addClickListener(clickEvent ->
        {
            switch (cancerResourceService.getStatus()) {
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
                    Thread async = new Thread(() -> cancerResourceService.load());
                    async.start();
                    break;
            }
        });

        Button cancerClearButton = new Button("Clear Cancer data");
        cancerClearButton.addClickListener(clickEvent ->
        {
            switch (cancerResourceService.getStatus()) {
                case LOADING:
                    Notification.show("Loading cancer data, unable to clear...");
                    break;
                case DATA_EXISTS:
                case IDLE:
                    Notification.show("Cleaning cancer data start...");
                    Thread async = new Thread(() -> cancerResourceService.clear());
                    async.start();
                    break;
            }
        });

        Button cancerDataSizeButton = new Button("Show record count");
        cancerDataSizeButton.addClickListener(clickEvent ->
        {
            Notification.show("Cancer data size: " + cancerResourceService.getSize());
        });

        cancerSpan.add(cancerLoadButton, cancerClearButton, cancerDataSizeButton);
        return cancerSpan;
    }


}
