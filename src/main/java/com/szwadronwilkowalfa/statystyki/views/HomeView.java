package com.szwadronwilkowalfa.statystyki.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

public class HomeView extends Div {

    public HomeView(){
        Button button = new Button("Do not press this button twice");

        button.addClickListener(clickEvent ->
                Notification.show("Do not press this button again"));

        this.add(button);
    }
}
