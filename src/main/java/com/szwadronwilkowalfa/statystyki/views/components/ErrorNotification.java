package com.szwadronwilkowalfa.statystyki.views.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

public class ErrorNotification {

    private String styles =
            ".my-style { "
            + "  color: red;"
            + " }";

    public ErrorNotification(String text){
        Div content = new Div();
        content.addClassName("my-style");
        content.setText(text);

        Notification notification = new Notification(content);
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

}
