package com.szwadronwilkowalfa.statystyki.views.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

public class Alert {

    public static void info(String message) {
        show(message, "green");
    }

    public static void error(String message) {
        show(message, "red");
    }

    public static void show(String message, String color){
        Div content = new Div();
        content.addClassName("my-style");
        content.getStyle().set("color", color);
        content.setText(message);

        Notification notification = new Notification(content);
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }
}
