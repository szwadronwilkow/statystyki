package com.szwadronwilkowalfa.statystyki.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HomeView extends VerticalLayout {

    public HomeView(){
        Label label1 = new Label("Adrian Cieślik");
        Label label2 = new Label("Arkadiusz Dziób");
        Label label3 = new Label("Rafał Balcerzak");
        this.add(label1, label2, label3);
    }
}
