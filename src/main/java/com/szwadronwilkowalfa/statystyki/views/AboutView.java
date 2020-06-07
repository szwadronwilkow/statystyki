package com.szwadronwilkowalfa.statystyki.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

public class AboutView extends Div {
    public AboutView(){
        Label label1 = new Label("Cancer statistic application per land/county");
        this.add(label1);
    }
}
