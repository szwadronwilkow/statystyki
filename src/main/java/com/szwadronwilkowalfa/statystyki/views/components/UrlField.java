package com.szwadronwilkowalfa.statystyki.views.components;

import com.helger.commons.url.URLValidator;
import com.szwadronwilkowalfa.statystyki.constants.UrlType;
import com.szwadronwilkowalfa.statystyki.services.UrlService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;

public class UrlField extends Span {

    private final UrlService urlService;
    private final UrlType urlType;

    private Label label;
    private TextField textField;
    private Button saveButton;
    private Button reloadButton;

    public UrlField(UrlService urlService, UrlType urlType){

        this.urlService = urlService;
        this.urlType = urlType;

        label = new Label(" Url: ");
        textField = new TextField();
        saveButton = new Button("Save");
        reloadButton = new Button("Reload");
        
        initSaveButton();
        initReloadButton();
        initTextField();
        
        add(label);
        add(textField);
        add(saveButton);
        add(reloadButton);

    }

    private void initTextField() {
        textField.setValue(urlService.findByNameOrDefault(UrlType.CANCER));
        textField.setWidth("10cm");
    }

    private void initReloadButton() {
        reloadButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            String url = urlService.findByNameOrDefault(urlType);
            textField.setValue(url);
        });
    }

    private void initSaveButton() {
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            String url = textField.getValue();
            if(URLValidator.isValid(url)){
                urlService.update(urlType, url);
            } else {
                Alert.error(urlType + " Url is not valid!");
            }
        });
    }


}
